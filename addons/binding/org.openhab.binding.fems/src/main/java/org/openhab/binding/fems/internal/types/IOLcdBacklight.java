/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fems.internal.types;

import org.bulldog.core.gpio.Pwm;
import org.eclipse.smarthome.core.types.Command;

/**
 *
 * @author Stefan Feilmeier
 */
public class IOLcdBacklight extends IOAnalogOutput {
    public final static float BACKLIGHT_MAX_VALUE = 0.70f;
    public final static float BACKLIGHT_MIN_VALUE = 0.20f;
    public final static int BACKLIGHT_SECONDS = 120;

    /**
     *
     * @param myId required, to schedule myself correctly via IO_AGENT
     * @param pwmPin
     */
    public IOLcdBacklight(Command initState, Pwm pwmPin) {
        super(pwmPin, initState, null);
    }

    @Override
    protected void setAnalogOutput(float duty) {
        if (duty > BACKLIGHT_MAX_VALUE) {
            duty = BACKLIGHT_MAX_VALUE;
        } else if (duty < BACKLIGHT_MIN_VALUE) {
            duty = BACKLIGHT_MIN_VALUE;
        }
        super.setAnalogOutput(duty);
    }
}
