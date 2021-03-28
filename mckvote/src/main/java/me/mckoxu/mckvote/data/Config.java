package me.mckoxu.mckvote.data;

import me.mckoxu.mckvote.MCKVote;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;

public class Config {
    public static String noperm;
    public static String correctUsagePlayer;
    public static List<String> correctUsageAdmin;

    public static void load(){
        ConfigurationSection config = MCKVote.getInst().getConfig();
        noperm = config.getString("messages.noperm");
        correctUsagePlayer = config.getString("messages.correctusageplayer");
        correctUsageAdmin = config.getStringList("messages.correusageadmin");
    }
}
