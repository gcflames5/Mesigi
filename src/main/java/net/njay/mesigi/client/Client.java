package net.njay.mesigi.client;

import net.njay.mesigi.client.auth.Credentials;
import net.njay.mesigi.client.user.User;
import net.njay.mesigi.packet.auth.AuthenticationSecretPacket;
import net.njay.serverinterconnect.client.TcpClientManager;

import javax.net.ssl.SSLSocket;
import java.io.IOException;

public class Client {

    private User user;
    private Credentials credentials;
    private TcpClientManager tcpManager;

    public Client(User user, Credentials credentials, String ipToConnect, int listenPort){
        this.user = user;
        this.credentials = credentials;
        tcpManager = new TcpClientManager(ipToConnect, listenPort);
    }

    public void initialize() throws IOException{
        tcpManager.initialize();
        sendKey();
    }

    public User getUser(){ return this.user; }
    public TcpClientManager getTcpManager(){ return this.tcpManager; }
    public Credentials getCredentials(){ return this.credentials; }

    private void sendKey(){
        AuthenticationSecretPacket asPacket = new AuthenticationSecretPacket(credentials.getSecretKey());
        getTcpManager().getConnection().sendPacket(asPacket);
    }

}
