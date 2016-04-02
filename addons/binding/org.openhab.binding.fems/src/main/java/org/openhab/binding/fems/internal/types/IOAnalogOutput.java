/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fems.internal.types;

import org.bulldog.core.gpio.Pwm;
import org.eclipse.smarthome.core.library.types.IncreaseDecreaseType;
import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.library.types.PercentType;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Stefan Feilmeier
 */
public class IOAnalogOutput implements IOOutput {
    private final Logger logger = LoggerFactory.getLogger(IOAnalogOutput.class);
    public static final float FREQUENCY = 5000.0f; // 5 kHz

    private Pwm pwmPin;
    private IOAnalogOutputCtrlVolt ctrlVolt;
    private final Command initState;

    public IOAnalogOutput(Pwm pwmPin, Command initState, IOAnalogOutputCtrlVolt ctrlVolt) {
        this.pwmPin = pwmPin;
        this.initState = initState;
        this.ctrlVolt = ctrlVolt;
        pwmPin.setTeardownOnShutdown(true);
        handleCommand(initState);
    }

    @Override
    public State getState() {
        double duty = pwmPin.getDuty();
        return toPercentType(duty);
    }

    @Override
    public void handleCommand(Command command) {
        if (command instanceof OnOffType) {
            OnOffType cmd = (OnOffType) command;
            switch (cmd) {
                case ON:
                    setAnalogOutput(1);
                    break;
                case OFF:
                    setAnalogOutput(0);
                    break;
            }
        } else if (command instanceof IncreaseDecreaseType) {
            IncreaseDecreaseType cmd = (IncreaseDecreaseType) command;
            int currentValue = toPercentType(pwmPin.getDuty()).intValue();
            switch (cmd) {
                case INCREASE:
                    setAnalogOutput(toDuty(new PercentType(currentValue + 1)));
                    break;
                case DECREASE:
                    setAnalogOutput(toDuty(new PercentType(currentValue - 1)));
                    break;
            }
        } else if (command instanceof PercentType) {
            PercentType cmd = (PercentType) command;
            setAnalogOutput(toDuty(cmd));
        }
    }

    public IOAnalogOutputCtrlVolt getCtrlVolt() {
        // might be null!
        return ctrlVolt;
    }

    private PercentType toPercentType(double duty) {
        if (duty >= 1) {
            return PercentType.HUNDRED;
        } else if (duty <= 0) {
            return PercentType.ZERO;
        } else {
            return new PercentType((int) Math.round(duty * 100));
        }
    }

    private float toDuty(PercentType percent) {
        return (percent.floatValue() / 100);
    }

    protected void setAnalogOutput(float duty) {
        try {
            pwmPin.setFrequency(FREQUENCY);
            pwmPin.setDuty(duty);
            pwmPin.enable();

        } catch (RuntimeException e) {
            logger.error("AnalogOutput failed: " + e.getMessage());
        }
    }

    @Override
    public void dispose() {
        handleCommand(initState);
    }
}
