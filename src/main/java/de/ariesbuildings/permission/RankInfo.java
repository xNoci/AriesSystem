package de.ariesbuildings.permission;

import de.ariesbuildings.I18n;
import de.ariesbuildings.hooks.LuckPermsHook;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class RankInfo {

    protected final UUID owner;

    public RankInfo(UUID uuid) {
        this.owner = uuid;
    }

    public static RankInfo getInfo(Player player) {
        return getInfo(player.getUniqueId());
    }

    public static RankInfo getInfo(UUID uuid) {
        if (LuckPermsHook.isEnabled()) {
            return new LuckPermsRankInfo(uuid);
        }
        return new DefaultRankInfo(uuid);
    }

    public abstract String getName();

    public abstract String getDisplayname();

    public abstract ChatColor getColor();

    public String getPrefix() {
        return I18n.translate("tab_list.player_list.prefix_format", getColor() + prefix(), getColor());
    }

    public int getSortID() {
        try {
            return Integer.parseInt("%03d".formatted(sortID()));
        } catch (NumberFormatException e) {
            return sortID();
        }
    }

    public String getTeamName() {
        return getSortID() + owner.toString().replaceAll("-", "");
    }

    protected abstract String prefix();

    protected abstract int sortID();

}
