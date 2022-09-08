package de.ariesbuildings.permission;

public class Permission {

    //PERMISSION PREFIX
    private static final String PERMISSION_PREFIX = "aries."; //Default permission prefix to use

    //COMANDS
    public static final String COMMAND_GAMEMODE = PERMISSION_PREFIX + "gamemode"; //Player can use /gamemode <type> and /gm <type>
    public static final String COMMAND_GAMEMODE_OTHER = PERMISSION_PREFIX + "gamemode.other"; //Player can use /gamemode <type> [Player] and /gm <type> [Player]

    //WORLD OPTIONS
    public static final String WORLD_BYPASS_BUILDER = PERMISSION_PREFIX + "world.bypass_builder"; //This allows a player to bypass the builder role to change settings and use world commands
    public static final String WORLD_OPTION_ANTI_BLOCK_UPDATE = PERMISSION_PREFIX + "world.physics"; //Player can toggle block updates with /abu and /antiblockupdate

    //PLAYER OPTIONS
    public static final String PLAYER_OPTION_VANISH = PERMISSION_PREFIX + "vanish"; //Player can use /vanish and also see vanish player
    //OTHER
    public static final String CHAT_USE_COLOR = PERMISSION_PREFIX + "chatcolor"; //Player can write colored text in chat

}