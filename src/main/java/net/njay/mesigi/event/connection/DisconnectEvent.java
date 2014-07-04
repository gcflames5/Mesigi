package net.njay.mesigi.event.connection;

import net.njay.serverinterconnect.client.TcpClientManager;

public class DisconnectEvent {

    private TcpClientManager manager;

    public DisconnectEvent(TcpClientManager manager){
        this.manager = manager;
    }

    public TcpClientManager getManager(){ return this.manager; }

}