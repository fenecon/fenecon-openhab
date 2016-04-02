/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fems.handler;

import org.eclipse.smarthome.core.thing.ChannelUID;
import org.eclipse.smarthome.core.thing.Thing;
import org.eclipse.smarthome.core.thing.ThingStatus;
import org.eclipse.smarthome.core.thing.binding.BaseThingHandler;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.RefreshType;
import org.openhab.binding.fems.FemsBindingConstants;
import org.openhab.binding.fems.internal.types.IO;
import org.openhab.binding.fems.internal.types.IOOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link FemsHandler} is responsible for handling commands, which are
 * sent to one of the channels.
 *
 * @author Stefan Feilmeier - Initial contribution
 */
public class FemsHandler extends BaseThingHandler {

    private Logger logger = LoggerFactory.getLogger(FemsHandler.class);

    public FemsHandler(Thing thing) {
        super(thing);
    }

    @Override
    public void initialize() {
        logger.debug("Initializing FEMS handler.");
        updateStatus(ThingStatus.ONLINE);
    }

    @Override
    public void handleCommand(ChannelUID channelUID, Command command) {
        IO io = FemsBindingConstants.IOS.get(channelUID.getId());
        if (io != null) {
            if (command instanceof RefreshType) {
                updateState(channelUID, io.getState());
            } else {
                if (io instanceof IOOutput) {
                    IOOutput ioOutput = (IOOutput) io;
                    ioOutput.handleCommand(command);
                }
            }
        }
    }
}
