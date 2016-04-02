package org.openhab.binding.wago.internal.channels;

import java.io.IOException;
import java.util.TreeMap;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.types.State;

import net.wimpi.modbus.msg.ModbusRequest;
import net.wimpi.modbus.msg.ModbusResponse;
import net.wimpi.modbus.msg.ReadCoilsRequest;
import net.wimpi.modbus.msg.ReadCoilsResponse;

public abstract class FbCoilChannelGroup extends FbChannelGroup {
    @Override
    public ModbusRequest getModbusRequest() {
        if (getChannels().length > 0) {
            return new ReadCoilsRequest(getStartAdress(), getChannels().length);
        } else {
            return null;
        }

    }

    @Override
    public TreeMap<String, State> handleModbusResponse(ModbusResponse res) throws IOException {
        TreeMap<String, State> ret = new TreeMap<String, State>();
        if (res instanceof ReadCoilsResponse) {
            ReadCoilsResponse cres = (ReadCoilsResponse) res;
            FbChannel[] channels = getChannels();
            for (int i = 0; i < channels.length; i++) {
                String name = channels[i].getId();
                State state = cres.getCoilStatus(i) == true ? OnOffType.ON : OnOffType.OFF;
                ret.put(name, state);
            }
        } else {
            super.handleModbusResponse(res);
        }
        return ret;
    }
}
