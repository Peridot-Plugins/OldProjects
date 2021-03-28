package me.mckoxu.spigot.lobbycore.inventory;

import me.mckoxu.spigot.lobbycore.LobbyCore;
import me.mckoxu.spigot.lobbycore.manager.ServerManager;
import me.mckoxu.spigot.lobbycore.object.Server;
import me.mckoxu.spigot.lobbycore.util.ChatUtil;
import me.mckoxu.spigot.lobbycore.util.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ServerSelectInventory {

    public static Map<UUID, Inventory> inventoryMap = new HashMap<>();

    public static Inventory getInventory(Player player) {
        Inventory inventory = null;
        if (inventoryMap.get(player.getUniqueId()) == null) {
            inventory = Bukkit.createInventory(null, LobbyCore.getInst().getConfig().getInt("serverlist.inventory.size") * 9, ChatUtil.color(LobbyCore.getInst().getConfig().getString("serverlist.inventory.title")));
        }
        inventoryMap.put(player.getUniqueId(), inventory);
        for (Server server : ServerManager.serverList) {
            inventory.setItem(server.getSlot(), Util.replaceServerInfo(server.getItem(), server.getName()));
        }
        return inventory;
    }

}
