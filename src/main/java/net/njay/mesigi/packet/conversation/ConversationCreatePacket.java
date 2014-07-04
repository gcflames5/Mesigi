package net.njay.mesigi.packet.conversation;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.njay.mesigi.client.message.Conversation;
import net.njay.serverinterconnect.packet.Packet;

public class ConversationCreatePacket extends Packet{

    private String conversationId;
    private Conversation conversation;

    public ConversationCreatePacket() {}

    public ConversationCreatePacket(String conversationId, Conversation conversation){
        this.conversationId = conversationId;
        this.conversation = conversation;
    }

    @Override
    public void readPacketContent(DataInputStream input) throws IOException {
        conversationId = Packet.readString(input);
        conversation = new Conversation();
        conversation.readFromStream(input);
    }

    @Override
    public void writePacketContent(DataOutputStream output) throws IOException {
        Packet.writeString(conversationId, output);
        conversation.writeToStream(output);
    }
}
