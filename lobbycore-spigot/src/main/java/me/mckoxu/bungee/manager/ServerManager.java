package me.mckoxu.bungee.manager;

import me.mckoxu.bungee.connector.BungeeServer;
import me.mckoxu.bungee.connector.GlobalBungeeServer;
import me.mckoxu.bungee.listener.PluginChannelListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class ServerManager {

    public static int autoUpdateTime = 300;
    private static final List<BungeeServer> servers = new ArrayList<>();
    private static Plugin instance;

    public ServerManager(Plugin plugin) {
        instance = plugin;

        plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, "BungeeCord");
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, "BungeeCord", new PluginChannelListener());

        new GlobalBungeeServer(plugin);
        GlobalBungeeServer.checkBungeeServers();

        runUpdate();
    }

    /*
        Server List
     */

    public static BungeeServer createServer(String target) {
        BungeeServer server = null;
        for (BungeeServer result : servers) {
            if (result.getName().equalsIgnoreCase(target)) {
                server = result;
            }
        }
        if (server == null) {
            server = new BungeeServer(target, instance);
        }
        return server;
    }

    public static void addServer(BungeeServer server) {
        if (!servers.contains(server)) servers.add(server);
    }

    /*
        Server Info
     */

    public static void checkServerInfo(BungeeServer server) {
        server.checkAddress();
        server.checkPlayerCount();
        server.checkOnlinePlayers();
        server.checkStauts();
    }

    /*
        Autoupdate
     */

    public static void runUpdate() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, new Runnable() {
            @Override
            public void run() {
                GlobalBungeeServer.checkBungeeServers();
                GlobalBungeeServer.getBungeeServers().forEach(server -> {
                    checkServerInfo(ServerManager.createServer(server));
                });
            }
        }, 0, autoUpdateTime);
    }

}
