package net.njay.mesigi.packet.auth;

import net.njay.serverinterconnect.packet.Packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class AuthenticationSecretPacket extends Packet {

    private String secret;

    public AuthenticationSecretPacket(String secret){
        this.secret = secret;
    }

    public String getSecret(){
        return this.secret;
    }

    @Override
    public void readPacketContent(DataInputStream dataInputStream) throws IOException {
        this.secret = Packet.readString(dataInputStream);
    }

    @Override
    public void writePacketContent(DataOutputStream dataOutputStream) throws IOException {
        Packet.writeString(secret, dataOutputStream);
    }
}

