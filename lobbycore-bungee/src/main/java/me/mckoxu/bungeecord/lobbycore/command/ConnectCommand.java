package me.mckoxu.bungeecord.lobbycore.command;

import me.mckoxu.bungeecord.lobbycore.LobbyCore;
import me.mckoxu.bungeecord.lobbycore.util.ChatUtil;
import me.mckoxu.bungeecord.lobbycore.util.TitleUtil;
import me.mckoxu.bungeecord.lobbycore.util.Util;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ConnectCommand extends Command {
    public ConnectCommand() {
        super("connect", "lobbycore.connect", "polacz", "server");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (commandSender instanceof ProxiedPlayer) {
            ProxiedPlayer player = (ProxiedPlayer) commandSender;
            if(strings.length >= 1) {
                String stringServer = strings[0].toLowerCase();
                ServerInfo server = Util.getServer(stringServer.toLowerCase());
                TitleUtil.createTitle(
                        LobbyCore.getConfig().getString("connect.connecting.title")
                                .replace("{SERVER}", stringServer.toUpperCase()),
                        LobbyCore.getConfig().getString("connect.connecting.subtitle")
                                .replace("{SERVER}", stringServer.toUpperCase()))
                        .send(player);
                    if (Util.pingServer(server)) {
                        if(player.hasPermission("lobbycore.connect."+stringServer) || player.hasPermission("lobbycore.connect.all") || server.getName().equalsIgnoreCase(LobbyCore.getConfig().getString("servers.lobby"))) {
                            if (!player.getServer().getInfo().getName().equalsIgnoreCase(server.getName())) {
                                player.connect(server);
                            } else {
                                TitleUtil.createTitle(
                                        LobbyCore.getConfig().getString("connect.alreadyonserver.title")
                                                .replace("{SERVER}", server.getName().toUpperCase()),
                                        LobbyCore.getConfig().getString("connect.alreadyonserver.subtitle")
                                                .replace("{SERVER}", server.getName().toUpperCase()))
                                        .send(player);
                            }
                        } else{
                            TitleUtil.createTitle(
                                    LobbyCore.getConfig().getString("connect.noperm.title")
                                            .replace("{SERVER}", server.getName().toUpperCase()),
                                    LobbyCore.getConfig().getString("connect.noperm.subtitle")
                                            .replace("{SERVER}", server.getName().toUpperCase()))
                                    .send(player);
                        }
                    } else {
                        TitleUtil.createTitle(
                                LobbyCore.getConfig().getString("connect.lose.title")
                                        .replace("{SERVER}", stringServer.toUpperCase()),
                                LobbyCore.getConfig().getString("connect.lose.subtitle")
                                        .replace("{SERVER}", stringServer.toUpperCase()))
                                .send(player);
                    }
            } else{
                StringBuilder servers = new StringBuilder();
                for(ServerInfo server : ProxyServer.getInstance().getServers().values()){
                    if(Util.pingServer(server)) {
                        if (player.hasPermission("lobbycore.connect." + server.getName()) || player.hasPermission("lobbycore.connect.all") || server.getName().equalsIgnoreCase(LobbyCore.getConfig().getString("servers.lobby"))) {
                            if(!server.getName().equalsIgnoreCase(player.getServer().getInfo().getName())) {
                                servers.append(", ");
                                servers.append(server.getName().toLowerCase());
                            }
                        }
                    }
                }
                if(servers.toString().isEmpty()){
                    servers.append(LobbyCore.getConfig().getString("messages.none"));
                }
                for(String string : LobbyCore.getConfig().getStringList("messages.connectcorrectusage")){
                    player.sendMessage(new TextComponent(ChatUtil.color(string.replace("{SERVERS}", servers.toString().replaceFirst(", ", "")))));
                }
            }
        } else {
            commandSender.sendMessage(new TextComponent(ChatColor.RED + "This command can only be run by a player!"));
        }
    }
}

