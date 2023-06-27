package de.ariesbuildings.utils;

import de.ariesbuildings.permission.RankInfo;
import de.ariesbuildings.world.AriesWorld;
import me.noci.quickutilities.utils.formatter.FormatKey;
import org.bukkit.World;
import org.bukkit.entity.Player;

public final class ChatFormat {

    public static final FormatKey RANK_COLOR = FormatKey.of(RankInfo.class, "%rank_color%", rankInfo -> rankInfo.getColor().toString());
    public static final FormatKey RANK_DISPLAYNAME = FormatKey.of(RankInfo.class, "%rank_displayname%", RankInfo::getDisplayname);
    public static final FormatKey CURRENT_ARIES_WORLD = FormatKey.of(AriesWorld.class, "%current_world%", AriesWorld::getWorldName);
    public static final FormatKey CURRENT_BUKKIT_WORLD = FormatKey.of(World.class, CURRENT_ARIES_WORLD.getKey(), World::getName);

    private ChatFormat() {

    }

}
