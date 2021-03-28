package me.mckoxu.bungeecord.lobbycore;

import me.mckoxu.bungeecord.lobbycore.command.ConnectCommand;
import me.mckoxu.bungeecord.lobbycore.command.LobbyCommand;
import me.mckoxu.bungeecord.lobbycore.listener.ServerConnectListener;
import me.mckoxu.bungeecord.lobbycore.listener.ProxyPingListener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.IOException;
import java.util.logging.Logger;

import static me.mckoxu.bungeecord.lobbycore.manager.FileManager.loadResource;

public final class LobbyCore extends Plugin {

    private static LobbyCore instance;
    private static Configuration configuration;
    private Logger logger;

    @Override
    public void onEnable() {
        instance = this;
        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(
                    loadResource(this, "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger = getLogger();
        getProxy().getPluginManager().registerListener(this, new ProxyPingListener());
        getProxy().getPluginManager().registerListener(this, new ServerConnectListener());
        getProxy().getPluginManager().registerCommand(this, new ConnectCommand());
        getProxy().getPluginManager().registerCommand(this, new LobbyCommand());
    }

    public static LobbyCore getInst(){
        return instance;
    }

    public static Configuration getConfig(){
        return configuration;
    }
}
