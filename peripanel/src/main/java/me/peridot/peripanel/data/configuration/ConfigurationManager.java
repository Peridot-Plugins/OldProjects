package me.peridot.peripanel.data.configuration;

import api.peridot.periapi.configuration.langapi.LangAPI;
import me.peridot.peripanel.PeriPanel;

public class ConfigurationManager {

    private final PeriPanel plugin;

    private PluginConfiguration pluginConfiguration;

    private MessagesConfiguration messagesConfiguration;
    private LangAPI lang;

    public ConfigurationManager(PeriPanel plugin) {
        this.plugin = plugin;
    }

    public void reloadConfiguration() {
        pluginConfiguration = new PluginConfiguration(plugin);
        pluginConfiguration.reloadConfiguration();

        messagesConfiguration = new MessagesConfiguration(plugin);
        messagesConfiguration.reloadConfiguration();
        lang = new LangAPI(messagesConfiguration.getYamlConfiguration().getConfigurationSection("messages"));
    }

    public PluginConfiguration getPluginConfiguration() {
        if (pluginConfiguration == null) {
            reloadConfiguration();
        }
        return pluginConfiguration;
    }

    public MessagesConfiguration getMessagesConfiguration() {
        if (messagesConfiguration == null) {
            reloadConfiguration();
        }
        return messagesConfiguration;
    }

    public LangAPI getLang() {
        if (lang == null) {
            reloadConfiguration();
        }
        return lang;
    }
}
