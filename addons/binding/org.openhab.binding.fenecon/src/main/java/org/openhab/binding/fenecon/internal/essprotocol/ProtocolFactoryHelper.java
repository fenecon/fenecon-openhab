package org.openhab.binding.fenecon.internal.essprotocol;

import java.util.ArrayList;

import org.openhab.binding.fenecon.internal.essprotocol.modbus.BitWordElement;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.DecimalDoublewordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.DecimalWordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ModbusElement;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ModbusElementRange;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.ModbusItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.OnOffBitItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.PercentageWordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.SecureDecimalWordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.StringWordItem;
import org.openhab.binding.fenecon.internal.essprotocol.modbus.VersionWordItem;

public class ProtocolFactoryHelper {
    public static void generateItemsFile(ArrayList<ModbusElementRange> protocol, String channel) {
        for (ModbusElementRange elementRange : protocol) {
            for (ModbusElement element : elementRange.getElements()) {
                if (element instanceof ModbusItem) {
                    ModbusItem item = (ModbusItem) element;
                    String name = item.getName();
                    if (item instanceof StringWordItem || item instanceof VersionWordItem) {
                        System.out.println("String " + name + " \"" + name + " [%s]\" { channel=\"fenecon:" + channel
                                + ":f:" + name + "\" }");
                    } else if (item instanceof PercentageWordItem) {
                        System.out.println("Dimmer " + name + "\"" + name + " [%d %%]\" { channel=\"fenecon:" + channel
                                + ":f:" + name + "\" }");
                    } else if (item instanceof DecimalWordItem || item instanceof DecimalDoublewordItem
                            || item instanceof SecureDecimalWordItem) {
                        System.out.println("Number " + name + " \"" + name + " [%d]\" { channel=\"fenecon:" + channel
                                + ":f:" + name + "\" }");
                    } else {
                        System.out.println("Missing: " + name);
                    }
                } else if (element instanceof BitWordElement) {
                    BitWordElement bitWord = (BitWordElement) element;
                    for (OnOffBitItem bitItem : bitWord.getBitItems()) {
                        String name = bitWord.getName() + "_" + bitItem.getName();
                        System.out.println("Number " + name + " \"" + name + " [%d]\" { channel=\"fenecon:" + channel
                                + ":f:" + name + "\" }");
                    }
                }
            }
        }
    }
}
