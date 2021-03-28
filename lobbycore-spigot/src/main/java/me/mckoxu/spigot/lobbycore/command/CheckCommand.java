package me.mckoxu.spigot.lobbycore.command;

import me.mckoxu.bungee.connector.BungeeServer;
import me.mckoxu.bungee.connector.GlobalBungeeServer;
import me.mckoxu.bungee.manager.ServerManager;
import me.mckoxu.spigot.lobbycore.data.Config;
import me.mckoxu.spigot.lobbycore.inventory.ServerSelectInventory;
import me.mckoxu.spigot.lobbycore.util.ChatUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("check")) {
            if (s.hasPermission("lobbycore.spigot.check")) {
                if (s instanceof Player) {
                    Player player = (Player) s;
                    if (args.length >= 1) {
                        String serverName = args[0];
                        ServerManager.checkServerInfo(ServerManager.createServer(serverName));
                        BungeeServer server = ServerManager.createServer(serverName);
                        GlobalBungeeServer.checkBungeeServers();
                        player.sendMessage("Nazwa serwera: " + server.getName());
                        player.sendMessage("Adres serwera: " + server.getAddress());
                        player.sendMessage("Status serwera: " + server.getStatus());
                        player.sendMessage("Liczba graczy online: " + server.getPlayerCount());
                        player.sendMessage("Gracze online: " + server.getPlayerList());
                        player.sendMessage("Serwery bungeecord: " + GlobalBungeeServer.getBungeeServers());
                        player.openInventory(ServerSelectInventory.getInventory(player));
                    } else {
                        player.sendMessage("Nie podales nazwy serwera!");
                    }
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

