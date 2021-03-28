package me.mckoxu.mckmagazyn.commands;

import me.mckoxu.mckmagazyn.MCKMagazyn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;


public class MagazynCmd implements CommandExecutor{
	
	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("magazyn")){
			if(s.hasPermission("mckmagazyn.cmd") || s.hasPermission("mckmagazyn.*")){
				if(args.length >= 1){
					if(args[0].equalsIgnoreCase("setsign")){
						if(s instanceof Player){
							Player p = (Player) s;
							if(p.hasPermission("mckmagazyn.setsign") || s.hasPermission("mckmagazyn.*")){
								if(args.length >= 2){
									int i = 0;
									try{
										i = Integer.parseInt(args[1]);
									} catch(Exception e){
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.errornr")));
										return false;
									}
									MCKMagazyn.SetSign(i, p);
									return true;				
								} else{
									for(String msg : MCKMagazyn.getInst().getConfig().getStringList("config.messages.correctusage")){
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
									}
								}
							} else{
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.noperm")));
							}
						} else{
							s.sendMessage("[ERROR] Nie jestes graczem !");
						}
					} else if(args[0].equalsIgnoreCase("setchest")){
						if(s instanceof Player){
							Player p = (Player) s;
							if(p.hasPermission("mckmagazyn.setchest") || s.hasPermission("mckmagazyn.*")){
								if(args.length >= 2){
									int i = 0;
									boolean b = false;
									String name = "";
									try{
										i = Integer.parseInt(args[1]);
									} catch(Exception e){
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.errornr")));
										return false;
									}
									try{
										b = Boolean.parseBoolean(args[2]);
									} catch(Exception e){
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.errorboolean")));
										return false;
									}
									for(int ii = 3; ii<args.length; ii++){
										name += " " + args[ii];
									}
									name = name.replaceFirst(" ", "");
									MCKMagazyn.SetChest(i, p, name, b);
									return true;				
								} else{
									for(String msg : MCKMagazyn.getInst().getConfig().getStringList("config.messages.correctusage")){
										p.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
									}
								}
							} else{
								p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.noperm")));
							}
						} else{
							s.sendMessage("[ERROR] Nie jestes graczem !");
						}
					} else if(args[0].equalsIgnoreCase("giveaccess")){
						if(s.hasPermission("mckmagazyn.giveaccess") || s.hasPermission("mckmagazyn.*")){
							if(args.length >= 2){
								String pName = args[1];
								Player ap = Bukkit.getPlayer(pName);
								if(args.length >= 3){
									int i = 0;
									try{
										i = Integer.parseInt(args[2]);
									} catch(Exception e){
										s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.errornr")));
										return false;
									}
									ConfigurationSection csk = MCKMagazyn.getInst().getConfig().getConfigurationSection("data.magazyny."+i);
									if(csk != null) {
										if(MCKMagazyn.getInst().getConfig().getBoolean("data.magazyny."+ i +".access")){
											if(!MCKMagazyn.getInst().getConfig().getBoolean("data.values." + ap.getName() + "." + i)){
												MCKMagazyn.SetValue(ap, i, true);
												s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.giveaccess")).replace("{PLAYER}", String.valueOf(ap.getName())).replace("{NUM}", String.valueOf(i)));
												ap.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.getaccess")).replace("{NUM}", String.valueOf(i)));
												return true;
											} else{
												s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.haschest")));
												return true;
											}
										} else{
											s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.naccesschest")));
											return true;
										}
									} else{
										s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.errornr")));
										return true;
									}
								} else{
									for(String msg : MCKMagazyn.getInst().getConfig().getStringList("config.messages.correctusage")){
										s.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
									}
								}
							} else{
								s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.noplayer")));
							}
						} else{
							s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.noperm")));
						}
					} else if(args[0].equalsIgnoreCase("removeaccess")){
						if(s.hasPermission("mckmagazyn.removeaccess") || s.hasPermission("mckmagazyn.*")){
							if(args.length >= 2){
								String pName = args[1];
								Player ap = Bukkit.getPlayer(pName);
								if(args.length >= 3){
									int i = 0;
									try{
										i = Integer.parseInt(args[2]);
									} catch(Exception e){
										s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.errornr")));
										return false;
									}
									ConfigurationSection csk = MCKMagazyn.getInst().getConfig().getConfigurationSection("data.magazyny."+i);
									if(csk != null) {
										if(MCKMagazyn.getInst().getConfig().getBoolean("data.magazyny."+ i +".access")){
											if(MCKMagazyn.getInst().getConfig().getBoolean("data.values." + ap.getName() + "." + i)){
												MCKMagazyn.SetValue(ap, 1, false);
												s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.removeaccess")).replace("{PLAYER}", String.valueOf(ap.getName())).replace("{NUM}", String.valueOf(i)));
												ap.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.takeaccess")).replace("{NUM}", String.valueOf(i)));
												return true;
											} else{
												s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.nhaschest")));
												return true;
											}
										} else{
											s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.naccesschest")));
											return true;
										}
									} else{
										s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.errornr")));
										return true;
									}
								} else{
									for(String msg : MCKMagazyn.getInst().getConfig().getStringList("config.messages.correctusage")){
										s.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
									}
								}
							} else{
								s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.noplayer")));
							}
						} else{
							s.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.noperm")));
						}
					} else{
						for(String msg : MCKMagazyn.getInst().getConfig().getStringList("config.messages.correctusage")){
							s.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
						}
					}
				} else{
					for(String msg : MCKMagazyn.getInst().getConfig().getStringList("config.messages.correctusage")){
						s.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
					}
				}
			}
		}
		return false;
	}
}
