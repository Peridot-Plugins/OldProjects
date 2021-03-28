package me.mckoxu.mckrtp.commands;

import me.mckoxu.mckrtp.MCKRTp;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

public class RTpCMD implements CommandExecutor {

    public static ConfigurationSection config = MCKRTp.getInst().getConfig();

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("randomtp")){
            if(s instanceof Player) {
                Player p = (Player) s;
                if (p.hasPermission("rtp.cmd")) {
                    if(p.getTargetBlock(null, 4).getType() == Material.STONE_BUTTON){
                        if(args.length >= 1) {
                            if(args[0].equalsIgnoreCase("add")) {
                                Location loc = p.getTargetBlock(null, 4).getLocation();
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("info.setbutton").replace("{WORLD}", loc.getWorld().getName()))
                                        .replace("{X}", String.valueOf(loc.getX()))
                                        .replace("{Y}", String.valueOf(loc.getY()))
                                        .replace("{Z}", String.valueOf(loc.getZ())));
                                MCKRTp.locations.add(loc);
                                List<String> blocks = config.getStringList("buttons.list");
                                blocks.add(String.valueOf(loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ()));
                                config.set("buttons.list", blocks);
                                MCKRTp.getInst().saveConfig();
                            } else if(args[0].equalsIgnoreCase("remove")) {
                                Location loc = p.getTargetBlock(null, 4).getLocation();
                                List<String> blocks = config.getStringList("buttons.list");
                                String ss = loc.getWorld().getName() + ":" + loc.getX() + ":" + loc.getY() + ":" + loc.getZ();
                                if(blocks.contains(ss)){
                                    MCKRTp.locations.remove(loc);
                                    blocks.remove(ss);
                                    config.set("buttons.list", blocks);
                                    MCKRTp.getInst().saveConfig();
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("info.removebutton").replace("{WORLD}", loc.getWorld().getName()))
                                            .replace("{X}", String.valueOf(loc.getX()))
                                            .replace("{Y}", String.valueOf(loc.getY()))
                                            .replace("{Z}", String.valueOf(loc.getZ())));
                                } else{
                                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("errors.nobutton")));
                                }
                            } else{
                                p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("errors.correctusage")));
                            }
                        } else{
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("errors.correctusage")));
                        }
                    } else{
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("errors.notargetedbutton")));
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("errors.noperm")));
                }
            } else{
                s.sendMessage("[MCKTools] This command can be used only by players !");
            }
        }
        return true;
    }
}

