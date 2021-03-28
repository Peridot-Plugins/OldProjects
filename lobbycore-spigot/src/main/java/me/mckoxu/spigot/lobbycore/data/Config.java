package me.mckoxu.spigot.lobbycore.data;

import me.mckoxu.spigot.lobbycore.LobbyCore;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

public class Config {

    //MESSAGES
    public static String noPerm;
    public static String setLobby;
    public static String correctUsage;
    //WEATHER
    public static boolean forceWeatherEnabled;
    public static String forceWeatherType;
    //TIME
    public static boolean forceTimeEnabled;
    public static int forceTime;
    //WORLDS
    public static World lobbyWorld;
    //STATUS
    public static String on;
    public static String off;

    public static void loadConfig() {
        FileConfiguration config = LobbyCore.getInst().getConfig();

        noPerm = config.getString("messages.noperm");
        setLobby = config.getString("messages.setlobby");
        correctUsage = config.getString("messages.correctusage");

        forceWeatherEnabled = config.getBoolean("forceweather.enabled");
        forceWeatherType = config.getString("forceweather.type");

        forceTimeEnabled = config.getBoolean("forcetime.enabled");
        forceTime = config.getInt("forcetime.time");

        lobbyWorld = Bukkit.getWorld(config.getString("lobbyworld"));

        on = config.getString("messaages.status.on");
        off = config.getString("messaages.status.off");
    }

}
