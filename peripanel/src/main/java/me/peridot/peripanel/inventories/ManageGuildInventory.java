package me.peridot.peripanel.inventories;

import api.peridot.periapi.configuration.langapi.LangAPI;
import api.peridot.periapi.inventories.InventoryContent;
import api.peridot.periapi.inventories.items.InventoryItem;
import api.peridot.periapi.inventories.providers.InventoryProvider;
import api.peridot.periapi.utils.Pair;
import api.peridot.periapi.utils.replacements.Replacement;
import me.peridot.peripanel.PeriPanel;
import me.peridot.peripanel.data.configuration.ConfigurationManager;
import me.peridot.peripanel.data.configuration.PluginConfiguration;
import net.dzikoysk.funnyguilds.FunnyGuilds;
import net.dzikoysk.funnyguilds.basic.guild.Guild;
import net.dzikoysk.funnyguilds.basic.guild.GuildUtils;
import net.dzikoysk.funnyguilds.basic.guild.Region;
import net.dzikoysk.funnyguilds.basic.user.User;
import net.dzikoysk.funnyguilds.data.database.DatabaseGuild;
import net.dzikoysk.funnyguilds.data.database.DatabaseRegion;
import net.dzikoysk.funnyguilds.data.database.SQLDataModel;
import net.dzikoysk.funnyguilds.data.flat.FlatDataModel;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.stream.Stream;

public class ManageGuildInventory implements InventoryProvider {

    private final PeriPanel plugin;

    public ManageGuildInventory(PeriPanel plugin) {
        this.plugin = plugin;
    }

    @Override
    public void init(Player player, InventoryContent content) {
        PluginConfiguration config = plugin.getConfigurations().getPluginConfiguration();
        LangAPI lang = plugin.getConfigurations().getLang();

        User user = User.get(player);

        if (!user.hasGuild()) {
            return;
        }
        Guild guild = user.getGuild();

        content.fill(InventoryItem.builder().item(config.getItemBuilder("inventories.panel.buttons.background").clone()).build());

        net.dzikoysk.funnyguilds.data.configs.PluginConfiguration guildsConfiguration = plugin.getGuildConfiguration();

        content.setItem(config.getInt("inventories.manage-guild.buttons.name-change.slot"), InventoryItem.builder()
                .item(config.getItemBuilder("inventories.manage-guild.buttons.name-change"))
                .consumer(event -> {
                    new AnvilGUI.Builder()
                            .text(config.getColoredString("messages.manage-guild.name-change.default-text"))
                            .onComplete((playerAnvil, text) -> {
                                if (!user.hasGuild()) {
                                    return AnvilGUI.Response.close();
                                }
                                if (text == null || text.isEmpty()) {
                                    return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.name-change.empty"));
                                }
                                if (GuildUtils.nameExists(text)) {
                                    return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.name-change.exist"));
                                }
                                if (text.length() > guildsConfiguration.createNameLength) {
                                    return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.name-change.too-long", new Replacement("{LENGTH}", guildsConfiguration.createNameLength)));
                                }
                                if (text.length() < guildsConfiguration.createNameMinLength) {
                                    return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.name-change.too-short", new Replacement("{LENGTH}", guildsConfiguration.createNameMinLength)));
                                }
                                if (!text.matches(guildsConfiguration.nameRegex.getPattern())) {
                                    return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.name-change.invalid"));
                                }
                                if (!GuildUtils.isNameValid(text)) {
                                    return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.name-change.invalid"));
                                }
                                for (Pair<Integer, ItemStack> cost : config.nameChangeCost) {
                                    int amount = cost.getKey();
                                    ItemStack item = cost.getValue();
                                    if (amountOfItem(player, item.getType(), item.getDurability()) < amount) {
                                        return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.name-change.can-not-afford"));
                                    }
                                }
                                for (Pair<Integer, ItemStack> cost : config.tagChangeCost) {
                                    int amount = cost.getKey();
                                    ItemStack item = cost.getValue();
                                    removeItem(player, item.getType(), item.getDurability(), amount);
                                }
                                lang.getMessage("manage-guild.name-changed").send(player, new Replacement("{NEW-NAME}", text),
                                        new Replacement("{OLD-NAME}", guild.getName()));
                                if (guildsConfiguration.regionsEnabled) {
                                    Region region = guild.getRegion();

                                    if (FunnyGuilds.getInstance().getDataModel() instanceof FlatDataModel) {
                                        FlatDataModel dataModel = (FlatDataModel) FunnyGuilds.getInstance().getDataModel();
                                        dataModel.getRegionFile(region).delete();
                                    }

                                    if (FunnyGuilds.getInstance().getDataModel() instanceof SQLDataModel) {
                                        new DatabaseRegion(region).delete();
                                    }

                                    region.setName(text);
                                }

                                if (FunnyGuilds.getInstance().getDataModel() instanceof FlatDataModel) {
                                    FlatDataModel dataModel = (FlatDataModel) FunnyGuilds.getInstance().getDataModel();
                                    dataModel.getGuildFile(guild).delete();
                                }

                                if (FunnyGuilds.getInstance().getDataModel() instanceof SQLDataModel) {
                                    new DatabaseGuild(guild).delete();
                                }

                                guild.setName(text);

                                FunnyGuilds.getInstance().getDataModel().save(false);
                                return AnvilGUI.Response.close();
                            })
                            .plugin(plugin)
                            .open(player);
                })
                .build());

        content.setItem(config.getInt("inventories.manage-guild.buttons.tag-change.slot"), InventoryItem.builder()
                .item(config.getItemBuilder("inventories.manage-guild.buttons.tag-change"))
                .consumer(event -> {
                    new AnvilGUI.Builder()
                            .text(config.getColoredString("messages.manage-guild.tag-change.default-text"))
                            .onComplete((playerAnvil, text) -> {
                                if (!user.hasGuild()) {
                                    return AnvilGUI.Response.close();
                                }
                                if (text == null || text.isEmpty()) {
                                    return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.tag-change.empty"));
                                }
                                if (GuildUtils.tagExists(text)) {
                                    return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.tag-change.exist"));
                                }
                                if (text.length() > guildsConfiguration.createTagLength) {
                                    return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.tag-change.too-long", new Replacement("{LENGTH}", guildsConfiguration.createTagLength)));
                                }
                                if (text.length() < guildsConfiguration.createTagMinLength) {
                                    return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.tag-change.too-short", new Replacement("{LENGTH}", guildsConfiguration.createTagMinLength)));
                                }
                                if (!text.matches(guildsConfiguration.tagRegex.getPattern())) {
                                    return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.tag-change.invalid"));
                                }
                                if (!GuildUtils.isTagValid(text)) {
                                    return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.tag-change.invalid"));
                                }
                                for (Pair<Integer, ItemStack> cost : config.tagChangeCost) {
                                    int amount = cost.getKey();
                                    ItemStack item = cost.getValue();
                                    if (amountOfItem(player, item.getType(), item.getDurability()) < amount) {
                                        return AnvilGUI.Response.text(config.getColoredString("messages.manage-guild.tag-change.can-not-afford"));
                                    }
                                }
                                for (Pair<Integer, ItemStack> cost : config.tagChangeCost) {
                                    int amount = cost.getKey();
                                    ItemStack item = cost.getValue();
                                    removeItem(player, item.getType(), item.getDurability(), amount);
                                }
                                lang.getMessage("manage-guild.tag-changed").send(player, new Replacement("{NEW-TAG}", text),
                                        new Replacement("{OLD-TAG}", guild.getTag()));
                                guild.setTag(text);
                                FunnyGuilds.getInstance().getDataModel().save(false);
                                return AnvilGUI.Response.close();
                            })
                            .plugin(plugin)
                            .open(player);
                })
                .build());

        content.setItem(config.getInt("inventories.manage-guild.buttons.home-change.slot"), InventoryItem.builder()
                .item(config.getItemBuilder("inventories.manage-guild.buttons.home-change"))
                .consumer(event -> {
                    if (!user.hasGuild()) {
                        return;
                    }
                    for (Pair<Integer, ItemStack> cost : config.homeChangeCost) {
                        int amount = cost.getKey();
                        ItemStack item = cost.getValue();
                        if (amountOfItem(player, item.getType(), item.getDurability()) < amount) {
                            lang.getMessage("errors.manage-guild.can-not-afford-for-home-change").send(player);
                            return;
                        }
                    }
                    for (Pair<Integer, ItemStack> cost : config.homeChangeCost) {
                        int amount = cost.getKey();
                        ItemStack item = cost.getValue();
                        removeItem(player, item.getType(), item.getDurability(), amount);
                    }
                })
                .build());

        content.setItem(config.getInt("inventories.manage-guild.buttons.back.slot"), InventoryItem.builder()
                .item(config.getItemBuilder("inventories.manage-guild.buttons.back"))
                .consumer(event -> {
                    plugin.getInventoryManager().getPanelInventory().open(player);
                })
                .build());
    }

    @Override
    public void update(Player player, InventoryContent content) {
    }

    private int amountOfItem(Player player, Material material, short durability) {
        return Stream.of(player.getInventory().getContents()).
                filter(item -> item != null && item.getType() == material && item.getDurability() == durability)
                .mapToInt(ItemStack::getAmount).sum();
    }

    private void removeItem(Player player, Material material, short durability, int amount) {
        PlayerInventory inventory = player.getInventory();
        int left = amount;
        for (int i = 0; i < 36; i++) {
            ItemStack item = inventory.getItem(i);
            if (item == null || item.getType() == Material.AIR) continue;
            int itemAmount = item.getAmount();
            if (item.getType() == material && item.getDurability() == durability) {
                if (itemAmount > left) {
                    item.setAmount(itemAmount - left);
                    left = 0;
                } else {
                    left -= itemAmount;
                    inventory.setItem(i, null);
                }
                if (left <= 0) {
                    break;
                }
            }
        }
    }
}
