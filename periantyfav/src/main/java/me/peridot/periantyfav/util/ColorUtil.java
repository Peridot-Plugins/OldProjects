package me.peridot.periantyfav.util;

import org.bukkit.ChatColor;

public class ColorUtil {

    public static String color(String string){
        return ChatColor.translateAlternateColorCodes('&', string);
    }

}
