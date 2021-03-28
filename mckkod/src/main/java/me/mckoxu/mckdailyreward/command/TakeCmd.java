package me.mckoxu.mckdailyreward.command;

import me.mckoxu.mckdailyreward.MCKDailyReward;
import me.mckoxu.mckdailyreward.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class TakeCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("reward")){
            if(sender instanceof Player){
                Player player = (Player) sender;
                if(player.hasPermission("mckdailyreward.take")) {
                    Long l = MCKDailyReward.getInst().getConfig().getLong("players."+player.getName()+".time");
                    if (l <= 0 || System.currentTimeMillis() >= l) {
                        MCKDailyReward.cmds.forEach(cmd -> {
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.replace("%player%", player.getName()));
                        });
                        player.sendMessage(MCKDailyReward.rewardtake);
                        MCKDailyReward.getInst().getConfig().set("players."+player.getName()+".time", System.currentTimeMillis()+86400000);
                        MCKDailyReward.getInst().saveConfig();
                    } else {
                        player.sendMessage(MCKDailyReward.takenreward.replace("%time%", Util.convertTime((int) Math.floor((l-System.currentTimeMillis())/1000))));
                    }
                } else{
                    player.sendMessage(MCKDailyReward.noperm);
                }
            }
        }
        return true;
    }
}
