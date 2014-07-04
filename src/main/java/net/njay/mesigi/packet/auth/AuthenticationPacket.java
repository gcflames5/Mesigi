package net.njay.mesigi.packet.auth;


import net.njay.serverinterconnect.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AuthenticationPacket extends Packet {

    private String username;
    private String sessionID;

    public AuthenticationPacket() {}

    public AuthenticationPacket(String username, String sessionID){
        this.username = username;
        this.sessionID = sessionID;
    }

    public String getUsername(){ return this.username; }
    public String getSessionId(){ return this.sessionID; }

    @Override
    public void readPacketContent(DataInputStream input) throws IOException {
        username = Packet.readString(input);
        sessionID = Packet.readString(input);
    }

    @Override
    public void writePacketContent(DataOutputStream output) throws IOException {
        Packet.writeString(username, output);
        Packet.writeString(sessionID, output);
    }

}
