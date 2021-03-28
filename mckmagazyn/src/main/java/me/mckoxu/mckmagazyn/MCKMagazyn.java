package me.mckoxu.mckmagazyn;

import ch.njol.skript.Skript;
import me.mckoxu.mckmagazyn.commands.MagazynCmd;
import me.mckoxu.mckmagazyn.data.FileManager;
import me.mckoxu.mckmagazyn.listeners.GuiCloseListener;
import me.mckoxu.mckmagazyn.listeners.GuiOpenListener;
import me.mckoxu.mckmagazyn.listeners.InteractListener;
import me.mckoxu.mckmagazyn.listeners.PlayerJoinListener;
import me.mckoxu.mckmagazyn.skript.conditions.HasAccess;
import me.mckoxu.mckmagazyn.skript.conditions.MagExist;
import me.mckoxu.mckmagazyn.skript.effects.OpenMag;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MCKMagazyn extends JavaPlugin{

	private static MCKMagazyn inst;
	public static Map<Player, Integer> magnum;
	
	public void onEnable(){
		inst = this;
    	if (Bukkit.getPluginManager().getPlugin("Skript") != null) {
    		Skript.registerAddon(this);
            Skript.registerEffect(OpenMag.class, "open chest %number% for %player%");
            Skript.registerCondition(HasAccess.class, "%player% has access to chest %number%");
            Skript.registerCondition(MagExist.class, "chest %number% exist");
    	}
		FileManager.checkFiles();
		Bukkit.getPluginManager().registerEvents(new GuiOpenListener(), this);
		Bukkit.getPluginManager().registerEvents(new GuiCloseListener(), this);
		Bukkit.getPluginManager().registerEvents(new InteractListener(), this);
		Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);
		getCommand("magazyn").setExecutor(new MagazynCmd());
		new MagazynCmd();
	}
	
	public static MCKMagazyn getInst(){
		return inst;
	}
	
	public static void OpenMag(Player p, int mn){
		File f;
		f = FileManager.getPFile(p);
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
		ConfigurationSection csk = getInst().getConfig().getConfigurationSection("data.magazyny."+mn);
		if(csk != null) {
			if(getInst().getConfig().getBoolean("data.magazyny."+mn+".access") && !fYml.getBoolean("data.magazyny."+mn+".access")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.noaccess")).replace("{NUM}", String.valueOf(mn)));
				return;
			}
			magnum = new HashMap<Player, Integer>();
			magnum.put(p, mn);
			p.openInventory(Bukkit.createInventory(null, InventoryType.CHEST, ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("data.magazyny." + mn + ".name"))));
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.openchest")).replace("{NUM}", String.valueOf(mn)));
		} else{
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.errornr")));
		}
	}
	
	public static void SetSign(int i, Player p){
		if(i<1) return;
		Block b = p.getTargetBlock((Set<Material>) null, 100);
		if(b.getType().equals(Material.WALL_SIGN) || b.getType().equals(Material.SIGN_POST)){
			Location bl = b.getLocation();
			MCKMagazyn.getInst().getConfig().set("data.tabliczki." + i + ".x", bl.getBlockX());
			MCKMagazyn.getInst().getConfig().set("data.tabliczki." + i + ".y", bl.getBlockY());
			MCKMagazyn.getInst().getConfig().set("data.tabliczki." + i + ".z", bl.getBlockZ());
			MCKMagazyn.getInst().getConfig().set("data.tabliczki." + i + ".world", bl.getWorld().getName());
			MCKMagazyn.getInst().saveConfig();
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.signset").replace("{NUM}", String.valueOf(i))));
		} else{
			p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.nosign")));
		}
	}
	
	public static void SetChest(int i, Player p, String name, boolean access){
		if(i<1) return;
		MCKMagazyn.getInst().getConfig().set("data.magazyny." + i + ".name", name);
		MCKMagazyn.getInst().getConfig().set("data.magazyny." + i + ".access", access);
		MCKMagazyn.getInst().saveConfig();
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', MCKMagazyn.getInst().getConfig().getString("config.messages.chestset").replace("{NUM}", String.valueOf(i)).replace("{NAME}", name)));
	}
	
	public static void SetValue(Player p, int mn, Boolean mb){
		File f;
		f = FileManager.getPFile(p);
		YamlConfiguration fYml = YamlConfiguration.loadConfiguration(f);
		if(mn<1) return;
		fYml.set("data.magazyny." + mn + ".access" ,mb);
		try {
			fYml.save(f);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
