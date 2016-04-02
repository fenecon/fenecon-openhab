package org.openhab.binding.wago.internal.channels;

import org.eclipse.smarthome.core.types.Command;

import net.wimpi.modbus.net.TCPMasterConnection;

public class FbInputCoilChannelGroup extends FbCoilChannelGroup {
    private FbInputCoilChannel[] fbInputCoilChannels;

    public FbInputCoilChannelGroup(FbInputCoilChannel[] fbInputCoilChannels) {
        this.fbInputCoilChannels = fbInputCoilChannels;
    }

    @Override
    public int getStartAdress() {
        return 0;
    }

    @Override
    protected FbChannel[] getChannels() {
        return fbInputCoilChannels;
    }

    @Override
    public boolean handleCommand(TCPMasterConnection tcpConnection, String name, Command command) {
        return false;
    }
}
