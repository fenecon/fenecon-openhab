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
import org.openhab.binding.fenecon.internal.essprotocol.FeneconDessProtocolFactory;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ModbusElementRange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Feilmeier
 */
public class FeneconDessHandler extends FeneconHandler {

    private Logger logger = LoggerFactory.getLogger(FeneconDessHandler.class);

    private final static int UNITID = 4;
    private final static int BAUDRATE = 9600;

    public FeneconDessHandler(Thing thing) {
        super(thing, UNITID, BAUDRATE);
    }

    @Override
    public void initialize() {
        logger.debug("Initializing FENECON PRO-Series (DESS) handler.");
        super.initialize();
    }

    @Override
    protected ArrayList<ModbusElementRange> getProtocol() {
        return FeneconDessProtocolFactory.getProtocol();
    }
}
