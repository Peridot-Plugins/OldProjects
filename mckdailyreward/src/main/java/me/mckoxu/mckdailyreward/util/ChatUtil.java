package me.mckoxu.mckdailyreward.util;

import org.bukkit.ChatColor;

public class ChatUtil {
    public static String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
