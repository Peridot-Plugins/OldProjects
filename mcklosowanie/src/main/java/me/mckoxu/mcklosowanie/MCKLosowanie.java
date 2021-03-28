package me.mckoxu.mcklosowanie;

import me.mckoxu.mcklosowanie.commands.LosowanieCommand;
import me.mckoxu.mcklosowanie.data.Config;
import org.bukkit.plugin.java.JavaPlugin;

public class MCKLosowanie extends JavaPlugin {

    private static MCKLosowanie instance;

    @Override
    public void onEnable(){
        instance = this;
        saveDefaultConfig();
        Config.load();
        getCommand("losowanie").setExecutor(new LosowanieCommand());
    }

    public static MCKLosowanie getInstance(){
        return instance;
    }

}
