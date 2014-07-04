package net.njay.mesigi.event.connection;

import net.njay.serverinterconnect.client.TcpClientManager;

public class ConnectEvent {

    private TcpClientManager manager;

    public ConnectEvent(TcpClientManager manager){
        this.manager = manager;
    }

    public TcpClientManager getManager(){ return this.manager; }

}
