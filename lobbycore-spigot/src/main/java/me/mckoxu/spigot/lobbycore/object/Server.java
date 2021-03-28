package me.mckoxu.spigot.lobbycore.object;

import org.bukkit.inventory.ItemStack;

public class Server {

    private String name;
    private ItemStack item;
    private int slot;

    public Server(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

}
