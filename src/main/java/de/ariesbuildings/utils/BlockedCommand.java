package de.ariesbuildings.utils;

import org.bukkit.entity.Player;

public interface BlockedCommand {

    String getIdentifier();

    String getReasonKey();

    boolean shouldBlock(Player player);

    default boolean matches(String command) {
        String identifier = getIdentifier();

        return command.equalsIgnoreCase("/" + identifier);
    }

}
