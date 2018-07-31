package com.zzy.def.module;

public class NetworkChangeEvent {
    public boolean isConnected;// 是否连接网络

    public NetworkChangeEvent(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
