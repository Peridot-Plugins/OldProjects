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
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.io.File;

public class GuiOpenListener implements Listener{
	@EventHandler
	public static void onOpen(InventoryOpenEvent e){
		HumanEntity he = e.getPlayer();
		Inventory i = e.getInventory();
		InventoryType it = i.getType();
		if(he instanceof Player){
			Player p = (Player)he;
			if(i != null) {
				if(it.equals(InventoryType.PLAYER) || it.equals(InventoryType.CHEST)){
					if(MCKMagazyn.getInst().getConfig().getString("config.database.type").equalsIgnoreCase("flat")){
						File f;
						f = FileManager.getPFile(p);
						YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
						int mn = 0;
						try {
							mn = MCKMagazyn.magnum.get(p);
						} catch (Exception ex) {
							Bukkit.broadcastMessage(ex+"");
							return;
						}
						ConfigurationSection csk = MCKMagazyn.getInst().getConfig().getConfigurationSection("data.magazyny."+mn);
						if(csk != null) {
							if(i.getName().equals(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("data.magazyny." + mn + ".name")))){
								for (int ii = 0; ii < 26;) {
									i.setItem(ii, fYml.getItemStack("data.magazyny." + mn +".slots."+ii));
									ii++;
								}
								return;
							} else{
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
}
