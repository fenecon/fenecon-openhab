package org.openhab.binding.wago.internal.channels;

public abstract class FbChannel {
    private final String id;

    public FbChannel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
