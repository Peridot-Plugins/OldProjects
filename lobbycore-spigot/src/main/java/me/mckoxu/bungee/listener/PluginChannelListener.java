package me.mckoxu.bungee.listener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.mckoxu.bungee.connector.GlobalBungeeServer;
import me.mckoxu.bungee.manager.ServerManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.net.InetSocketAddress;
import java.util.Arrays;

public class PluginChannelListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("PlayerCount")) {
            String server = in.readUTF();
            int playercount = in.readInt();
            ServerManager.createServer(server).setPlayerCount(playercount);
        }
        if (subchannel.equals("ServerIP")) {
            String server = in.readUTF();
            String ip = in.readUTF();
            int port = in.readUnsignedShort();
            ServerManager.createServer(server).setAddress(new InetSocketAddress(ip, port));
        }
        if (subchannel.equals("PlayerList")) {
            String server = in.readUTF();
            String[] playerList = in.readUTF().split(", ");
            ServerManager.createServer(server).setPlayerList(Arrays.asList(playerList));
        }
        if (subchannel.equals("GetServers")) {
            String[] serverrList = in.readUTF().split(", ");
            GlobalBungeeServer.setBungeeServers(Arrays.asList(serverrList));
        }
    }

}
