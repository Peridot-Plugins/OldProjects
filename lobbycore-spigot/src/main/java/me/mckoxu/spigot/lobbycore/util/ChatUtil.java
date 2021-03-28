package me.mckoxu.spigot.lobbycore.util;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ChatUtil {

    public static String color(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static ArrayList<String> color(List<String> s) {
        ArrayList<String> al = new ArrayList<String>();
        for (String msg : s) {
            al.add(color(msg));
        }
        return al;
    }

}
