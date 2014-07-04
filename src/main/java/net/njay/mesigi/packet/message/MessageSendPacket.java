package net.njay.mesigi.packet.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.njay.mesigi.client.message.Message;
import net.njay.serverinterconnect.packet.Packet;

public class MessageSendPacket extends Packet{

    private Message message;

    public MessageSendPacket() {}

    public MessageSendPacket(Message message){
        this.message = message;
    }

    public Message getMessage(){ return this.message; }

    @Override
    public void readPacketContent(DataInputStream input) throws IOException {
        this.message = new Message();
        this.message.readFromStream(input);
    }

    @Override
    public void writePacketContent(DataOutputStream output) throws IOException {
        this.message.writeToStream(output);
    }
}
