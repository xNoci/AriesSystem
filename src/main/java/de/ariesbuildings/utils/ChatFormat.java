package de.ariesbuildings.utils;

import de.ariesbuildings.permission.RankInfo;
import me.noci.quickutilities.utils.formatter.FormatKey;
import org.bukkit.entity.Player;

public final class ChatFormat {

    public static final FormatKey MESSAGE = FormatKey.MESSAGE;
    public static final FormatKey RANK_COLOR = FormatKey.of(RankInfo.class, "%rank_color%", rankInfo -> rankInfo.getColor().toString());
    public static final FormatKey RANK_DISPLAYNAME = FormatKey.of(RankInfo.class, "%rank_displayname%", RankInfo::getDisplayname);
    public static final FormatKey PLAYER_NAME = FormatKey.of(Player.class, "%rank_color%", Player::getName);

    private ChatFormat() {

    }

}