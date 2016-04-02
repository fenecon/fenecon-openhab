/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecon.handler;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.fenecon.FeneconBindingConstants;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.BitWordElement;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ModbusDoublewordElement;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ModbusElement;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ModbusElementRange;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ModbusItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ModbusWordElement;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.OnOffBitItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ReservedElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.wimpi.modbus.Modbus;
import net.wimpi.modbus.io.ModbusSerialTransaction;
import net.wimpi.modbus.msg.ExceptionResponse;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.msg.ReadMultipleRegistersRequest;
import net.wimpi.modbus.msg.ReadMultipleRegistersResponse;
import net.wimpi.modbus.net.SerialConnection;
import net.wimpi.modbus.util.SerialParameters;

/**
 *
 * @author Stefan Feilmeier
 */
public abstract class FeneconHandler extends BaseThingHandler {
    private Logger logger = LoggerFactory.getLogger(FeneconHandler.class);

    private final ArrayList<ModbusElementRange> protocol;

    private int refresh = 60; // default: 60 seconds
    private String modbusinterface = "/dev/ttyUSB0";
    private int unitid;
    private int baudrate;

    private ScheduledFuture<?> refreshJob;

    public FeneconHandler(Thing thing, int unitid, int baudrate) {
        super(thing);
        this.protocol = getProtocol();
        this.unitid = unitid;
        this.baudrate = baudrate;
    }

    @Override
    public void initialize() {
        Configuration config = getThing().getConfiguration();

        // Get unitid or stay with default
        Object confUnitid = config.get("unitid");
        if (confUnitid != null) {
            try {
                setUnitid(((BigDecimal) confUnitid).intValue());
                logger.info("Set unitid to " + getUnitid());
            } catch (Exception e) {
                logger.warn("Unable to set unitid");
            }
        }

        // Get modbusinterface or stay with default
        Object confModbusInterface = config.get("modbusinterface");
        if (confModbusInterface != null) {
            try {
                modbusinterface = (String) confModbusInterface;
                logger.info("Set modbusinterface to " + modbusinterface);
            } catch (Exception e) {
                logger.warn("Unable to set modbusinterface");
            }
        }

        // Get refresh period or stay with default
        Object confRefresh = config.get("refresh");
        if (confRefresh != null) {
            try {
                refresh = ((BigDecimal) confRefresh).intValue();
                logger.info("Set refresh to " + refresh);
            } catch (Exception e) {
                logger.warn("Unable to set refresh");
            }
        }

        startAutomaticRefresh();
        super.initialize();
    }

    @Override
    public void dispose() {
        if (refreshJob != null) {
            refreshJob.cancel(true);
        }
    }

    private void startAutomaticRefresh() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    updateData();
                    for (ModbusElementRange elementRange : protocol) {
                        for (ModbusElement element : elementRange.getElements()) {
                            if (element instanceof ModbusItem) {
                                ModbusItem item = (ModbusItem) element;
                                updateState(item.getName(), item.getState());
                                // logger.info(item.getName() + ": " + item.getState());
                            } else if (element instanceof BitWordElement) {
                                BitWordElement bitWord = (BitWordElement) element;
                                for (OnOffBitItem bitItem : bitWord.getBitItems()) {
                                    updateState(bitWord.getName() + "_" + bitItem.getName(), bitItem.getState());
                                    // logger.info(
                                    // bitWord.getName() + "_" + bitItem.getName() + ": " + bitItem.getState());
                                }
                            }
                        }
                    }

                } catch (Exception e) {
                    logger.error("Exception occurred during execution: {}", e.getMessage(), e);
                }
            }
        };

        refreshJob = scheduler.scheduleAtFixedRate(runnable, 3, refresh, TimeUnit.SECONDS);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        if (command instanceof RefreshType) {
            try {
                logger.info("RefreshType");
            } catch (Exception e) {
                logger.debug("Unable to handle command for channel: {}", channelUID.getId());
            }
        } else {
            logger.debug("Command {} is not supported for channel: {}", command, channelUID.getId());
        }
    }

    private void updateData() {
        SerialParameters params = new SerialParameters();
        params.setPortName(modbusinterface);
        params.setBaudRate(getBaudrate());
        params.setDatabits(8);
        params.setParity("None");
        params.setStopbits(1);
        params.setEncoding(Modbus.SERIAL_ENCODING_RTU);
        params.setEcho(false);
        params.setReceiveTimeout(FeneconBindingConstants.MODBUS_TIMEOUT);
        SerialConnection serialConnection = new SerialConnection(params);
        try {
            serialConnection.open();
            for (ModbusElementRange elementRange : protocol) {
                ReadMultipleRegistersRequest req = new ReadMultipleRegistersRequest(elementRange.getStartAddress(),
                        elementRange.getTotalLength());
                req.setUnitID(getUnitid());
                req.setHeadless();
                ModbusSerialTransaction trans = new ModbusSerialTransaction(serialConnection);
                trans.setRequest(req);
                trans.setTransDelayMS(FeneconBindingConstants.MODBUS_TRANS_DELAY_MS);
                trans.setRetries(FeneconBindingConstants.MODBUS_RETRIES);
                trans.execute();
                ModbusResponse res = trans.getResponse();
                if (res instanceof ReadMultipleRegistersResponse) {
                    ReadMultipleRegistersResponse mres = (ReadMultipleRegistersResponse) res;
                    // send updates
                    int position = 0;
                    for (ModbusElement element : elementRange.getElements()) {
                        if (element instanceof ModbusWordElement) {
                            ModbusWordElement word = (ModbusWordElement) element;
                            word.updateData(mres.getRegister(position));
                            position++;
                        } else if (element instanceof ModbusDoublewordElement) {
                            ModbusDoublewordElement doubleword = (ModbusDoublewordElement) element;
                            doubleword.updateData(mres.getRegister(position), mres.getRegister(position + 1));
                            position += 2;
                        } else if (element instanceof ReservedElement) {
                            ReservedElement reservedElement = (ReservedElement) element;
                            position += reservedElement.getLength();
                        }
                    }

                } else {
                    if (res instanceof ExceptionResponse) {
                        throw new IOException(
                                "Modbus exception response: " + ((ExceptionResponse) res).getExceptionCode());
                    } else {
                        throw new IOException("Undefined Modbus response: " + res.toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            serialConnection.close();
        }
    }

    protected abstract ArrayList<ModbusElementRange> getProtocol();

    protected int getBaudrate() {
        return this.baudrate;
    }

    protected int getUnitid() {
        return this.unitid;
    }

    protected void setUnitid(int unitid) {
        this.unitid = unitid;
    }
}
