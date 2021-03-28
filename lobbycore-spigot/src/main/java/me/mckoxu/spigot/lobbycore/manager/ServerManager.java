package me.mckoxu.spigot.lobbycore.manager;

import me.mckoxu.spigot.lobbycore.LobbyCore;
import me.mckoxu.spigot.lobbycore.object.ItemBuilder;
import me.mckoxu.spigot.lobbycore.object.Server;
import me.mckoxu.spigot.lobbycore.util.ChatUtil;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class ServerManager {

    public static List<Server> serverList = new ArrayList<>();

    public static void load() {
        ConfigurationSection config = LobbyCore.getInst().getConfig();
        ConfigurationSection csk = config.getConfigurationSection("serverlist.inventory.items.servers");
        for (String ss : csk.getKeys(false)) {
            ConfigurationSection cs = csk.getConfigurationSection(ss);
            for (String sss : cs.getKeys(false)) {
                ConfigurationSection css = cs.getConfigurationSection(sss);
                Server server = new Server(css.getString("connect"));
                server.setItem(new ItemBuilder(Material.matchMaterial(css.getString("material")))
                        .setData(css.getInt("data"))
                        .setName(ChatUtil.color(css.getString("name")))
                        .setLore(ChatUtil.color(css.getStringList("lore"))).toItemStack());
                server.setSlot(css.getInt("slot"));
                serverList.add(server);
            }
        }
    }

}
