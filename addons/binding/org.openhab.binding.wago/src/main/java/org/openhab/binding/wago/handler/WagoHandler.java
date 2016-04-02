/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.wago.handler;

import java.math.BigDecimal;
import java.net.InetAddress;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.eclipse.smarthome.config.core.Configuration;
import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.openhab.binding.wago.internal.Fieldbus;
import org.openhab.binding.wago.internal.FieldbusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link WagoHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Stefan Feilmeier - Initial contribution
 */
public class WagoHandler extends BaseThingHandler {

    private Logger logger = LoggerFactory.getLogger(WagoHandler.class);
    private ScheduledFuture<?> refreshJob;

    private InetAddress ip = null;
    private String username = "admin";
    private String password = "wago";
    private int port = 502;
    private int refresh = 1; // default: 1 second

    private Fieldbus fieldbus = null;

    public WagoHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        Configuration config = getThing().getConfiguration();

        // Get IP
        Object confIp = config.get("ip");
        if (confIp != null) {
            try {
                ip = InetAddress.getByName((String) confIp);
                logger.info("Set IP to " + ip);
            } catch (Exception e) {
                logger.warn("Unable to set unitid");
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

        if (ip != null) {
            startAutomaticRefresh();
        } else {
            logger.error("Unable to configure WAGO binding");
        }

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
                    if (fieldbus == null) {
                        FieldbusFactory fbFactory = new FieldbusFactory();
                        fieldbus = fbFactory.getFieldbus(ip, port, username, password);
                    }
                    updateData();

                } catch (Exception e) {
                    logger.error("Exception occurred during execution: {}", e.getMessage(), e);
                }
            }
        };

        refreshJob = scheduler.scheduleAtFixedRate(runnable, 3, refresh, TimeUnit.SECONDS);
    }

    private void updateData() {
        for (Map.Entry<String, State> itemState : fieldbus.updateData().entrySet()) {
            updateState(itemState.getKey(), itemState.getValue());
        }
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        fieldbus.handleCommand(channelUID, command);
    }
}
