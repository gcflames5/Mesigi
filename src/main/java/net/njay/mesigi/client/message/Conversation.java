package net.njay.mesigi.client.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import net.njay.mesigi.client.user.User;
import net.njay.serverinterconnect.packet.Packet;
import net.njay.serverinterconnect.packet.Transferable;

public class Conversation extends Transferable{

    private String id;
    private List<User> participants;
    private List<Message> messages;

    public Conversation() { }

    public Conversation(int id, List<User> participants, List<Message> messages){
        this.participants = participants;
        this.messages = messages;
    }

    public void addMessage(Message message){
        this.messages.add(message);
        //UPDATE UI
    }

    public String getId(){ return this.id; }
    public List<User> getParticipants(){ return this.participants; }
    public List<Message> getMessages(){ return this.messages; }

    @Override
    public void writeToStream(DataOutputStream output) throws IOException {
        Packet.writeString(id, output);
        Packet.writeList(participants, output);
        Packet.writeList(messages, output);
    }

    @Override
    public void readFromStream(DataInputStream input) throws IOException {
        id = Packet.readString(input);
        participants = Packet.convertList(Packet.readList(User.class, input));
        messages = Packet.convertList(Packet.readList(Message.class, input));
    }

}

