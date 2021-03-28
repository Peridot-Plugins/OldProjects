package me.peridot.peripanel.inventories;

import api.peridot.periapi.inventories.CustomInventory;
import api.peridot.periapi.inventories.PeriInventoryManager;
import me.peridot.peripanel.PeriPanel;
import me.peridot.peripanel.data.configuration.PluginConfiguration;

public class InventoryManager {

    private final PeriPanel plugin;
    private final PeriInventoryManager manager;

    private final CustomInventory panelInventory;
    private final CustomInventory manageGuildInventory;

    public InventoryManager(PeriPanel plugin) {
        this.plugin = plugin;
        this.manager = plugin.getPeriAPI().getInventoryManager();

        PluginConfiguration config = plugin.getConfigurations().getPluginConfiguration();

        panelInventory = CustomInventory.builder()
                .plugin(plugin)
                .manager(manager)
                .provider(new PanelInventory(plugin))
                .rows(config.getInt("inventories.panel.size"))
                .title(config.getColoredString("inventories.panel.title"))
                .updateDelay(-1)
                .build();

        manageGuildInventory = CustomInventory.builder()
                .plugin(plugin)
                .manager(manager)
                .provider(new ManageGuildInventory(plugin))
                .rows(config.getInt("inventories.manage-guild.size"))
                .title(config.getColoredString("inventories.manage-guild.title"))
                .updateDelay(-1)
                .build();
    }

    public CustomInventory getPanelInventory() {
        return panelInventory;
    }

    public CustomInventory getManageGuildInventory() {
        return manageGuildInventory;
    }
}
