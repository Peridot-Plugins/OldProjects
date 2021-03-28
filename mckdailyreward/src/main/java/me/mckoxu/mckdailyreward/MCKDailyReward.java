package me.mckoxu.mckdailyreward;

import me.mckoxu.mckdailyreward.command.TakeCmd;
import me.mckoxu.mckdailyreward.util.ChatUtil;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class MCKDailyReward extends JavaPlugin {

    public static List<String> cmds;
    public static String noperm;
    public static String takenreward;
    public static String rewardtake;
    public static MCKDailyReward instance;

    public void onEnable(){
        instance = this;
        saveDefaultConfig();
        cmds = getConfig().getStringList("takecmds");
        noperm = ChatUtil.color(getConfig().getString("noperm"));
        takenreward = ChatUtil.color(getConfig().getString("takenreward"));
        rewardtake = ChatUtil.color(getConfig().getString("rewardtake"));
        getCommand("reward").setExecutor(new TakeCmd());
    }

    public static MCKDailyReward getInst(){
        return instance;
    }
}
