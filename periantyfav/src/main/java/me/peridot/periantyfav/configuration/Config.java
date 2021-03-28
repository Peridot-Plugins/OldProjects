package me.peridot.periantyfav.configuration;

import me.peridot.periantyfav.PeriAntyFav;
import org.bukkit.configuration.ConfigurationSection;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Config {

    private final PeriAntyFav plugin;

    public List<String> newItemDescription = new ArrayList<>();
    public DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");

    public Config(PeriAntyFav plugin) {
        this.plugin = plugin;
    }

    public void loadConfiguration() {
        plugin.saveDefaultConfig();
        ConfigurationSection config = plugin.getConfig();

        newItemDescription = config.getStringList("new_item_description");
        dateFormat = DateTimeFormatter.ofPattern(config.getString("date_format"));
    }
}
