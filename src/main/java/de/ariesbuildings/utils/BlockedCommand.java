package de.ariesbuildings.utils;

import de.ariesbuildings.AriesPlayer;

public interface BlockedCommand {

    String getIdentifier();

    String getReasonKey();

    boolean shouldBlock(AriesPlayer player);

    default boolean matches(String command) {
        String identifier = getIdentifier();

        return command.equalsIgnoreCase("/" + identifier);
    }

}
