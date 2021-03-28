package me.mckoxu.mckmagazyn.data;

import me.mckoxu.mckmagazyn.MCKMagazyn;
import org.bukkit.entity.Player;

import java.io.File;

public class FileManager {
	private static File data = new File(MCKMagazyn.getInst().getDataFolder(), "data");
	
	public static void checkFiles(){
		if(!MCKMagazyn.getInst().getDataFolder().exists()){
			MCKMagazyn.getInst().getDataFolder().mkdir();
		}
		if(!new File(MCKMagazyn.getInst().getDataFolder(), "config.yml").exists()){
			MCKMagazyn.getInst().saveDefaultConfig();
		}
		if(!data.exists()){
			data.mkdir();
		}	
	}
	
	public static File getUsersFolder(){
		return data;
	}
	public static File getPFile(Player p){
		File f = new File(data, p.getName() + ".yml");
		if(!f.exists()) return null;
		return f;
	}
	
}
