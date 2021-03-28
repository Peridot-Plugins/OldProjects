package me.mckoxu.mcklosowanie.data;

import me.mckoxu.mcklosowanie.MCKLosowanie;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {

    public static String noPerm;
    public static String tooMuchPlayers;
    public static String notEnoughPlayers;
    public static String wrongNumber;
    public static String airInHand;
    public static String correctUsage;
    public static String start;
    public static String end;
    public static String win;
    public static int maxPlayers;
    public static int minPlayersOnline;
    public static int drawLong;
    public static List<Integer> endNotification;

    public static void load(){
        FileConfiguration config = MCKLosowanie.getInstance().getConfig();
        noPerm = config.getString("messages.noperm");
        tooMuchPlayers = config.getString("messages.toomuchplayers");
        notEnoughPlayers = config.getString("messages.notenoughplayers");
        wrongNumber = config.getString("messages.wrongnumber");
        airInHand = config.getString("messages.airinhand");
        correctUsage = config.getString("messages.correctusage");
        start = config.getString("messages.start");
        end = config.getString("messages.end");
        win = config.getString("messages.win");
        maxPlayers = config.getInt("maxplayers");
        minPlayersOnline = config.getInt("minplayersonline");
        drawLong = config.getInt("drawlong");
        endNotification = config.getIntegerList("endnotification");
    }

}
