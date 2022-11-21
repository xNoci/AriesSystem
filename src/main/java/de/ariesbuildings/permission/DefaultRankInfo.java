package de.ariesbuildings.permission;

import org.bukkit.ChatColor;

import java.util.UUID;

public class DefaultRankInfo extends RankInfo {

    public DefaultRankInfo(UUID uuid) {
        super(uuid);
    }

    @Override
    public String getName() {
        return "User";
    }

    @Override
    public String getDisplayname() {
        return "User";
    }

    @Override
    public ChatColor getColor() {
        return ChatColor.GRAY;
    }

    @Override
    protected String prefix() {
        return "User";
    }

    @Override
    protected int sortID() {
        return 0;
    }
}
