package net.njay.mesigi.client;

import net.njay.customevents.event.Event;
import net.njay.mesigi.client.auth.Credentials;
import net.njay.mesigi.client.listener.AuthenticationListener;
import net.njay.mesigi.client.user.User;
import net.njay.mesigi.packet.auth.AuthenticationSecretPacket;
import net.njay.serverinterconnect.client.TcpClientManager;
import net.njay.serverinterconnect.packet.Packet;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

public class Client {

    private User user;
    private Credentials credentials;
    private TcpClientManager tcpManager;
    private LinkedBlockingDeque<Packet> packetQueue;

    private boolean isInitialized;

    public Client(User user, Credentials credentials, String ipToConnect, int listenPort){
        this.user = user;
        this.credentials = credentials;
        tcpManager = new TcpClientManager(ipToConnect, listenPort);
        packetQueue = new LinkedBlockingDeque<Packet>();
        isInitialized = false;
    }

    public void initialize() throws IOException{
        tcpManager.initialize();
        sendKey();
        Event.addListener(new AuthenticationListener(this));
    }

    public void sendPacket(Packet packet){
        if (!isInitialized)
            packetQueue.addLast(packet);
        else
            getTcpManager().getConnection().sendPacket(packet);
    }

    public void flushQueue() throws InterruptedException {
        for (int i = 0; i < packetQueue.size(); i++)
            sendPacket(packetQueue.takeFirst());
        packetQueue.clear();
    }

    public User getUser(){ return this.user; }
    public TcpClientManager getTcpManager(){ return this.tcpManager; }
    public Credentials getCredentials(){ return this.credentials; }

    private void sendKey(){
        AuthenticationSecretPacket asPacket = new AuthenticationSecretPacket(credentials.getSecretKey());
        getTcpManager().getConnection().sendPacket(asPacket);
    }

    public void setInitialized(boolean initialized){ this.isInitialized = initialized; if (initialized) try { flushQueue(); } catch (InterruptedException e) { e.printStackTrace(); } }
    public boolean isInitialized(){ return this.isInitialized; }


}
