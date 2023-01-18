package de.ariesbuildings.utils;

import de.ariesbuildings.permission.RankInfo;
import me.noci.quickutilities.utils.formatter.FormatKey;
import org.bukkit.entity.Player;

public enum ChatFormat implements FormatKey {

    MESSAGE,
    RANK_COLOR{
        @Override
        public String replace(String message, Object o) {
            if (o instanceof RankInfo rankInfo) {
                return replace(message, rankInfo.getColor().toString());
            }
            return super.replace(message, o);
        }
    },
    RANK_DISPLAYNAME {
        @Override
        public String replace(String message, Object o) {
            if (o instanceof RankInfo rankInfo) {
                return replace(message, rankInfo.getDisplayname());
            }
            return super.replace(message, o);
        }
    },
    PLAYER_NAME {
        @Override
        public String replace(String message, Object o) {
            if (o instanceof Player player) {
                return replace(message, player.getName());
            }
            return super.replace(message, o);
        }
    };

    private final String key;

    ChatFormat() {
        this.key = "%" + name().toLowerCase() + "%";
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String toString() {
        return key;
    }
}
