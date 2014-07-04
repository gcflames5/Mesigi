package net.njay.mesigi.server;

import net.njay.customevents.event.Event;
import net.njay.mesigi.server.listener.AuthenticationListener;
import net.njay.mesigi.server.user.UserBank;
import net.njay.serverinterconnect.connection.TcpConnection;
import net.njay.serverinterconnect.connection.TcpSocketFactory;
import net.njay.serverinterconnect.server.TcpServerManager;

import javax.net.ssl.SSLServerSocket;

public class Server {

    private SSLServerSocket serverSocket;
    private AuthenticatedTcpServerManager manager;
    private UserBank userBank;

    public Server(int port){
        serverSocket = TcpSocketFactory.generateServerSocket(port);
        manager = new AuthenticatedTcpServerManager(this, serverSocket);
        userBank = new UserBank();
    }

    public SSLServerSocket getServerSocket(){ return this.serverSocket; }
    public TcpServerManager getTcpManager(){ return this.manager; }
    public UserBank getUserBank(){ return this.userBank; }

    public void terminateConnection(TcpConnection conn){
        manager.getConnections().remove(conn);
        conn.terminate();
    }

}
