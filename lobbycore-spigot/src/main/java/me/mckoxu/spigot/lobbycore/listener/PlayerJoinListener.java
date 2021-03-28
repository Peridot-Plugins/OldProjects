package me.mckoxu.spigot.lobbycore.listener;

import me.mckoxu.bungee.connector.GlobalBungeeServer;
import me.mckoxu.bungee.manager.ServerManager;
import me.mckoxu.spigot.lobbycore.LobbyCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        FileConfiguration config = LobbyCore.getInst().getConfig();
        Location location = new Location(Bukkit.getWorld(config.getString("data.lobby.world")), config.getDouble("data.lobby.x"), config.getDouble("data.lobby.y"), config.getDouble("data.lobby.z"));
        location.setYaw((float) config.getDouble("data.lobby.yaw"));
        location.setPitch((float) config.getDouble("data.lobby.pitch"));
        player.teleport(location);
        event.setJoinMessage(null);
        GlobalBungeeServer.checkBungeeServers();
        GlobalBungeeServer.getBungeeServers().forEach(server -> {
            ServerManager.checkServerInfo(ServerManager.createServer(server));
        });
    }

}
