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
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;

/**
 *
 * @author Stefan Feilmeier
 */
public class IODigitalOutput implements IOOutput {
    private DigitalOutput pin;
    private final Command initState;

    public IODigitalOutput(DigitalOutput pin, Command initState) {
        this.pin = pin;
        this.initState = initState;
        pin.setTeardownOnShutdown(true);
        handleCommand(initState);
    }

    @Override
    public State getState() {
        if (pin.isHigh()) {
            return OnOffType.ON;
        } else {
            return OnOffType.OFF;
        }
    }

    @Override
    public void handleCommand(Command command) {
        if (command instanceof OnOffType) {
            OnOffType cmd = (OnOffType) command;
            switch (cmd) {
                case ON:
                    pin.high();
                    break;
                case OFF:
                    pin.low();
                    break;
            }
        }
    }

    @Override
    public void dispose() {
        handleCommand(initState);
    }
}
