package me.mckoxu.bungeecord.lobbycore.listener;

import me.mckoxu.bungeecord.lobbycore.LobbyCore;
import me.mckoxu.bungeecord.lobbycore.util.TitleUtil;
import me.mckoxu.bungeecord.lobbycore.util.Util;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ServerConnectListener implements Listener {
    @EventHandler
    public void onConnect(ServerConnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        ServerInfo server = event.getTarget();
        TitleUtil.createTitle(
                LobbyCore.getConfig().getString("connect.connecting.title")
                        .replace("{SERVER}", server.getName().toUpperCase()),
                LobbyCore.getConfig().getString("connect.connecting.subtitle")
                        .replace("{SERVER}", server.getName().toUpperCase()))
                .send(player);
        try {
            if (Util.pingServer(server)) {
                if (player.getServer() == null || !server.getName().equalsIgnoreCase(player.getServer().getInfo().getName())) {
                    TitleUtil.createTitle(
                            LobbyCore.getConfig().getString("connect.connect.title")
                                    .replace("{SERVER}", server.getName().toUpperCase())
                                    .replace("{MOTD}", server.getMotd())
                                    .replace("{PLAYER}", player.getName()),
                            LobbyCore.getConfig().getString("connect.connect.subtitle")
                                    .replace("{SERVER}", server.getName().toUpperCase())
                                    .replace("{MOTD}", server.getMotd())
                                    .replace("{PLAYER}", player.getName()))
                            .send(player);
                } else {
                    TitleUtil.createTitle(
                            LobbyCore.getConfig().getString("connect.alreadyonserver.title")
                                    .replace("{SERVER}", server.getName().toUpperCase()),
                            LobbyCore.getConfig().getString("connect.alreadyonserver.subtitle")
                                    .replace("{SERVER}", server.getName().toUpperCase()))
                            .send(player);
                }
            } else {
                TitleUtil.createTitle(
                        LobbyCore.getConfig().getString("connect.lose.title")
                                .replace("{SERVER}", server.getName().toUpperCase()),
                        LobbyCore.getConfig().getString("connect.lose.subtitle")
                                .replace("{SERVER}", server.getName().toUpperCase()))
                        .send(player);
            }
        } catch (Exception ex) {
        }
    }
}
