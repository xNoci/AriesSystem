package de.ariesbuildings.objects;

import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.events.PostOptionChangeEvent;
import de.ariesbuildings.options.OptionHolder;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.RankInfo;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AriesPlayer extends OptionHolder<PlayerOption> {

    @Getter private final Player base;
    private final UUID uuid;

    public AriesPlayer(Player player) {
        this.base = player;
        this.uuid = player.getUniqueId();
        player.sendActionBar();
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public RankInfo getRankInfo() {
        return RankInfo.getInfo(uuid);
    }

    @Override
    protected OptionChangeEvent createOptionChangeEvent(PlayerOption option, Object oldValue, Object newValue) {
        return new OptionChangeEvent(option, oldValue, newValue, this);
    }

    @Override
    protected PostOptionChangeEvent createPostOptionChangeEvent(PlayerOption option, Object oldValue, Object newValue) {
        return new PostOptionChangeEvent(option, oldValue, newValue, this);
    }
}
