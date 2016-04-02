/**
 * Copyright (c) 2014 Stefan Feilmeier <stefan.feilmeier@fenecon.de>.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecon.internal.essprotocol.modbus;

public class ReservedElement implements ModbusElement {
	private int length;
	
	public ReservedElement(int fromAddress, int toAddress) {
		this.length = toAddress-fromAddress+1;
	}
	
	public ReservedElement(int address) {
		this(address, address);
	}
		
	public int getLength() {
		return length;
	}

	@Override
	public String getName() {
		return null;
	}

}
