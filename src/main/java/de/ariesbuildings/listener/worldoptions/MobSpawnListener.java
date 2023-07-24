package de.ariesbuildings.listener.worldoptions;

import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.options.WorldOption;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class MobSpawnListener implements Listener {

    @EventHandler
    public void handlePlayerDamage(CreatureSpawnEvent event) {
        LivingEntity entity = event.getEntity();

        //HOSTILE MOBS
        AriesSystem.getInstance().getWorldManager()
                .getWorld(entity)
                .filter(world -> entity instanceof Monster)
                .filter(world -> world.getOptions().isDisabled(WorldOption.ALLOW_HOSTILE_SPAWNING))
                .ifPresent(world -> event.setCancelled(true));

        //FRIENDLY MOBS
        AriesSystem.getInstance().getWorldManager()
                .getWorld(entity)
                .filter(world -> !(entity instanceof Monster))
                .filter(world -> world.getOptions().isDisabled(WorldOption.ALLOW_FRIENDLY_SPAWNING))
                .ifPresent(world -> event.setCancelled(true));
    }
}
