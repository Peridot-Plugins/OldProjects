package me.mckoxu.mckrtp.utils;

import org.bukkit.Location;

public class CoordinateUtil {

    public static boolean sameCords(Location loc1, Location loc2){
        if(loc1.getWorld().equals(loc2.getWorld())){
            if(loc1.distance(loc2) == 0){
                return true;
            } else{
                return false;
            }
        } else{
            return false;
        }
    }
}
