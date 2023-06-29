package de.ariesbuildings;

import de.ariesbuildings.commands.*;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.listener.*;
import de.ariesbuildings.listener.playeroptions.VoidDamageTeleportListener;
import de.ariesbuildings.listener.worldoptions.AntiBlockUpdateListeners;
import de.ariesbuildings.listener.worldoptions.EntityTargetPlayerListener;
import de.ariesbuildings.listener.worldoptions.PlayerWorldDamageListener;
import de.ariesbuildings.listener.worldoptions.WeatherChangeListener;
import de.ariesbuildings.managers.AriesPlayerManager;
import de.ariesbuildings.managers.AriesWorldManager;
import de.ariesbuildings.managers.VanishManager;
import io.papermc.lib.PaperLib;
import lombok.Getter;
import me.noci.quickutilities.quickcommand.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AriesSystem extends JavaPlugin {

    @Getter
    private static AriesSystem instance;

    private I18n i18n;
    @Getter private AriesWorldManager worldManager;
    @Getter private AriesPlayerManager playerManager;
    @Getter private VanishManager vanishManager;

    @Override
    public void onEnable() {
        PaperLib.suggestPaper(this);
        instance = this;

        AriesSystemConfig.setPlugin(this);
        AriesSystemConfig.load();

        this.i18n = new I18n();
        this.worldManager = new AriesWorldManager(this);
        this.playerManager = new AriesPlayerManager();
        this.vanishManager = new VanishManager();

        this.worldManager.loadSavedWorlds();

        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(I18n.translate("serverRestart")));

        this.worldManager.saveWorlds();
        this.vanishManager.stopTask();
        this.i18n.disable();
    }

    private void registerCommands() {
        CommandManager.register(new CommandGamemode(this));
        CommandManager.register(new CommandAntiBlockUpdate(this));
        CommandManager.register(new CommandVanish(this));
        CommandManager.register(new CommandConfig(this));
        CommandManager.register(new CommandWorld(this, this.worldManager));
        CommandManager.register(new CommandMenu(this));
    }

    private void registerListeners() {
        //WORLD OPTIONS
        registerListener(new AntiBlockUpdateListeners());
        registerListener(new EntityTargetPlayerListener());
        registerListener(new PlayerWorldDamageListener());
        registerListener(new WeatherChangeListener());

        //PLAYER OPTIONS
        registerListener(new VoidDamageTeleportListener());

        //OTHER
        registerListener(new PlayerJoinListener());
        registerListener(new PlayerQuitListener());
        registerListener(new PlayerLoginListener());
        registerListener(new PlayerChatListener());
        registerListener(new EntityTargetPlayerListener());
        registerListener(new FoodLevelChangeListener());
        registerListener(new OptionChangeListener());
        registerListener(new PostOptionChangeListener());
        registerListener(new PlayerCommandPreprocessListener());
        registerListener(new ServerListPingBukkitListener(), new ServerListPingPaperListener());
    }

    private void registerListener(Listener listener) {
        registerListener(listener, null);
    }

    private void registerListener(Listener bukkitListener, Listener paperListener) {
        PluginManager pluginManager = getServer().getPluginManager();
        Listener toRegister = bukkitListener;
        if(PaperLib.isPaper() && paperListener != null) toRegister = paperListener;
        pluginManager.registerEvents(toRegister, this);
    }

}
