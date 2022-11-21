package de.ariesbuildings.objects;

import com.cryptomorin.xseries.messages.ActionBar;
import com.cryptomorin.xseries.messages.Titles;
import de.ariesbuildings.events.OptionChangeEvent;
import de.ariesbuildings.events.PostOptionChangeEvent;
import de.ariesbuildings.options.OptionHolder;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.RankInfo;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

public class AriesPlayer {

    @Getter private final OptionHolder<PlayerOption> options = new OptionHolder<>(this);

    @Getter private final Player base;
    @Getter private final String name;
    private final UUID uuid;

    public AriesPlayer(Player player) {
        this.base = player;
        this.name = player.getName();
        this.uuid = player.getUniqueId();
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public RankInfo getRankInfo() {
        return RankInfo.getInfo(uuid);
    }

    public void clearActionBar() {
        if(!base.isOnline()) return;
        ActionBar.clearActionBar(this.base);
    }

    public void sendActionBar(String message) {
        if(!base.isOnline()) return;
        ActionBar.sendActionBar(base, message);
    }

}
