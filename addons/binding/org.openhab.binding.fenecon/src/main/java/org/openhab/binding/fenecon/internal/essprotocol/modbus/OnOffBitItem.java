/**
 * Copyright (c) 2014 Stefan Feilmeier <stefan.feilmeier@fenecon.de>.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecon.internal.essprotocol.modbus;

import java.math.BigInteger;
import net.wimpi.modbus.procimg.Register;
import org.eclipse.smarthome.core.library.types.OnOffType;

public class OnOffBitItem extends ModbusItem {
	private int bitAddress;
	
	public OnOffBitItem(int bitAddress, String name) {
		super(name);
		this.bitAddress = bitAddress;
	}
	
	public void updateData(Register register) {
		if(BigInteger.valueOf(register.getValue()).testBit(bitAddress)) {
			setState(OnOffType.ON);	
		} else {
			setState(OnOffType.OFF);
		}		
	}
}
