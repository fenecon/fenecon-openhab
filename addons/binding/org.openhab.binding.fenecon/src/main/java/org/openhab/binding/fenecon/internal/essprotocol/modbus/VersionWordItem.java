package org.openhab.binding.fenecon.internal.essprotocol.modbus;

import org.eclipse.smarthome.core.library.types.StringType;

import net.wimpi.modbus.procimg.Register;

public class VersionWordItem extends DecimalWordItem {

    public VersionWordItem(String name) {
        super(name);
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void updateData(Register register) {
        int versionCode = register.getValue();
        setState(new StringType( String.format("%d.%d", versionCode >> 8 , versionCode & 255 ) ));
    }
}
