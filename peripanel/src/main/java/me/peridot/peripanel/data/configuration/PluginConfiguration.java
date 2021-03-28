package me.peridot.peripanel.data.configuration;

import api.peridot.periapi.configuration.ConfigurationProvider;
import api.peridot.periapi.items.ItemParser;
import api.peridot.periapi.utils.Pair;
import me.peridot.peripanel.PeriPanel;
import me.peridot.peripanel.inventories.ConfigurationButton;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PluginConfiguration extends ConfigurationProvider {

    private final PeriPanel plugin;

    public static int autosave_delay;

    public static DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    public static DecimalFormat decimalFormat = new DecimalFormat("#.00");

    public final List<Pair<Integer, ItemStack>> nameChangeCost = new ArrayList<>();
    public final List<Pair<Integer, ItemStack>> tagChangeCost = new ArrayList<>();
    public final List<Pair<Integer, ItemStack>> homeChangeCost = new ArrayList<>();

    public final Map<Integer, ConfigurationButton> panelInventoryButtons = new HashMap<>();

    public PluginConfiguration(PeriPanel plugin) {
        super(plugin.getConfig().getConfigurationSection("config"));
        this.plugin = plugin;
    }

    public void reloadConfiguration() {
        FileConfiguration configuration = plugin.getConfig();
        ConfigurationSection configurationSection = configuration.getConfigurationSection("config");

        autosave_delay = configurationSection.getInt("tasks.autosave");

        dateFormat = DateTimeFormatter.ofPattern(configurationSection.getString("format.date"));
        decimalFormat = new DecimalFormat(configurationSection.getString("format.decimal"));

        for (String itemString : configurationSection.getStringList("cost.name-change")) {
            String[] itemStringSplited = itemString.split(",", 3);
            try {
                int amount = Integer.parseInt(itemStringSplited[0]);
                Material material = Material.matchMaterial(itemStringSplited[1]);
                short data = Short.parseShort(itemStringSplited[2]);

                nameChangeCost.add(Pair.of(amount, new ItemStack(material, 1, data)));
            } catch (Exception ignored) {
            }
        }

        for (String itemString : configurationSection.getStringList("cost.tag-change")) {
            String[] itemStringSplited = itemString.split(",", 3);
            try {
                int amount = Integer.parseInt(itemStringSplited[0]);
                Material material = Material.matchMaterial(itemStringSplited[1]);
                short data = Short.parseShort(itemStringSplited[2]);

                tagChangeCost.add(Pair.of(amount, new ItemStack(material, 1, data)));
            } catch (Exception ignored) {
            }
        }

        for (String itemString : configurationSection.getStringList("cost.home-change")) {
            String[] itemStringSplited = itemString.split(",", 3);
            try {
                int amount = Integer.parseInt(itemStringSplited[0]);
                Material material = Material.matchMaterial(itemStringSplited[1]);
                short data = Short.parseShort(itemStringSplited[2]);

                homeChangeCost.add(Pair.of(amount, new ItemStack(material, 1, data)));
            } catch (Exception ignored) {
            }
        }

        for (String stringSection : configurationSection.getConfigurationSection("inventories.panel.buttons").getKeys(false)) {
            ConfigurationSection buttonSection = configurationSection.getConfigurationSection("inventories.panel.buttons." + stringSection);

            if (buttonSection.getName().equalsIgnoreCase("background")) {
                continue;
            }

            List<ConfigurationButton.Action> actions = buttonSection.getStringList("actions").stream().map(ConfigurationButton.Action::valueOf).collect(Collectors.toList());
            panelInventoryButtons.put(buttonSection.getInt("slot"), new ConfigurationButton(ItemParser.parseItemBuilder(buttonSection), actions));
        }
    }
}
