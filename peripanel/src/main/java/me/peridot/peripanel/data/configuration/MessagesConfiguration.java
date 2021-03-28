package me.peridot.peripanel.data.configuration;

import me.peridot.peripanel.PeriPanel;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MessagesConfiguration {

    private final PeriPanel plugin;

    private YamlConfiguration yamlConfiguration;

    public MessagesConfiguration(PeriPanel plugin) {
        this.plugin = plugin;
    }

    public void reloadConfiguration() {
        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdir();
        }

        File file = new File(plugin.getDataFolder(), "messages.yml");

        if (!file.exists()) {
            plugin.saveResource("messages.yml", true);
        }

        yamlConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getYamlConfiguration() {
        return yamlConfiguration;
    }
}
