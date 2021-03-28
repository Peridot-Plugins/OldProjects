package me.mckoxu.mckrtp.listeners;

import me.mckoxu.mckrtp.MCKRTp;
import me.mckoxu.mckrtp.utils.CoordinateUtil;
import me.mckoxu.mckrtp.utils.RandomUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractListener implements Listener {

    public static ConfigurationSection config = MCKRTp.getInst().getConfig();

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        Player p = e.getPlayer();
        Action a = e.getAction();
        if(a == Action.RIGHT_CLICK_BLOCK){
            Block b = e.getClickedBlock();
            Location l = b.getLocation();
            if(b.getType() == Material.STONE_BUTTON){
                if(!MCKRTp.locations.isEmpty()) {
                    for (Location loc : MCKRTp.locations) {
                        if (CoordinateUtil.sameCords(l, loc)) {
                            double x = RandomUtil.getRandom(MCKRTp.minx, MCKRTp.maxx);
                            double z = RandomUtil.getRandom(MCKRTp.minz, MCKRTp.maxz);
                            Location teleport = new Location(loc.getWorld(), x, loc.getWorld().getHighestBlockYAt((int) x, (int) z), z);
                            p.teleport(teleport);
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("info.teleport").replace("{WORLD}", teleport.getWorld().getName()).replace("{X}", String.valueOf(teleport.getX())).replace("{Y}", String.valueOf(teleport.getY())).replace("{Z}", String.valueOf(teleport.getZ()))));
                            return;
                        }
                    }
                }
            }
        }
    }
}
