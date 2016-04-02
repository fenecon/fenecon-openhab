package org.openhab.binding.wago.internal.channels;

import org.eclipse.smarthome.core.library.types.OnOffType;
import org.eclipse.smarthome.core.types.Command;

import net.wimpi.modbus.ModbusException;
import net.wimpi.modbus.ModbusIOException;
import net.wimpi.modbus.ModbusSlaveException;
import net.wimpi.modbus.io.ModbusTCPTransaction;
import net.wimpi.modbus.msg.WriteCoilRequest;
import net.wimpi.modbus.net.TCPMasterConnection;

public class FbOutputCoilChannelGroup extends FbCoilChannelGroup {
    private FbOutputCoilChannel[] fbOutputCoilChannels;

    public FbOutputCoilChannelGroup(FbOutputCoilChannel[] fbOutputCoilChannels) {
        this.fbOutputCoilChannels = fbOutputCoilChannels;
    }

    @Override
    public int getStartAdress() {
        return 512;
    }

    @Override
    protected FbChannel[] getChannels() {
        return fbOutputCoilChannels;
    }

    @Override
    public boolean handleCommand(TCPMasterConnection tcpConnection, String name, Command command)
            throws ModbusIOException, ModbusSlaveException, ModbusException {
        boolean ret = false;
        FbChannel[] channels = getChannels();
        for (int i = 0; i < channels.length; i++) {
            if (channels[i].getId().equals(name)) {
                if (command instanceof OnOffType) {
                    OnOffType onOffCommand = (OnOffType) command;
                    WriteCoilRequest request = new WriteCoilRequest(getStartAdress() + i,
                            onOffCommand.equals(OnOffType.ON) ? true : false);
                    ModbusTCPTransaction trans = new ModbusTCPTransaction(request);
                    trans.setConnection(tcpConnection);
                    trans.execute();
                    ret = true;
                }
            }
        }
        return ret;
    }
}
