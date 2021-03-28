package me.mckoxu.bungeecord.lobbycore.util;

import me.mckoxu.bungeecord.lobbycore.LobbyCore;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.Title;
import net.md_5.bungee.api.chat.TextComponent;

public class TitleUtil {
    public static Title createTitle(String titleMessage, String subtitleMessage){
        Title title = ProxyServer.getInstance().createTitle();
        title.title(new TextComponent(ChatUtil.color(titleMessage)));
        title.subTitle(new TextComponent(ChatUtil.color(subtitleMessage)));
        title.stay(40);
        return title;
    }
}
