/**
 * Copyright (c) 2014 Stefan Feilmeier <stefan.feilmeier@fenecon.de>.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.binding.fenecon.internal.essprotocol.modbus;

import java.util.Arrays;

import net.wimpi.modbus.procimg.Register;

public class BitWordElement implements ModbusWordElement {
//	private Logger logger = LoggerFactory.getLogger(ModbusBitWord.class);
	OnOffBitItem[] bitItemsOnOff;
	String name;
	
	public BitWordElement(String name, OnOffBitItem... bitItemsOnOff) {
		this.name = name;
		this.bitItemsOnOff = bitItemsOnOff;
	}

	@Override
	public void updateData(Register register) {
		for (OnOffBitItem bitItemOnOff : bitItemsOnOff) {
			bitItemOnOff.updateData(register);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "ModbusBitWord [name=" + name + ", bitItemsOnOff="
				+ Arrays.toString(bitItemsOnOff) + "]";
	}
	
	public OnOffBitItem[] getBitItems() {
		return bitItemsOnOff;
	}
}
