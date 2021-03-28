package me.peridot.periantyfav.listeners;

import me.peridot.periantyfav.PeriAntyFav;
import me.peridot.periantyfav.configuration.Config;
import me.peridot.periantyfav.util.ColorUtil;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InventoryCreativeListener implements Listener {

    private final PeriAntyFav plugin;
    private final Config pluginConfig;

    public InventoryCreativeListener(PeriAntyFav plugin) {
        this.plugin = plugin;
        this.pluginConfig = plugin.getPluginConfig();
    }

    @EventHandler(ignoreCancelled = true)
    public void onCreativeInventory(InventoryCreativeEvent event){
        System.out.println("--------------------------------");
        System.out.println("event.getCursor() = " + event.getCursor());
        System.out.println("event.getCurrentItem() = " + event.getCurrentItem());
        System.out.println("event.getSlotType() = " + event.getSlotType());

        ItemStack item = event.getCursor();
        ItemStack currentItem = event.getCurrentItem();
        InventoryType.SlotType slotType = event.getSlotType();
        Inventory clickedInventory = event.getClickedInventory();

        if(item == null) return;

        Player player = (Player) event.getWhoClicked();

        if(clickedInventory == null && slotType != InventoryType.SlotType.OUTSIDE && item.getItemMeta() == null) return;
        //if(item.getItemMeta() != null && item.getItemMeta().hasLore() && currentItem != null) return;

        try {
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.setLore(fixItemsInfo(pluginConfig.newItemDescription, item, player));
            item.setItemMeta(itemMeta);
        } catch (Exception ignored) { }
    }

    private List<String> fixItemsInfo(List<String> description, ItemStack item, Player who){
        List<String> newDescription = new ArrayList<>();
        Location loc = who.getLocation();

        for(String string : description){
            string = StringUtils.replace(string, "{WHO}", who.getName());
            string = StringUtils.replace(string, "{WHEN}", pluginConfig.dateFormat.format(LocalDateTime.now()));
            string = StringUtils.replace(string, "{WORLD}", loc.getWorld().getName());
            string = StringUtils.replace(string, "{X}", Integer.toString(loc.getBlockX()));
            string = StringUtils.replace(string, "{Y}", Integer.toString(loc.getBlockY()));
            string = StringUtils.replace(string, "{Z}", Integer.toString(loc.getBlockZ()));
            string = StringUtils.replace(string, "{AMOUNT}", Integer.toString(item.getAmount()));
            newDescription.add(ColorUtil.color(string));
        }

        return newDescription;
    }
}
