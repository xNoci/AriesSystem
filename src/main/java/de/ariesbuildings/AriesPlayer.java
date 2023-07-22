package de.ariesbuildings;

import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.messages.ActionBar;
import de.ariesbuildings.options.OptionHolder;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.RankInfo;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.RawLocation;
import io.papermc.lib.PaperLib;
import lombok.Getter;
import lombok.Setter;
import me.noci.quickutilities.utils.MathUtils;
import me.noci.quickutilities.utils.Require;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class AriesPlayer {

    private static final float DEFAULT_FLY_SPEED = 0.1f;

    @Getter private final OptionHolder<PlayerOption> options = new OptionHolder<>(this);

    @Getter private final Player base;
    @Getter private final String name;
    @Getter @Setter private RawLocation lastKnownLocation;
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

    public void sendTranslate(String key, Object... args) {
        sendMessage(I18n.translate(key, args));
    }

    public void sendNoPermissions() {
        sendMessage(I18n.noPermission());
    }

    public void sendMessage(String message) {
        if (!base.isOnline()) return;
        base.sendMessage(message);
    }

    public boolean hasPermission(String permission) {
        if (!base.isOnline()) return false;
        return base.hasPermission(permission);
    }

    public void clearActionBar() {
        if (!base.isOnline()) return;
        ActionBar.clearActionBar(this.base);
    }

    public void sendActionBar(String message) {
        if (!base.isOnline()) return;
        ActionBar.sendActionBar(base, message);
    }

    public void setFlySpeed(int speed) {
        if (!base.isOnline()) return;
        speed = MathUtils.clamp(1, 10, speed);
        base.setFlySpeed(DEFAULT_FLY_SPEED * speed);
    }

    public void playSound(XSound sound, float volume, float pitch) {
        if (!base.isOnline()) return;
        base.playSound(base.getLocation(), sound.parseSound(), volume, pitch);
    }

    public void updateFlySpeed() {
        setFlySpeed(options.get(PlayerOption.FLY_SPEED, int.class));
    }

    public Location getLocation() {
        Require.checkState(base.isOnline(), "Cannot get location because player %s is offline".formatted(name));
        return this.base.getLocation();
    }

    public void teleport(Location location) {
        if (!base.isOnline()) return;
        PaperLib.teleportAsync(this.base, location);
    }

    public Optional<AriesWorld> getWorld() {
        return AriesSystem.getInstance().getWorldManager().getWorld(this);
    }

}
