/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.wago;

import org.eclipse.smarthome.core.thing.ThingTypeUID;

/**
 * The {@link WagoBinding} class defines common constants, which are
 * used across the whole binding.
 *
 * @author Stefan Feilmeier - Initial contribution
 */
public class WagoBindingConstants {

    public static final String BINDING_ID = "wago";

    // List of all Thing Type UIDs
    public final static ThingTypeUID THING_TYPE_FIELDBUS = new ThingTypeUID(BINDING_ID, "fieldbus");
}
