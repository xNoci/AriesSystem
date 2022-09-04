package de.ariesbuildings.objects;

import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.utils.OptionHolder;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AriesPlayer extends OptionHolder<PlayerOption> {

    @Getter private final Player base;
    @Getter private final UUID uuid;

    protected AriesPlayer(Player player) {
        this.base = player;
        this.uuid = player.getUniqueId();
    }

    @Override
    protected OptionChangeEvent createOptionChangeEvent(PlayerOption option, Object oldValue, Object newValue) {
        return new OptionChangeEvent(option, oldValue, newValue, this);
    }
}
