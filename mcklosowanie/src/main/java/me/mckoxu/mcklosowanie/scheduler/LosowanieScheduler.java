package me.mckoxu.mcklosowanie.scheduler;

import me.mckoxu.mcklosowanie.commands.LosowanieCommand;
import org.bukkit.Material;

public abstract class LosowanieScheduler implements Runnable {

    public static int start(){
        if(LosowanieCommand.itemToWin != null && LosowanieCommand.itemToWin.getType() != Material.AIR){

        }
        return 0;
    }

}
