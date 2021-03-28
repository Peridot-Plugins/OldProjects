package me.mckoxu.bungee.connector;

import me.mckoxu.bungee.manager.ServerManager;
import me.mckoxu.spigot.lobbycore.util.Util;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class BungeeServer {

    private final String name;
    /*
      Object
    */
    private final Plugin plugin;
    private InetSocketAddress address;
    private int playerCount;
    private List playerList = new ArrayList();
    private boolean status;

    public BungeeServer(String server, Plugin plugin) {
        this.name = server;
        this.plugin = plugin;
        ServerManager.addServer(this);
    }

    /*
      Bungeecord
    */
    public void connect(Player player) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(array);

        try {
            output.writeUTF("Connect");
            output.writeUTF(this.name);
        } catch (IOException ignored) {
        }

        player.sendPluginMessage(plugin, "BungeeCord", array.toByteArray());
    }

    public void checkAddress() {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(array);

        try {
            output.writeUTF("ServerIP");
            output.writeUTF(this.name);
        } catch (IOException ignored) {
        }

        plugin.getServer().sendPluginMessage(plugin, "BungeeCord", array.toByteArray());
    }

    public void checkPlayerCount() {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(array);

        try {
            output.writeUTF("PlayerCount");
            output.writeUTF(this.name);
        } catch (IOException ignored) {
        }

        plugin.getServer().sendPluginMessage(plugin, "BungeeCord", array.toByteArray());
    }

    public void checkOnlinePlayers() {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(array);

        try {
            output.writeUTF("PlayerList");
            output.writeUTF(this.name);
        } catch (IOException ignored) {
        }

        plugin.getServer().sendPluginMessage(plugin, "BungeeCord", array.toByteArray());
    }

    public void checkStauts() {
        if (this.address == null) setStatus(false);
        setStatus(Util.pingServer(this.address));
    }

    public String getName() {
        return name;
    }

    public InetSocketAddress getAddress() {
        if (!getStatus()) return null;
        return address;
    }

    public void setAddress(InetSocketAddress address) {
        this.address = address;
    }

    public int getPlayerCount() {
        if (!getStatus()) return 0;
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public List getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List playerList) {
        this.playerList = playerList;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
