package org.openhab.binding.wago.internal;

import java.io.IOException;
import java.util.TreeMap;

import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.openhab.binding.wago.internal.channels.FbChannelGroup;
import org.openhab.binding.wago.internal.channels.FbInputCoilChannelGroup;
import org.openhab.binding.wago.internal.channels.FbOutputCoilChannelGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.ExceptionResponse;
import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.net.TCPMasterConnection;

public class Fieldbus {
    private static Logger logger = LoggerFactory.getLogger(Fieldbus.class);
    private final FbChannelGroup[] channelGroups;
    private final TCPMasterConnection modbusConnection;

    public Fieldbus(TCPMasterConnection modbusConnection, FbInputCoilChannelGroup fbInputCoilChannelGroup,
            FbOutputCoilChannelGroup fbOutputCoilChannelGroup) {
        channelGroups = new FbChannelGroup[] { fbInputCoilChannelGroup, fbOutputCoilChannelGroup };
        this.modbusConnection = modbusConnection;
    }

    public FbChannelGroup[] getChannelGroups() {
        return channelGroups;
    }

    public TreeMap<String, State> updateData() {
        TreeMap<String, State> ret = new TreeMap<String, State>();
        try {
            modbusConnection.connect();
            for (FbChannelGroup channelGroup : channelGroups) {
                ModbusRequest request = channelGroup.getModbusRequest();
                if (request != null) {
                    ModbusTCPTransaction trans = new ModbusTCPTransaction(request);
                    trans.setConnection(modbusConnection);
                    trans.execute();
                    ModbusResponse res = trans.getResponse();
                    if (res instanceof ExceptionResponse) {
                        throw new IOException(
                                "Modbus exception response: " + ((ExceptionResponse) res).getExceptionCode());
                    } else {
                        TreeMap<String, State> newStates = channelGroup.handleModbusResponse(res);
                        ret.putAll(newStates);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            modbusConnection.close();
        }
        return ret;
    }

    public void handleCommand(ChannelUID channelUID, Command command) {
        try {
            modbusConnection.connect();
            boolean success = false;
            for (FbChannelGroup channelGroup : channelGroups) {
                success = channelGroup.handleCommand(modbusConnection, channelUID.getId(), command);
            }
            if (!success) {
                logger.warn("Unable to set " + channelUID.getId() + " to " + command);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            modbusConnection.close();
        }
    }
}
