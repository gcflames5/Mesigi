package net.njay.mesigi;

import net.njay.customevents.event.Event;
import net.njay.customevents.event.EventHandler;
import net.njay.customevents.event.Listener;
import net.njay.mesigi.client.Client;
import net.njay.mesigi.client.auth.Credentials;
import net.njay.mesigi.client.message.Message;
import net.njay.mesigi.client.user.User;
import net.njay.mesigi.client.user.status.UserStatus;
import net.njay.mesigi.packet.auth.AuthenticationPacket;
import net.njay.mesigi.packet.auth.AuthenticationRequestPacket;
import net.njay.mesigi.packet.auth.AuthenticationSecretPacket;
import net.njay.mesigi.packet.conversation.ConversationCreatePacket;
import net.njay.mesigi.packet.message.MessageSendPacket;
import net.njay.mesigi.packet.user.UserStateChangePacket;
import net.njay.mesigi.server.Server;
import net.njay.mesigi.util.cmd.CmdUtil;
import net.njay.mesigi.util.log.LogLevel;
import net.njay.mesigi.util.log.ReflectionLogger;
import net.njay.mesigi.util.upnp.Upnp;
import net.njay.mesigi.util.upnp.UpnpManager;
import net.njay.mesigi.util.upnp.exception.CleanPortNotFoundException;
import net.njay.mesigi.util.upnp.exception.GatewayNotFoundException;
import net.njay.serverinterconnect.event.PacketRecievedEvent;
import net.njay.serverinterconnect.packet.Packet;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nick on 7/3/14.
 */
public class Mesigi implements Listener {

    public static void main(String args[]) {
        hookCleanupThread();
        try{
            Event.addListener(new Mesigi());
            for (Listener l : Event.getListeners())
                ReflectionLogger.log(LogLevel.INFO, l.getClass().toString());
            registerPackets(MessageSendPacket.class, AuthenticationPacket.class, AuthenticationRequestPacket.class, AuthenticationSecretPacket.class, ConversationCreatePacket.class,
                    ConversationCreatePacket.class, UserStateChangePacket.class);
            //ReflectionLogger.filter(LogLevel.INFO, ReflectionLogger.FilterMode.WHITELIST, Mesigi.class);

            int portForward = CmdUtil.getIntAttribute(args, "-pf", "-upnp");
            try{
                Upnp.forward(portForward, portForward);
            }catch(GatewayNotFoundException e){
                ReflectionLogger.log(LogLevel.WARNING, "No valid Upnp Gateway found!");
            }catch(CleanPortNotFoundException e){
                ReflectionLogger.log(LogLevel.WARNING,  "Failed to find clean port!");
            }

            String mode = CmdUtil.getStringAttribute(args, "-m", "-mode");
            if (mode.contains("s"))
                startServer(args);
            else if (mode.contains("c"))
                startClient(args);
            else
                System.out.println("Unknown mode: " + mode);


        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static void hookCleanupThread() {
        Runtime.getRuntime().addShutdownHook(new CleanupThread());
    }

    public static void startClient(String args[]) throws IOException {
        Credentials cred = new Credentials("username", "password", "uuid");
        final Client client = new Client(new User("test", UserStatus.ONLINE), cred,
                "127.0.0.1", CmdUtil.getIntAttribute(args, "-port"));
        client.initialize();
        client.sendPacket(new MessageSendPacket(
           new Message("1", new User("test", UserStatus.ONLINE), "2", new ArrayList<User>())));
    }

    public static void startServer(String args[]){
        Server server = new Server(CmdUtil.getIntAttribute(args, "-p", "-port"));
    }

    public static void registerPackets(Class<? extends Packet>... classes)  {
        for (Class<? extends Packet> clazz : classes)
            Packet.registerPacket(clazz);
    }

    public static class CleanupThread extends Thread{
        @Override
        public void run(){
            UpnpManager.cleanup();
        }
    }

    @EventHandler
    public void onRecieve(PacketRecievedEvent e){
        ReflectionLogger.log(LogLevel.INFO, "Packet recieved: " + e.getPacket().getClass().getName());
    }
}
