package pl.peridot.periplots.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class Util {

    public static boolean inventoryIsFull(Player player, ItemStack itemToAdd) {
        for (int i = 0; i < 36; i++) {
            if (player.getInventory().getItem(i) == null) {
                return false;
            }
            ItemStack item = player.getInventory().getItem(i);
            if (item.getType().equals(itemToAdd.getType()) && item.getItemMeta().equals(itemToAdd.getItemMeta()) && item.getDurability() == itemToAdd.getDurability() && item.getEnchantments().equals(itemToAdd.getEnchantments()) && item.getAmount() + itemToAdd.getAmount() <= itemToAdd.getMaxStackSize()) {
                return false;
            }
        }
        return true;
    }

    public static int itemsAmountInInventory(Player player, Material item) {
        if (item == null)
            return 0;
        int amount = 0;
        for (int i = 0; i < 36; i++) {
            ItemStack slot = player.getInventory().getItem(i);
            if (slot == null || slot.getType() != item)
                continue;
            amount += slot.getAmount();
        }
        return amount;
    }

    public static int goldAmountInInventory(Player player) {
        int goldIngot = itemsAmountInInventory(player, Material.GOLD_INGOT);
        int goldBlock = itemsAmountInInventory(player, Material.GOLD_BLOCK);

        return goldIngot + goldBlock * 9;
    }

    public static void removeItemFromInventory(Player player, Material material, int amount){
        PlayerInventory inventory = player.getInventory();
        for (int ia = 0; ia < amount; ia++) {
            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack itm = inventory.getItem(i);
                if (itm != null && itm.getType() == material) {
                    int amt = itm.getAmount() - 1;
                    itm.setAmount(amt);
                    inventory.setItem(i, amt > 0 ? itm : null);
                    break;
                }
            }
        }
    }
}
