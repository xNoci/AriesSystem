package de.ariesbuildings.permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class DefaultRankInfo extends RankInfo {

    public DefaultRankInfo(UUID uuid) {
        super(uuid);
    }

    @Override
    public String getName() {
        return isOperator() ? "Administrator" : "User";
    }

    @Override
    public String getDisplayname() {
        return isOperator() ? "Admin" : "User";
    }

    @Override
    public ChatColor getColor() {
        return isOperator() ? ChatColor.DARK_RED : ChatColor.GRAY;
    }

    @Override
    protected String prefix() {
        return isOperator() ? "Admin" : "User";
    }

    @Override
    protected int sortID() {
        return isOperator() ? 0 : 1;
    }

    private boolean isOperator() {
        return Bukkit.getOperators().stream().map(OfflinePlayer::getUniqueId).anyMatch(uuid -> uuid.equals(this.owner));
    }

}
