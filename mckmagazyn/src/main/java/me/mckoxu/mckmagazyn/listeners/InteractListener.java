package me.mckoxu.mckmagazyn.listeners;

import me.mckoxu.mckmagazyn.MCKMagazyn;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractListener implements Listener{

	@EventHandler
	public void onInteract(PlayerInteractEvent e){
		Player p = e.getPlayer();
		if(e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getClickedBlock().getType().equals(Material.WALL_SIGN) || e.getClickedBlock().getType().equals(Material.SIGN_POST)){
			if(p.hasPermission("mckmagazyn.open")){
				Location locb = e.getClickedBlock().getLocation();
				double locx = locb.getBlockX();
				double locy = locb.getBlockY();
				double locz = locb.getBlockZ();
				String locworld = locb.getWorld().getName();
				ConfigurationSection csk = MCKMagazyn.getInst().getConfig().getConfigurationSection("data.tabliczki");
				if(csk != null) {
					for(String ss : csk.getKeys(false)){
						ConfigurationSection cs = csk.getConfigurationSection(ss);
						String worldc = cs.getString("world");
						double xc = cs.getDouble("x");
						double yc = cs.getDouble("y");
						double zc = cs.getDouble("z");
						if(locworld.equals(worldc) && locx == xc && locy == yc && locz == zc) {
							int i = 0;
							try {
								i = Integer.parseInt(cs.getName());
							} catch(Exception ex) {
								Bukkit.getLogger().info("[ERROR] " + p.getName() + ": " + ex.getMessage());
								return;
							}
							MCKMagazyn.OpenMag(p, i);
						}
					}
				} else {
					return;
				}
			} else{
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.noperm")));
			}
		}
	}
}
