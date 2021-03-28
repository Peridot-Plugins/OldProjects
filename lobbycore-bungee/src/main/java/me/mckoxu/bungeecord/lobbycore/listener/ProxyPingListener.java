package me.mckoxu.bungeecord.lobbycore.listener;

import me.mckoxu.bungeecord.lobbycore.LobbyCore;
import me.mckoxu.bungeecord.lobbycore.util.ChatUtil;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.EventHandler;

public class ProxyPingListener implements Listener {
    @EventHandler
    public void onPing(ProxyPingEvent event){
        Configuration config = LobbyCore.getConfig();
        ServerPing serverPing = event.getResponse();
        serverPing.setDescription(ChatUtil.color(ChatUtil.replace(config.getString("serverlist.motd"))
               .replace("{ONLINE}", String.valueOf(ProxyServer.getInstance().getOnlineCount()))));
        serverPing.setVersion(new ServerPing.Protocol(ChatUtil.color(ChatUtil.replace(config.getString("serverlist.version"))
                .replace("{ONLINE}", String.valueOf(ProxyServer.getInstance().getOnlineCount()))), -1));
        int i = 0;
        ServerPing.PlayerInfo[] players = new ServerPing.PlayerInfo[config.getStringList("serverlist.players").size()];
        for(String player : config.getStringList("serverlist.players")){
            players[i] = new ServerPing.PlayerInfo(ChatUtil.color(ChatUtil.replace(player))
                    .replace("{ONLINE}", String.valueOf(ProxyServer.getInstance().getOnlineCount())), String.valueOf(i));
            i++;
        }
        serverPing.setPlayers(new ServerPing.Players(0, config.getStringList("serverlist.players").size(), players));
        event.setResponse(serverPing);
    }
}
