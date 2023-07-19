package de.ariesbuildings.world;

import de.ariesbuildings.I18n;
import lombok.Getter;
import org.bukkit.ChatColor;

public enum WorldStatus {

    CREATED(ChatColor.RED),
    WAITING(ChatColor.AQUA),
    WORK_IN_PROGRESS(ChatColor.YELLOW),
    FINISHED(ChatColor.DARK_GREEN),
    REWORK(ChatColor.LIGHT_PURPLE);

    @Getter private final ChatColor color;
    @Getter private final String statusName;

    WorldStatus(ChatColor statusColor) {
        this.color = statusColor;
        this.statusName = I18n.translate("world_status." + name().toLowerCase() + ".name");
    }


    public String getColoredName() {
        return color.toString() + statusName;
    }
}
