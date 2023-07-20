package de.ariesbuildings.config;

import org.bukkit.Difficulty;
import org.bukkit.plugin.java.JavaPlugin;

public class AriesSystemConfig extends AbstractConfig {

    @ConfigEntry(name = "debug", comment = "When enabled the plugin will print various debug messages.")
    public static boolean DEBUG = true;

    @ConfigEntry(name = "settings.server.shutdown_delay", comment = "Sets the delay in seconds when the server shuts down after pressing shutdown button in main menu")
    public static int SERVER_SHUTDOW_DELAY = 3;

    @ConfigEntry(name = "date_format", comment = "The date format used to indicate creation times")
    public static String DATE_FORMAT = "dd.MM.yyyy HH:mm:ss";

    //DEFAULT WORLD SETTINGS
    @ConfigEntry(name = "world.difficulty", comment = "Default difficulty of a world.")
    public static Difficulty WORLD_DIFFICULTY = Difficulty.PEACEFUL;
    @ConfigEntry(name = "world.time", comment = "Default time in a world.")
    public static long WORLD_TIME = 6000;
    @ConfigEntry(name = "world.void.placeSpawnBlock", comment = "Set whether to place a spawn block or not.")
    public static boolean WORLD_VOID_PLACE_SPAWN_BLOCK = true;
    @ConfigEntry(name = "world.void.blockType", comment = "Type of the spawn block.")
    public static String WORLD_VOID_BLOCK_TYPE = "STONE";

    @ConfigEntry(name = "world.build.blockWorldEdit", comment = "When this is set to true, WorldEdit will be block for players who are not a builder of the current world")
    public static boolean WORLD_BLOCK_WORLD_EDIT = true;

    //----------------------

    @ConfigEntry(name = "input.cancel_string", comment = "Set wich message will cancel the player input.")
    public static String PLAYER_INPUT_CANCEL = "!cancel";

    private static JavaPlugin plugin;

    public static void load() {
        info("Loading config values...");
        new AriesSystemConfig();
        info("Loaded config values from 'config.hocon'.");
    }

    public static void setPlugin(JavaPlugin plugin) {
        if (AriesSystemConfig.plugin != null) return;
        AriesSystemConfig.plugin = plugin;
    }

    public static void debug(String message, Object... args) {
        if (!DEBUG) return;

        if (plugin == null) {
            System.out.println("[AriesSystem/DEBUG] " + message);
            return;
        }

        plugin.getLogger().info("[DEBUG] " + String.format(message, args));
    }

    public static void info(String message, Object... args) {
        if (plugin == null) {
            System.out.println("[AriesSystem/INFO] " + message);
            return;
        }

        plugin.getLogger().info(String.format(message, args));
    }

    protected AriesSystemConfig() {
        super("config.hocon", 1);
    }

}
