package org.openhab.binding.fenecon.internal.essprotocol.modbus;

import org.eclipse.smarthome.core.library.types.StringType;

import net.wimpi.modbus.procimg.Register;

public class StringWordItem extends DecimalWordItem {
    private final String[] values;

    public StringWordItem(String name, String... values) {
        super(name);
        this.values = values;
    }

    @Override
    public void updateData(Register register) {
        int id = register.getValue();
        String value = (values.length > id) ? values[id] : Integer.toString(id);
        setState(new StringType(value));
    }
}
