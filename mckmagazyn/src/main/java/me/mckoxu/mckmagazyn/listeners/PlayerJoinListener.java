package me.mckoxu.mckmagazyn.listeners;

import me.mckoxu.mckmagazyn.data.FileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;

public class PlayerJoinListener implements Listener{

	@EventHandler
	public void onJoin(PlayerJoinEvent e) throws IOException{
		Player p = e.getPlayer();
		File f;
		if(FileManager.getPFile(p) == null){
			f = new File(FileManager.getUsersFolder(), e.getPlayer().getName() + ".yml");
			f.createNewFile();
		} else{
			f = FileManager.getPFile(p);
		}
	}
}
