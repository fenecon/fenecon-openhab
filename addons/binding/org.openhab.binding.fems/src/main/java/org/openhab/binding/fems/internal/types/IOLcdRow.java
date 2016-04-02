/**
 * Copyright (c) 2015 FENECON GmbH & Co. KG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fems.internal.types;

import org.eclipse.smarthome.core.library.types.StringType;
import org.eclipse.smarthome.core.types.Command;
import org.eclipse.smarthome.core.types.State;
import org.openhab.binding.fems.FemsBindingConstants;

/**
 *
 * @author Stefan Feilmeier
 */
public class IOLcdRow implements IOOutput {
    private final int row;
    private final IOLcd lcd;

    public IOLcdRow(IOLcd lcd, String id) {
        this.lcd = lcd;
        switch (id) {
            case FemsBindingConstants.LCD_1:
                this.row = 0;
                break;
            case FemsBindingConstants.LCD_2:
            default:
                this.row = 1;
                break;
        }
    }

    @Override
    public State getState() {
        if (row == 0) {
            return new StringType(lcd.getFirstRow());
        } else {
            return new StringType(lcd.getSecondRow());
        }
    }

    @Override
    public void handleCommand(Command command) {
        if (command instanceof StringType) {
            lcd.handleCommand(row, command);
        }
    }

    @Override
    public void dispose() {
        // nothing to do here
    }
}
