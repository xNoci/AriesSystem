package de.ariesbuildings.objects;

import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.utils.OptionHolder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AriesPlayer extends OptionHolder<PlayerOption> {

    @Getter private final Player player;
    @Getter private final UUID uuid;

    protected AriesPlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
    }

    @Override
    protected OptionChangeEvent createOptionChangeEvent(PlayerOption option, Object oldValue, Object newValue) {
        return new OptionChangeEvent(option, oldValue, newValue, this);
    }
}
