package de.ariesbuildings.listener.worldoptions;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChangeListener implements Listener {

    @EventHandler
    private void handleWeatherChange(WeatherChangeEvent event) {
        AriesSystem.getInstance().getWorldManager()
                .getWorld(event.getWorld())
                .filter(world -> world.getOptions().isDisabled(WorldOption.WEATHER_CYCLE))
                .ifPresent(world -> event.setCancelled(true));
    }

}
