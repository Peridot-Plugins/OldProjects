package me.mckoxu.spigot.lobbycore.listener;

import me.mckoxu.bungee.connector.GlobalBungeeServer;
import me.mckoxu.bungee.manager.ServerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        GlobalBungeeServer.checkBungeeServers();
        GlobalBungeeServer.getBungeeServers().forEach(server -> {
            ServerManager.checkServerInfo(ServerManager.createServer(server));
        });
    }

}
