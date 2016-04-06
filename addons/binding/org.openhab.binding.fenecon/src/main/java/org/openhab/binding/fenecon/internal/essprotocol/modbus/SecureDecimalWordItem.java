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

public class SecureDecimalWordItem extends ModbusItem implements ModbusWordElement {
    // private Logger logger = LoggerFactory.getLogger(DecimalWordItem.class);
    protected double multiplier = 1.;
    protected int delta = 0;
    protected int errorval = 0;

    public SecureDecimalWordItem(String name) {
        this(name, 1.);
    }

    public SecureDecimalWordItem(String name, double multiplier) {
        this(name, multiplier, 0);

    }

    public SecureDecimalWordItem(String name, double multiplier, int delta) {
        this(name, multiplier, delta, 0);
    }

    public SecureDecimalWordItem(String name, double multiplier, int delta, int errorval) {
        super(name);
        this.multiplier = multiplier;
        this.delta = delta;
        this.errorval = errorval;
    }

    @Override
    public void updateData(Register register) {
        int value = register.getValue();
        setState(new DecimalType(new BigDecimal(value == errorval ? 0 : ((value - delta) * multiplier)).setScale(2,
                BigDecimal.ROUND_HALF_UP)));
    }
}
