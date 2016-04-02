/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecon;

import java.util.Collection;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

import com.google.common.collect.Lists;

/**
 * The {@link FeneconBindingConstants} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Stefan Feilmeier - Initial contribution
 */
public class FeneconBindingConstants {

    public static final String BINDING_ID = "fenecon";

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_MINIES = new ThingTypeUID(BINDING_ID, "minies");
    public final static ThingTypeUID THING_TYPE_CESS = new ThingTypeUID(BINDING_ID, "cess");
    public final static ThingTypeUID THING_TYPE_DESS = new ThingTypeUID(BINDING_ID, "dess");
    public final static ThingTypeUID THING_TYPE_INDUSTRIAL = new ThingTypeUID(BINDING_ID, "industrial");

    // Collection of Thing Type UIDs
    public final static Collection<ThingTypeUID> SUPPORTED_THING_TYPES = Lists.newArrayList(THING_TYPE_CESS,
            THING_TYPE_DESS, THING_TYPE_INDUSTRIAL, THING_TYPE_MINIES);

    // Modbus data
    public final static int MODBUS_TIMEOUT = 3000;
    public final static int MODBUS_TRANS_DELAY_MS = 1000;
    public final static int MODBUS_RETRIES = 10;
}
