package me.mckoxu.bungeecord.lobbycore.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtil {
    public static String color(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String replace(String string) {
        Matcher m = Pattern.compile("\\{SERVER\\-[a-zA-Z0-9_]*\\-ONLINE\\}").matcher(string);
        while(m.find()) {
            String raw = m.group();
            String match = raw.substring(8);
            match = match.subSequence(0, match.indexOf("-")).toString();
            ServerInfo server = Util.getServer(match);
            string = string.replaceFirst(Pattern.quote(raw), String.valueOf(server.getPlayers().size()));
        }
        return string;
    }

}
