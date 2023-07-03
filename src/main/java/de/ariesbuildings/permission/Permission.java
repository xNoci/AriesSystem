package de.ariesbuildings.permission;

public class Permission {

    //PERMISSION PREFIX
    private static final String PERMISSION_PREFIX = "aries."; //Default permission prefix to use
    private static final String WORLD_PREFIX = PERMISSION_PREFIX + "world.";
    private static final String WORLD_OPTION_PREFIX = WORLD_PREFIX + "option.";
    private static final String CONFIG_PREFIX = PERMISSION_PREFIX + "config.";

    //COMANDS
    public static final String COMMAND_GAMEMODE = PERMISSION_PREFIX + "gamemode"; //Player can use /gamemode <type> and /gm <type>
    public static final String COMMAND_GAMEMODE_OTHER = PERMISSION_PREFIX + "gamemode.other"; //Player can use /gamemode <type> [Player] and /gm <type> [Player]

    //WORLD OPTIONS
    public static final String WORLD_BYPASS_BUILDER = WORLD_PREFIX + "bypass_builder"; //This allows a player to bypass the builder role to change settings and use world commands
    public static final String WORLD_OPTION_ANTI_BLOCK_UPDATE = WORLD_OPTION_PREFIX + "physics"; //Player can toggle block updates with /abu, /antiblockupdate and /physics or via world menu
    public static final String WORLD_OPTION_VISIBILITY = WORLD_OPTION_PREFIX + "visibility"; //Player can change visibility via world menu
    public static final String WORLD_OPTION_STATUS = WORLD_OPTION_PREFIX + "status"; //Player can change visibility via world menu
    public static final String WORLD_OPTION_PLAYER_DAMAGE = WORLD_OPTION_PREFIX + "player_damage"; //Player can toggle player damage via world menu
    public static final String WORLD_OPTION_ENTITY_TARGET_PLAYER = WORLD_OPTION_PREFIX + "entity_target_player"; //Player can toggle entity player target via world menu
    public static final String WORLD_OPTION_WEATHER_CYCLE = WORLD_OPTION_PREFIX + "weather_cycle"; //Player can toggle weather cycle via world menu

    public static final String WORLD_IMPORT = PERMISSION_PREFIX + "import";
    public static final String WORLD_UNIMPORT = PERMISSION_PREFIX + "unimport";
    public static final String WORLD_CREATE = PERMISSION_PREFIX + "create";

    public static final String WORLD_TP = WORLD_PREFIX + "tp";


    //PLAYER OPTIONS
    public static final String PLAYER_OPTION_VANISH = PERMISSION_PREFIX + "vanish"; //Player can use /vanish and also see vanish player

    //CONFIG
    public static final String CONFIG_RELOAD = CONFIG_PREFIX + "reload"; //Allows the player to reload the config with /config reload or /cfg reload
    public static final String CONFIG_DISPLAY = CONFIG_PREFIX + "display"; //Allows the player to display the config with /config display or /cfg display

    //OTHER
    public static final String CHAT_USE_COLOR = PERMISSION_PREFIX + "chatcolor"; //Player can write colored text in chat

    //OWNER PERMISSION
    private static final String OWNR_PERM = PERMISSION_PREFIX + ".owner.";
    public static final String OWNR_GUI_STOP_SERVER = OWNR_PERM + "gui.stop_server";

}