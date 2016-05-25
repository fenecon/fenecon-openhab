/**
 * Copyright (c) 2014 Stefan Feilmeier <stefan.feilmeier@fenecon.de>.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecon.internal.essprotocol.modbus;

import java.math.BigDecimal;

import org.eclipse.smarthome.core.library.types.DecimalType;

import net.wimpi.modbus.procimg.Register;

public class ShortDecimalWordItem extends DecimalWordItem {

    public ShortDecimalWordItem(String name) {
        super(name);
    }

    public ShortDecimalWordItem(String name, double multiplier) {
        super(name, multiplier);
    }

    @Override
    public void updateData(Register register) {
        short value = register.toShort();
        if (value == 0 && delta != 0) { // avoid e.g. "-10000" as result
            setState(new DecimalType(0));
        } else {
            setState(new DecimalType(
                    new BigDecimal((value - delta) * multiplier).setScale(2, BigDecimal.ROUND_HALF_UP)));
        }

    }
}
