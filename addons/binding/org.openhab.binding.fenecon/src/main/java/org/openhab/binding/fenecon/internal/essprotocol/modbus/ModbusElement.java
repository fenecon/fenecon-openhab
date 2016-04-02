/**
 * Copyright (c) 2014 Stefan Feilmeier <stefan.feilmeier@fenecon.de>.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecon.internal.essprotocol.modbus;

/*
 * A ModbusElement is one concrete element of the modbus
 * protocol
 * 
 * Don't implement this interface directly, but implement
 * - ModbusWordElement for 16bit elements
 * - ModbusDoublewordElement for 32 bit elements
 * - ModbusReservedElement for a specified number of reserved/unused 16bit elements
 */

public abstract interface ModbusElement {
	/* 
	 * Return the name of the modbus element
	 */
	public String getName();
}
