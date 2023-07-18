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
import de.ariesbuildings.world.WorldType;
import de.ariesbuildings.world.creator.CreatorID;
import io.papermc.lib.PaperLib;
import lombok.Getter;
import me.noci.quickutilities.quickcommand.CommandRegister;
import me.noci.quickutilities.quickcommand.mappings.CommandMapping;
import me.noci.quickutilities.utils.ServerProperties;
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
    @Getter private ServerData serverData;

    @Override
    public void onEnable() {
        PaperLib.suggestPaper(this);
        instance = this;

        AriesSystemConfig.setPlugin(this);
        AriesSystemConfig.load();

        this.serverData = new ServerData();
        this.serverData.deserialize();
        this.i18n = new I18n();
        this.worldManager = new AriesWorldManager(this);
        this.playerManager = new AriesPlayerManager();
        this.vanishManager = new VanishManager();

        this.worldManager.loadSavedWorlds();

        registerListeners();
        registerCommands();

        loadTutorialWorld();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(I18n.translate("serverRestart")));

        this.serverData.serialize();
        this.worldManager.saveWorlds();
        this.vanishManager.stopTask();
        this.i18n.disable();
    }

    private void registerCommands() {
        CommandMapping.registerPlayerMapping(AriesPlayer.class, player -> playerManager.getPlayer(player));
        CommandMapping.registerArgumentMapping(AriesPlayer.class, argument -> playerManager.getPlayer(argument));

        CommandRegister.register(new CommandGamemode(this));
        CommandRegister.register(new CommandAntiBlockUpdate(this));
        CommandRegister.register(new CommandVanish(this));
        CommandRegister.register(new CommandConfig(this));
        CommandRegister.register(new CommandWorld(this, this.worldManager));
        CommandRegister.register(new CommandMenu(this));
    }

    private void registerListeners() {
        //WORLD OPTIONS
        registerListener(new AntiBlockUpdateListeners());
        registerListener(new EntityTargetPlayerListener());
        registerListener(new PlayerWorldDamageListener());
        registerListener(new WeatherChangeListener());
        if (ServerProperties.isCommandBlockEnabled()) {
            AriesSystemConfig.debug("Command blocks are enabled - register ServerCommandListener");
            registerListener(new ServerCommandListener());
        }

        //PLAYER OPTIONS
        registerListener(new VoidDamageTeleportListener());

        //OTHER
        registerListener(new CustomBlockListener());
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
        if (PaperLib.isPaper() && paperListener != null) toRegister = paperListener;
        pluginManager.registerEvents(toRegister, this);
    }

    private void loadTutorialWorld() {
        if (this.serverData.isTutorialWorldLoaded()) return;
        AriesSystemConfig.debug("Creating tutorial world...");
        if (!this.worldManager.createWorld("tutorialWorld", CreatorID.TUTORIAL.getUUID(), WorldType.INTERNAL_TUTORIAL)) {
            AriesSystemConfig.debug("Failed to create tutorial world.");
        }
        this.serverData.setTutorialWorldLoaded(true);
    }

    public void teleportToSpawnWorld(AriesPlayer player) {
        this.serverData.getSpawnWorld().teleport(player, true, true);
    }

}
