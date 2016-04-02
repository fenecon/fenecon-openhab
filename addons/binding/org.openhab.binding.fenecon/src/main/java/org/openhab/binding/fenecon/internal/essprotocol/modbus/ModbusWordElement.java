/**
 * Copyright (c) 2014 Stefan Feilmeier <stefan.feilmeier@fenecon.de>.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecon.internal.essprotocol.modbus;

import net.wimpi.modbus.procimg.Register;

public interface ModbusWordElement extends ModbusElement {
	/*
	 * Handle data from a register
	 */
	public void updateData(Register register);
}
