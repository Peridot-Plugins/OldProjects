package me.mckoxu.spigot.lobbycore.listener;

import me.mckoxu.spigot.lobbycore.data.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if (Config.forceWeatherEnabled) {
            event.setCancelled(true);
        }
    }

}
