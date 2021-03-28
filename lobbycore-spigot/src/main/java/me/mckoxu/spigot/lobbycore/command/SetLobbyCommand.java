package me.mckoxu.spigot.lobbycore.command;

import me.mckoxu.spigot.lobbycore.LobbyCore;
import me.mckoxu.spigot.lobbycore.data.Config;
import me.mckoxu.spigot.lobbycore.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class SetLobbyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setlobby")) {
            FileConfiguration config = LobbyCore.getInst().getConfig();
            if (s.hasPermission("lobbycore.spigot.setlobby")) {
                if (s instanceof Player) {
                    Player player = (Player) s;
                    Location location = player.getLocation();
                    config.set("data.lobby.x", location.getX());
                    config.set("data.lobby.y", location.getY());
                    config.set("data.lobby.z", location.getZ());
                    config.set("data.lobby.world", location.getWorld().getName());
                    config.set("data.lobby.yaw", location.getYaw());
                    config.set("data.lobby.pitch", location.getPitch());
                    LobbyCore.getInst().saveConfig();
                    player.sendMessage(ChatUtil.color(Config.setLobby.replace("{WORLD}", location.getWorld().getName())
                            .replace("{X}", String.valueOf(location.getX()))
                            .replace("{Y}", String.valueOf(location.getY()))
                            .replace("{Z}", String.valueOf(location.getZ()))));
                } else {
                    Bukkit.getLogger().info("[LobbyCore] Tylko gracz na serwerze moze uzyc tej okmendy!");
                }
            } else {
                s.sendMessage(ChatUtil.color(Config.noPerm));
            }
        }
        return true;
    }

}
