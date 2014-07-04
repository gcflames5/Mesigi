package net.njay.mesigi.client;

import net.njay.mesigi.client.user.User;
import net.njay.serverinterconnect.client.TcpClientManager;

import javax.net.ssl.SSLSocket;
import java.io.IOException;

/**
 * Created by Nick on 7/3/14.
 */
public class Client {

    private User user;
    private TcpClientManager tcpManager;

    public Client(User user, String ipToConnect, int listenPort){
        this.user = user;
        tcpManager = new TcpClientManager(ipToConnect, listenPort);
    }

    public void initialize() throws IOException{
        tcpManager.initialize();
    }

    public TcpClientManager getTcpManager(){ return this.tcpManager; }

}
