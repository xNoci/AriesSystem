package de.ariesbuildings.objects;

import de.ariesbuildings.options.PlayerOption;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AriesPlayer {

    @Getter private final Player player;
    @Getter private final UUID uuid;

    protected AriesPlayer(Player player) {
        this.player = player;
        this.uuid = player.getUniqueId();
    }

    }
}
