package me.peridot.peripanel.inventories;

import api.peridot.periapi.items.ItemBuilder;
import me.peridot.peripanel.PeriPanel;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class ConfigurationButton {

    private final ItemBuilder item;
    private final List<Action> actions;

    public ConfigurationButton(ItemBuilder item, List<Action> actions) {
        this.item = item;
        this.actions = actions;
    }

    public ItemBuilder getItem() {
        return item;
    }

    public List<Action> getActions() {
        return new ArrayList<>(actions);
    }

    public void run(PeriPanel plugin, InventoryClickEvent event) {
        for (Action action : actions) {
            action.run(plugin, event);
        }
    }

    public enum Action {
        NONE((plugin, event) -> {
        }),
        OPEN_GUILD_MANAGE_GUI((plugin, event) -> {
            if(!(event.getWhoClicked() instanceof Player)) return;
            plugin.getInventoryManager().getManageGuildInventory().open((Player) event.getWhoClicked());
        }),
        OPEN_GUILD_MEMBER_GUI((plugin, event) -> {
        });

        private final BiConsumer<PeriPanel, InventoryClickEvent> consumer;

        private Action(BiConsumer<PeriPanel, InventoryClickEvent> consumer) {
            this.consumer = consumer;
        }

        public BiConsumer<PeriPanel, InventoryClickEvent> getConsumer() {
            return consumer;
        }

        public void run(PeriPanel plugin, InventoryClickEvent event) {
            consumer.accept(plugin, event);
        }
    }

}
