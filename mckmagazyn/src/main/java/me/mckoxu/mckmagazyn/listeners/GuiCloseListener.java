package me.mckoxu.mckmagazyn.listeners;

import me.mckoxu.mckmagazyn.MCKMagazyn;
import me.mckoxu.mckmagazyn.data.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;

public class GuiCloseListener implements Listener{
	@EventHandler
	public static void onClose(InventoryCloseEvent e) throws IOException{
		HumanEntity he = e.getPlayer();
		Inventory i = e.getInventory();
		if(he instanceof Player){
			Player p = (Player)he;
			if(i != null) {
				if(MCKMagazyn.getInst().getConfig().getString("config.database.type").equalsIgnoreCase("flat")){
					File f;
					f = FileManager.getPFile(p);
					YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
					int mn = 0;
					try {
						mn = MCKMagazyn.magnum.get(p);
					} catch (Exception ex) {
						return;
					}
					ConfigurationSection csk = MCKMagazyn.getInst().getConfig().getConfigurationSection("data.magazyny."+mn);
					if(csk != null) {
						if(i.getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("data.magazyny." + mn + ".name")))){
							for (int ii = 0; ii < 26;) {
								fYml.set("data.magazyny." + mn +".slots."+ii, i.getItem(ii));
								ii++;
							}
							fYml.save(f);	
							MCKMagazyn.magnum = null;
							return;
						}
					} else {
						MCKMagazyn.magnum = null;
						return;
					}
				} else if(MCKMagazyn.getInst().getConfig().getString("config.database.type").equalsIgnoreCase("mysql")){
					Bukkit.getLogger().info("[ERROR] Bad database type!");
				} else{
					Bukkit.getLogger().info("[ERROR] Bad database type!");
				}
			}
		}
	}
}
