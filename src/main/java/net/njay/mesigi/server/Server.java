package net.njay.mesigi.server;

import net.njay.mesigi.server.user.UserBank;
import net.njay.serverinterconnect.connection.TcpSocketFactory;
import net.njay.serverinterconnect.server.TcpServerManager;

import javax.net.ssl.SSLServerSocket;

public class Server {

    private SSLServerSocket serverSocket;
    private TcpServerManager manager;
    private UserBank userBank;

    public Server(int port){
        serverSocket = TcpSocketFactory.generateServerSocket(port);
        manager = new TcpServerManager(serverSocket);
        userBank = new UserBank();
    }

    public SSLServerSocket getServerSocket(){ return this.serverSocket; }
    public TcpServerManager getTcpManager(){ return this.manager; }
    public UserBank getUserBank(){ return this.userBank; }

}
