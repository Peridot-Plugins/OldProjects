package me.mckoxu.bungee.connector;

import org.bukkit.plugin.Plugin;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GlobalBungeeServer {

    private static Plugin plugin;

    private static List<String> bungeeServers = new ArrayList<>();

    public GlobalBungeeServer(Plugin plugin) {
        GlobalBungeeServer.plugin = plugin;
        checkBungeeServers();
    }

    public static void sendMessage(String player, String message) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(array);

        try {
            output.writeUTF("Message");
            output.writeUTF(player);
            output.writeUTF(message);
        } catch (IOException ignored) {
        }

        plugin.getServer().sendPluginMessage(plugin, "BungeeCord", array.toByteArray());
    }

    public static void kicPlayer(String player, String reason) {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(array);

        try {
            output.writeUTF("KickPlayer");
            output.writeUTF(player);
            output.writeUTF(reason);
        } catch (IOException ignored) {
        }

        plugin.getServer().sendPluginMessage(plugin, "BungeeCord", array.toByteArray());
    }

    public static void checkBungeeServers() {
        ByteArrayOutputStream array = new ByteArrayOutputStream();
        DataOutputStream output = new DataOutputStream(array);

        try {
            output.writeUTF("GetServers");
        } catch (IOException ignored) {
        }

        plugin.getServer().sendPluginMessage(plugin, "BungeeCord", array.toByteArray());
    }

    public static List<String> getBungeeServers() {
        return bungeeServers;
    }

    public static void setBungeeServers(List<String> bungeeServers) {
        GlobalBungeeServer.bungeeServers = bungeeServers;
    }

}
