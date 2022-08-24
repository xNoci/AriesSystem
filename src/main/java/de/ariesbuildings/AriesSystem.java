package de.ariesbuildings;

import de.ariesbuildings.commands.CommandGamemode;
import de.ariesbuildings.commands.system.CommandManager;
import de.ariesbuildings.listener.PlayerJoinListener;
import de.ariesbuildings.listener.PlayerQuitListener;
import io.papermc.lib.PaperLib;
import lombok.Getter;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AriesSystem extends JavaPlugin {

    @Getter private static AriesSystem instance;
    private I18n i18n;

    @Override
    public void onEnable() {
        instance = this;
        PaperLib.suggestPaper(this);

        this.i18n = new I18n();

        registerListener();
        registerCommands();
    }

    @Override
    public void onDisable() {
        i18n.disable();
    }

    private void registerListener() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
    }

    private void registerCommands() {
        CommandManager.register(new CommandGamemode(this));
    }

}
