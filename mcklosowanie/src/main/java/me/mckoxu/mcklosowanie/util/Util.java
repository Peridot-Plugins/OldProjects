package me.mckoxu.mcklosowanie.util;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Util {

    public static String itemToString(ItemStack item){
        StringBuilder string = new StringBuilder();
        ItemMeta meta = item.getItemMeta();

        StringBuilder enchantments = new StringBuilder();
        item.getEnchantments().forEach((enchantment, level) -> {
            enchantments.append(enchantment.getName() + " " + level);
        });

        string.append(item.getAmount()+"x"+item.getType().toString());
        if(meta.getDisplayName() != null){
            string.append(", Nazwa: " + meta.getDisplayName());
        }
        if(!meta.getEnchants().isEmpty()){
            string.append(", Enchanty: " + enchantments.toString());
        }
        return ChatColor.stripColor(string.toString());
    }

}
