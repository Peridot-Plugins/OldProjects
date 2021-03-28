package me.mckoxu.spigot.lobbycore;

import me.mckoxu.bungee.manager.ServerManager;
import me.mckoxu.spigot.lobbycore.command.CheckCommand;
import me.mckoxu.spigot.lobbycore.command.SetLobbyCommand;
import me.mckoxu.spigot.lobbycore.data.Config;
import me.mckoxu.spigot.lobbycore.listener.PlayerInteractListener;
import me.mckoxu.spigot.lobbycore.listener.PlayerJoinListener;
import me.mckoxu.spigot.lobbycore.listener.PlayerQuitListener;
import me.mckoxu.spigot.lobbycore.listener.WeatherChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class LobbyCore extends JavaPlugin {

    private static LobbyCore instance;

    public static void setForceWeather() {
        if (Config.forceWeatherEnabled) {
            if (Config.forceWeatherType.equalsIgnoreCase("sun")) {
                Config.lobbyWorld.setThundering(false);
                Config.lobbyWorld.setStorm(false);
            } else if (Config.forceWeatherType.equalsIgnoreCase("rain")) {
                Config.lobbyWorld.setThundering(false);
                Config.lobbyWorld.setStorm(true);
            } else if (Config.forceWeatherType.equalsIgnoreCase("storm")) {
                Config.lobbyWorld.setThundering(true);
                Config.lobbyWorld.setStorm(true);
            }
        }
    }

    public static void setForceTime() {
        if (Config.forceTimeEnabled) {
            Config.lobbyWorld.setTime(Config.forceTime);
            Config.lobbyWorld.setGameRuleValue("doDaylightCycle", "false");
        }
    }

    public static LobbyCore getInst() {
        return instance;
    }

    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Config.loadConfig();

        new ServerManager(this);
        me.mckoxu.spigot.lobbycore.manager.ServerManager.load();

        ServerManager.autoUpdateTime = getConfig().getInt("update.time");

        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(), this);
        Bukkit.getPluginManager().registerEvents(new WeatherChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getCommand("setlobby").setExecutor(new SetLobbyCommand());
        getCommand("check").setExecutor(new CheckCommand());
        setForceWeather();
        setForceTime();
    }

}
