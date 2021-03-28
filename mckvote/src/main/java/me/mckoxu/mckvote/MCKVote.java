package me.mckoxu.mckvote;

import me.mckoxu.mckvote.commands.VoteCMD;
import me.mckoxu.mckvote.data.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class MCKVote extends JavaPlugin {
    private static MCKVote instance;

    public void onEnable(){
        instance = this;
        saveDefaultConfig();
        Config.load();
        getCommand("vote").setExecutor(new VoteCMD());
    }

    public static MCKVote getInst(){
        return instance;
    }
}
