/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fems.internal.types;

import org.bulldog.core.gpio.DigitalOutput;
import org.eclipse.smarthome.core.library.types.OnOffType;

/**
 * ON: control VOLTAGE
 * OFF: control AMPERE
 *
 * @author Stefan Feilmeier
 */
public class IOAnalogOutputCtrlVolt extends IODigitalOutput {

    public IOAnalogOutputCtrlVolt(DigitalOutput pin, OnOffType init) {
        super(pin, init);
    }
}
