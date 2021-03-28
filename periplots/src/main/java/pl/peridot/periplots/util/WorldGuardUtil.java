package pl.peridot.periplots.util;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.internal.platform.WorldGuardPlatform;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.List;

public class WorldGuardUtil {

    public static List<ProtectedRegion> getWorldGuardRegions(){
        WorldGuardPlatform worldGuardPlatform = WorldGuard.getInstance().getPlatform();
        RegionContainer regionContainer = worldGuardPlatform.getRegionContainer();

        for (World world : Bukkit.getWorlds()){
            RegionManager regionManager = regionContainer.get(new BukkitWorld(world));

            r
        }
    }

}
