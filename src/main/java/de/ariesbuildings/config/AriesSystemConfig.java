package de.ariesbuildings.config;


public class AriesSystemConfig extends AbstractConfig {

    @ConfigEntry(name = "debug", comment = "When enabled the plugin will print various debug messages.")
    public static boolean DEBUG = false;

    public static void load() {
        new AriesSystemConfig();
    }

    protected AriesSystemConfig() {
        super("config.hocon", 1);
    }

}
