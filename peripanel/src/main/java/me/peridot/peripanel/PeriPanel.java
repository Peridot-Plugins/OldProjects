package me.peridot.peripanel;

import api.peridot.periapi.PeriAPI;
import me.peridot.peripanel.commands.PanelCommand;
import me.peridot.peripanel.data.configuration.ConfigurationManager;
import me.peridot.peripanel.inventories.InventoryManager;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.data.configs.PluginConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class PeriPanel extends JavaPlugin {

    private ConfigurationManager configurationManager;
    private PeriAPI periAPI;
    private InventoryManager inventoryManager;
    private PluginConfiguration guildsConfiguration;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configurationManager = new ConfigurationManager(this);
        configurationManager.reloadConfiguration();

        periAPI = new PeriAPI(this);
        periAPI.init();

        initInventoryManager();

        guildsConfiguration = FunnyGuilds.getInstance().getPluginConfiguration();

        PluginManager pluginManager = Bukkit.getPluginManager();

        new PanelCommand(this).registerCommand();
    }

    public ConfigurationManager getConfigurations() {
        return configurationManager;
    }

    public PeriAPI getPeriAPI() {
        return periAPI;
    }

    public InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public void initInventoryManager() {
        this.inventoryManager = new InventoryManager(this);
    }

    public PluginConfiguration getGuildConfiguration() {
        return guildsConfiguration;
    }
}
