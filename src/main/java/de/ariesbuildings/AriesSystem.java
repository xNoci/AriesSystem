package de.ariesbuildings;

import de.ariesbuildings.commands.CommandAntiBlockUpdate;
import de.ariesbuildings.commands.CommandGamemode;
import de.ariesbuildings.commands.CommandVanish;
import de.ariesbuildings.listener.*;
import de.ariesbuildings.listener.playeroptions.VoidDamageTeleportListener;
import de.ariesbuildings.listener.worldoptions.AntiBlockUpdateListeners;
import de.ariesbuildings.listener.worldoptions.EntityTargetPlayerListener;
import de.ariesbuildings.listener.worldoptions.PlayerWorldDamageListener;
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

        this.i18n = new I18n();
        this.worldManager = new AriesWorldManager();
        this.playerManager = new AriesPlayerManager();
        this.vanishManager = new VanishManager();

        registerListeners();
        registerCommands();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(I18n.translate("serverRestart")));

        this.vanishManager.stopTask();
        this.i18n.disable();
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();

        //WORLD OPTIONS
        registerListener(new AntiBlockUpdateListeners());
        registerListener(new EntityTargetPlayerListener());
        registerListener(new PlayerWorldDamageListener());

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
        registerListener(PaperLib.isPaper() ? new ServerListPingPaperListener() : new ServerListPingBukkitListener());
    }

    private void registerCommands() {
        CommandManager.register(new CommandGamemode(this));
        CommandManager.register(new CommandAntiBlockUpdate(this));
        CommandManager.register(new CommandVanish(this));
    }

    private void registerListener(Listener listener) {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(listener, this);
    }

}
