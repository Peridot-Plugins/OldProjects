package me.mckoxu.spigot.lobbycore.object;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemBuilder {

    private Material material;
    private short data;
    private ItemStack itemstack;
    private int amount;
    private String name;
    private ArrayList<String> lore;
    private ArrayList<String> enchantments;
    private ItemMeta itemmeta;

    public ItemBuilder(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder setItem(ItemStack itemstack) {
        this.itemstack = itemstack;
        return this;
    }

    public int getAmount() {
        return amount;
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public short getData() {
        return data;
    }

    public ItemBuilder setData(short data) {
        this.data = data;
        return this;
    }

    public ItemBuilder setData(int data) {
        this.data = (short) data;
        return this;
    }

    public String getName() {
        return name;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder addLoreLine(String loreline) {
        this.lore.add(loreline);
        return this;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public ItemBuilder setLore(ArrayList<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder addEnchantments(ArrayList<String> enchantments) {
        for (String enchantment : enchantments) {
            this.enchantments.add(enchantment);
        }
        return this;
    }

    public ItemBuilder setEnchantment(String enchantment) {
        this.enchantments.clear();
        this.enchantments.add(enchantment);
        return this;
    }

    public ItemBuilder addEnchantment(String enchantment) {
        this.enchantments.add(enchantment);
        return this;
    }

    public ItemBuilder removeEnchantment(String enchantment) {
        this.enchantments.remove(enchantment);
        return this;
    }

    public ItemBuilder removeEnchantments(ArrayList<String> enchantments) {
        for (String enchantment : enchantments) {
            this.enchantments.remove(enchantment);
        }
        return this;
    }

    public ArrayList<String> getEnchantments() {
        return enchantments;
    }

    public ItemBuilder setEnchantments(ArrayList<String> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public ItemMeta getItemMeta() {
        ItemMeta im = this.itemmeta;
        return im;
    }

    public ItemBuilder setItemMeta(ItemMeta itemmeta) {
        this.itemmeta = itemmeta;
        return this;
    }

    public ItemBuilder getItemBuilder() {
        return this;
    }

    @SuppressWarnings("deprecation")
    public ItemStack toItemStack() {
        if (name == null) {
            name = "";
        }
        if (amount < 1) amount = 1;
        if (itemstack == null) {
            if (material == null) {
                material = Material.AIR;
            }
            ItemStack is = new ItemStack(material, amount, data);
            is.setAmount(amount);
            ItemMeta im = is.getItemMeta();
            if (itemmeta != null) {
                im = itemmeta;
                if (lore != null) {
                    im.setLore(lore);
                }
                if (name != null) {
                    im.setDisplayName(name);
                }
            } else {
                if (lore != null) {
                    im.setLore(lore);
                }
                if (name != null) {
                    im.setDisplayName(name);
                }
            }
            is.setItemMeta(im);
            HashMap<Enchantment, Integer> enchantmentIntegerMap = new HashMap<Enchantment, Integer>();
            if (enchantments != null) {
                for (String s : enchantments) {
                    String[] ss = s.split(":");
                    enchantmentIntegerMap.put(Enchantment.getByName(ss[0]), Integer.valueOf(Integer.parseInt(ss[1])));
                }
            }
            if (enchantmentIntegerMap != null) {
                is.addUnsafeEnchantments(enchantmentIntegerMap);
            }
            return is;
        } else {
            ItemStack is = this.itemstack;
            HashMap<Enchantment, Integer> enchantmentIntegerMap = new HashMap<Enchantment, Integer>();
            if (enchantments != null) {
                for (String s : enchantments) {
                    String[] ss = s.split(":");
                    enchantmentIntegerMap.put(Enchantment.getByName(ss[0]), Integer.valueOf(Integer.parseInt(ss[1])));
                }
            }
            if (enchantmentIntegerMap != null) {
                is.addUnsafeEnchantments(enchantmentIntegerMap);
            }
            is.setAmount(amount);
            ItemMeta im = is.getItemMeta();
            if (itemmeta != null) {
                im = itemmeta;
                if (lore != null) {
                    im.setLore(lore);
                }
                if (name != null) {
                    im.setDisplayName(name);
                }
            } else {
                if (lore != null) {
                    im.setLore(lore);
                }
                if (name != null) {
                    im.setDisplayName(name);
                }
            }
            is.setItemMeta(im);
            return is;
        }
    }

}
