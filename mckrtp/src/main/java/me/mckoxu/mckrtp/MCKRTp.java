package me.mckoxu.mckrtp;

import me.mckoxu.mckrtp.commands.RTpCMD;
import me.mckoxu.mckrtp.listeners.PlayerInteractListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class MCKRTp extends JavaPlugin {
    private static MCKRTp instance;
    public static List<Location> locations = new ArrayList<>();
    public static int minx;
    public static int minz;
    public static int maxx;
    public static int maxz;

    public void onEnable(){
        instance = this;
        saveDefaultConfig();
        loadButtons();
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getCommand("randomtp").setExecutor(new RTpCMD());
        minx = getConfig().getInt("data.min-x");
        minz = getConfig().getInt("data.min-z");
        maxx = getConfig().getInt("data.max-x");
        maxz = getConfig().getInt("data.max-z");
    }

    public void loadButtons(){
        List<String> buttons = getConfig().getStringList("buttons.list");
        for(String button : buttons) {
            String[] str = button.split(":");
            double x = 0;
            double y = 0;
            double z = 0;
            String world = str[0];
            try {
                x = Double.valueOf(str[1]);
            } catch (Exception ex) {
                x = 0D;
            }
            try {
                y = Double.valueOf(str[2]);
            } catch (Exception ex) {
                y = 0D;
            }
            try {
                z = Double.valueOf(str[3]);
            } catch (Exception ex) {
                z = 0D;
            }
            Location loc = new Location(Bukkit.getWorld(world), x, y, z);
            locations.add(loc);
        }
    }

    public static MCKRTp getInst(){
        return instance;
    }
}
