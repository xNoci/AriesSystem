package de.ariesbuildings;

import de.ariesbuildings.commands.CommandAntiBlockUpdate;
import de.ariesbuildings.commands.CommandGamemode;
import de.ariesbuildings.commands.CommandVanish;
import de.ariesbuildings.commands.system.CommandManager;
import de.ariesbuildings.listener.*;
import de.ariesbuildings.managers.AriesPlayerManager;
import de.ariesbuildings.managers.VanishManager;
import io.papermc.lib.PaperLib;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AriesSystem extends JavaPlugin {

    @Getter
    private static AriesSystem instance;

    private I18n i18n;
    @Getter private AriesPlayerManager playerManager;
    @Getter private VanishManager vanishManager;

    @Override
    public void onEnable() {
        PaperLib.suggestPaper(this);
        instance = this;

        this.i18n = new I18n();
        this.playerManager = new AriesPlayerManager();
        this.vanishManager = new VanishManager();

        registerListener();
        registerCommands();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(I18n.translate("serverRestart")));

        this.vanishManager.stopTask();
        this.i18n.disable();
    }

    private void registerListener() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerJoinListener(), this);
        pluginManager.registerEvents(new PlayerQuitListener(), this);
        pluginManager.registerEvents(new PlayerLoginListener(), this);
        pluginManager.registerEvents(new PlayerDamageListener(), this);
        pluginManager.registerEvents(new PlayerChatListener(), this);
        pluginManager.registerEvents(new EntityTargetEntityListener(), this);
        pluginManager.registerEvents(new FoodLevelChangeListener(), this);
        pluginManager.registerEvents(new OptionChangeListener(), this);
        pluginManager.registerEvents(new PostOptionChangeListener(), this);
        pluginManager.registerEvents(PaperLib.isPaper() ? new ServerListPingPaperListener() : new ServerListPingBukkitListener(), this);
    }

    private void registerCommands() {
        CommandManager.register(new CommandGamemode(this));
        CommandManager.register(new CommandAntiBlockUpdate(this));
        CommandManager.register(new CommandVanish(this));
    }

}
