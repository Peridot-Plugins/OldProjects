package me.mckoxu.mckvote.commands;

import me.mckoxu.mckvote.data.Config;
import me.mckoxu.mckvote.util.ChatUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class VoteCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("vote")){
            if(sender.hasPermission("mckvote.cmd")){
                if(args.length >= 1){
                    if(args[0].equalsIgnoreCase("admin")){

                    } else if(args[0].equalsIgnoreCase("yes") || args[0].equalsIgnoreCase("tak")){

                    } else if(args[0].equalsIgnoreCase("no") || args[0].equalsIgnoreCase("nie")){

                    } else{
                        if(sender.hasPermission("mckvote.cmd.admin")){
                            for(String string : Config.correctUsageAdmin){
                                sender.sendMessage(ChatUtil.color(string));
                            }
                        } else{
                            sender.sendMessage(ChatUtil.color(Config.correctUsagePlayer));
                        }
                    }
                } else {
                    sender.sendMessage(ChatUtil.color(""));
                }
            } else{
                sender.sendMessage(ChatUtil.color(Config.noperm));
            }
        }
        return true;
    }
}
