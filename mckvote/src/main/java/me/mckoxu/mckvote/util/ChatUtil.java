package me.mckoxu.mckvote.util;

import org.bukkit.ChatColor;

public class ChatUtil {
    public static String color(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
