package de.ariesbuildings;

import de.ariesbuildings.listener.PlayerJoinListener;
import de.ariesbuildings.listener.PlayerQuitListener;
import io.papermc.lib.PaperLib;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AriesSystem extends JavaPlugin {

    @Override
    public void onEnable() {
        PaperLib.suggestPaper(this);

        this.i18n = new I18n();

        registerListener();
    }

    @Override
    public void onDisable() {
        i18n.disable();
    }

    public void registerListener() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
    }

}
