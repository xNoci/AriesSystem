package de.ariesbuildings.config;

import org.bukkit.Difficulty;

public class AriesSystemConfig extends AbstractConfig {

    @ConfigEntry(name = "debug", comment = "When enabled the plugin will print various debug messages.")
    public static boolean DEBUG = false;

    //DEFAULT WORLD SETTINGS
    @ConfigEntry(name = "world.difficulty", comment = "Default difficulty of a world.")
    public static Difficulty WORLD_DIFFICULTY = Difficulty.PEACEFUL;
    @ConfigEntry(name = "world.time", comment = "Default time in a world.")
    public static long WORLD_TIME = 6000;
    @ConfigEntry(name = "world.void.placeSpawnBlock", comment = "Set whether to place a spawn block or not.")
    public static boolean WORLD_VOID_PLACE_SPAWN_BLOCK = true;
    @ConfigEntry(name = "world.void.blockType", comment = "Type of the spawn block.")
    public static String WORLD_VOID_BLOCK_TYPE = "STONE";
    //----------------------

    public static void load() {
        new AriesSystemConfig();
    }

    protected AriesSystemConfig() {
        super("config.hocon", 1);
    }

}
