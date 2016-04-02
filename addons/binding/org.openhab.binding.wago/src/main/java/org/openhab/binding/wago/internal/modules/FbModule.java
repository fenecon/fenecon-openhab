package org.openhab.binding.wago.internal.modules;

import org.openhab.binding.wago.internal.channels.FbChannel;

public abstract class FbModule {
    protected final FbChannel[] channels;

    protected FbModule(FbChannel[] channels) {
        this.channels = channels;
    }

    public FbChannel[] getChannels() {
        return channels;
    }

    public abstract String getName();

    @Override
    public String toString() {
        StringBuilder channelString = new StringBuilder();
        String sep = "";
        for (FbChannel channel : channels) {
            channelString.append(sep + channel.getId());
            sep = ", ";
        }
        return getName() + " [" + channelString.toString() + "]";
    }
}
