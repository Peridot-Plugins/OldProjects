package pl.peridot.periplots.listener;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import pl.peridot.periplots.util.ChatUtil;

import java.util.Set;

public class SignChangeListener implements Listener {

    @EventHandler
    public void onChange(SignChangeEvent event){
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Location location = block.getLocation();
        if(event.getLine(0) != null
                && !event.getLine(0).isEmpty()
                && event.getLine(0).equalsIgnoreCase("[Dzialka]")){
            if(player.hasPermission("periplots.createsign")){
                ProtectedRegion region = null;
                int cost = 0;
                try{
                    cost = Integer.parseInt(event.getLine(2));
                } catch (Exception ex){
                    player.sendMessage("Niepoprawna cena!");
                    event.setCancelled(true);
                    return;
                }
                WorldGuardPlatform wgp = WorldGuard.getInstance().getPlatform();
                RegionManager regionManager = wgp.getRegionContainer().get(new BukkitWorld(location.getWorld()));
                if(event.getLine(1).isEmpty() || event.getLine(1) == null){
                    ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(BlockVector3.at(location.getX(), location.getY(), location.getZ()));
                    Set<ProtectedRegion> regionSet = applicableRegionSet.getRegions();
                    if(!regionSet.isEmpty()){
                        region = regionSet.iterator().next();
                    } else {
                        region = null;
                    }
                } else {
                    region = regionManager.getRegion(event.getLine(1));
                }
                if(region != null){
                    try {
                        event.setLine(0, ChatUtil.color("&r[&2Dzialka&r]"));
                        event.setLine(1, region.getId());
                        event.setLine(2, ChatUtil.color("&6" + cost + " zlota"));
                        event.setLine(3, ChatUtil.color("&2Wolna"));
                    } catch (Exception ex){
                        player.sendMessage("Błąd na tabliczce!");
                        event.setCancelled(true);
                        ex.printStackTrace();
                    }
                } else {
                    player.sendMessage("Niepoprawny region!");
                    event.setCancelled(true);
                }
            }
        }
    }

}
