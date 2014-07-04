package net.njay.mesigi.client.message;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import net.njay.mesigi.client.user.User;
import net.njay.serverinterconnect.packet.Packet;
import net.njay.serverinterconnect.packet.Transferable;

public class Message extends Transferable{

    private String conversationId;
    private User sender;
    private List<User> recipients;
    private String message;
    private Calendar timeSent;

    public Message() { }

    public Message(String id, User sender, String message, List<User> recipients){
        this.conversationId = id;
        this.sender = sender;
        this.message = message;
        this.timeSent = Calendar.getInstance();
        this.recipients = recipients;
    }

    public Message(String id, User sender, String message, User... recipients){
        this.conversationId = id;
        this.sender = sender;
        this.message = message;
        this.recipients = new ArrayList<User>();
        this.timeSent = Calendar.getInstance();
        for (User user : recipients)
            this.recipients.add(user);
    }

    public User getSender(){ return this.sender; }
    public List<User> getRecipients(){ return this.recipients; }
    public String getMessage(){ return this.message; }

    public void sentTimeSent(Calendar timeSent){ this.timeSent = timeSent; }
    public Calendar getTimeSent(){ return this.timeSent; }

    public void writeToStream(DataOutputStream output) throws IOException {
        Packet.writeString(conversationId, output);
        sender.writeToStream(output);
        Packet.writeList(recipients, output);
        Packet.writeString(message, output);
        output.writeInt(timeSent.get(Calendar.DAY_OF_MONTH));
        output.writeInt(timeSent.get(Calendar.MONTH));
        output.writeInt(timeSent.get(Calendar.YEAR));
    }

    public void readFromStream(DataInputStream input) throws IOException {
        conversationId = Packet.readString(input);
        sender = new User();
        sender.readFromStream(input);
        recipients = Packet.convertList(Packet.readList(User.class, input));
        message = Packet.readString(input);
        timeSent = Calendar.getInstance();
        timeSent.set(Calendar.DAY_OF_MONTH, input.readInt());
        timeSent.set(Calendar.MONTH, input.readInt());
        timeSent.set(Calendar.YEAR, input.readInt());
    }

}
