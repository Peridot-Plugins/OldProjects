package me.peridot.periantyfav;

import me.peridot.periantyfav.configuration.Config;
import me.peridot.periantyfav.listeners.InventoryCreativeListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PeriAntyFav extends JavaPlugin {

    private Config pluginConfig;

    @Override
    public void onEnable() {
        pluginConfig = new Config(this);
        pluginConfig.loadConfiguration();

        Bukkit.getPluginManager().registerEvents(new InventoryCreativeListener(this), this);
    }

    public Config getPluginConfig() {
        return pluginConfig;
    }
}
