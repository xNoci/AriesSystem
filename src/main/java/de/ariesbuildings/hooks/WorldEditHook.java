package de.ariesbuildings.hooks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class WorldEditHook {

    private static Plugin plugin;
    private static boolean checktPlugin = false;

    public static boolean isEnabled() {
        if (!checktPlugin) {
            checktPlugin = true;
            plugin = Bukkit.getPluginManager().getPlugin("WorldEdit");
        }
        if (plugin == null) return false;
        return plugin.isEnabled();
    }

}
