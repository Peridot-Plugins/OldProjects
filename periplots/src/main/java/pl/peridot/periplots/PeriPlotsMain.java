package pl.peridot.periplots;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pl.peridot.periplots.data.Config;
import pl.peridot.periplots.listener.PlayerInteractListener;
import pl.peridot.periplots.listener.SignChangeListener;

import java.time.format.DateTimeFormatter;

public class PeriPlotsMain extends JavaPlugin {

    private static PeriPlotsMain instance;

    public static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @Override
    public void onEnable(){
        instance = this;
        saveDefaultConfig();
        Config.load();

        Bukkit.getPluginManager().registerEvents(new SignChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
    }

    public static PeriPlotsMain getInstance() {
        return instance;
    }
}
