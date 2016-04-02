/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fems.internal.types;

import java.io.IOException;
import java.nio.channels.ClosedByInterruptException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.eclipse.smarthome.core.types.UnDefType;
import org.openhab.binding.fems.FemsBindingConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Feilmeier
 */
public class IOUserLed implements IOOutput {
    private final Logger logger = LoggerFactory.getLogger(IOUserLed.class);
    private final int led;
    private State state = UnDefType.UNDEF;
    private final Command initState;

    public IOUserLed(String id, Command initState) {
        this.initState = initState;
        switch (id) {
            case FemsBindingConstants.UserLED_1:
                led = 0;
                break;
            case FemsBindingConstants.UserLED_2:
                led = 1;
                break;
            case FemsBindingConstants.UserLED_3:
                led = 2;
                break;
            case FemsBindingConstants.UserLED_4:
            default:
                led = 3;
                break;
        }
        handleCommand(initState);
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public void handleCommand(Command command) {
        if (command instanceof OnOffType) {
            OnOffType cmd = (OnOffType) command;
            switchLED(cmd);
        }
    }

    private void switchLED(OnOffType state) {
        try {
            Files.write(Paths.get("/sys/class/leds/beaglebone:green:usr" + led, "brightness"),
                    ((state == OnOffType.ON) ? "1" : "0").getBytes());
            this.state = state;
        } catch (ClosedByInterruptException ce) {
            // known error... ignore
        } catch (IOException e) {
            logger.warn("Unable to switch User-LED: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        handleCommand(initState);
    }
}
