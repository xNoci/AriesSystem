package de.ariesbuildings.permission;

public class Permission {

    private static final String PERMISSION_PREFIX = "aries."; //Default permission prefix to use

    public static final String COMMAND_GAMEMODE = PERMISSION_PREFIX + "gamemode"; //Player can use /gamemode <type> and /gm <type>
    public static final String COMMAND_GAMEMODE_OTHER = PERMISSION_PREFIX + "gamemode.other"; //Player can use /gamemode <type> [Player] and /gm <type> [Player]

    public static final String CHAT_USE_COLOR = PERMISSION_PREFIX + "chatcolor"; //Player can write colored text in chat

    public static final String ANTI_BLOCK_UPDATE = PERMISSION_PREFIX + "antiblockupdate"; //Player can toggle block updates with /abu and /antiblockupdate

}