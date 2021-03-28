package me.mckoxu.spigot.lobbycore.util;

import me.mckoxu.bungee.connector.BungeeServer;
import me.mckoxu.bungee.manager.ServerManager;
import me.mckoxu.spigot.lobbycore.data.Config;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Util {

    public static boolean pingServer(InetSocketAddress address) {
        if (address == null) {
            return false;
        }
        try {
            Socket s = new Socket();
            s.connect(address, 120);
            s.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static ItemStack replaceServerInfo(ItemStack item, String serverName) {
        ItemStack returnItem = item;
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();
        List<String> newLore = new ArrayList<>();
        ServerManager.checkServerInfo(ServerManager.createServer(serverName));
        BungeeServer server = ServerManager.createServer(serverName);
        String status = ChatUtil.color(Config.off);
        if (server.getStatus()) {
            status = ChatUtil.color(Config.on);
        }
        for (String loreLine : lore) {
            newLore.add(ChatUtil.color(loreLine
                    .replace("{PLAYERCOUNT}", String.valueOf(server.getPlayerCount()))
                    .replace("{STATUS}", status)));
        }
        meta.setLore(newLore);
        returnItem.setItemMeta(meta);
        return returnItem;
    }

}
