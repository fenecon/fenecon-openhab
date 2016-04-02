/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecon.handler;

import java.util.ArrayList;

import org.eclipse.smarthome.core.thing.Thing;
import org.openhab.binding.fenecon.internal.essprotocol.FeneconIndustrialProtocolFactory;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ModbusElementRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Feilmeier
 */
public class FeneconIndustrialHandler extends FeneconHandler {

    private Logger logger = LoggerFactory.getLogger(FeneconIndustrialHandler.class);

    private final static int UNITID = 10;
    private final static int BAUDRATE = 19200;

    public FeneconIndustrialHandler(Thing thing) {
        super(thing, UNITID, BAUDRATE);
    }

    @Override
    public void initialize() {
        logger.debug("Initializing FENECON Industrial-Series (ESS) handler.");
        super.initialize();
    }

    @Override
    protected ArrayList<ModbusElementRange> getProtocol() {
        return FeneconIndustrialProtocolFactory.getProtocol();
    }
}
