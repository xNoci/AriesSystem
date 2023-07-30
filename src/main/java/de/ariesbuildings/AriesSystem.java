package de.ariesbuildings;

import de.ariesbuildings.commands.*;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.listener.*;
import de.ariesbuildings.listener.playeroptions.VoidDamageTeleportListener;
import de.ariesbuildings.listener.worldoptions.*;
import de.ariesbuildings.locales.LanguageLoader;
import de.ariesbuildings.managers.AriesPlayerManager;
import de.ariesbuildings.managers.AriesWorldManager;
import de.ariesbuildings.managers.VanishManager;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.permission.RankInfo;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.WorldStatus;
import de.ariesbuildings.world.WorldType;
import de.ariesbuildings.world.creator.CreatorID;
import io.papermc.lib.PaperLib;
import lombok.Getter;
import me.noci.quickutilities.quickcommand.CommandRegister;
import me.noci.quickutilities.quickcommand.mappings.CommandMapping;
import me.noci.quickutilities.quicktab.QuickTab;
import me.noci.quickutilities.quicktab.builder.QuickTabBuilder;
import me.noci.quickutilities.scoreboard.QuickBoard;
import me.noci.quickutilities.utils.BukkitUnit;
import me.noci.quickutilities.utils.ServerProperties;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

public class AriesSystem extends JavaPlugin {

    @Getter
    private static AriesSystem instance;

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

        LanguageLoader.initialise();

        this.serverData = new ServerData();
        this.serverData.deserialize();
        this.worldManager = new AriesWorldManager(this);
        this.playerManager = new AriesPlayerManager();
        this.vanishManager = new VanishManager();

        this.worldManager.loadSavedWorlds();

        registerListeners();
        registerCommands();

        loadTutorialWorld();


        createTabList();
        createScoreboard();
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(player -> player.kickPlayer(I18n.translate("serverRestart")));

        this.serverData.serialize();
        this.worldManager.saveWorlds();
        this.vanishManager.stopTask();
    }

    private void registerCommands() {
        CommandMapping.registerPlayerMapping(AriesPlayer.class, player -> playerManager.getPlayer(player));
        CommandMapping.registerArgumentMapping(AriesPlayer.class, argument -> playerManager.getPlayer(argument).orElse(null));
        CommandMapping.registerArgumentMapping(AriesWorld.class, argument -> worldManager.getWorld(argument).orElse(null));

        CommandRegister.register(new CommandAriesSystem(this));
        CommandRegister.register(new CommandGamemode(this));
        CommandRegister.register(new CommandAntiBlockUpdate(this));
        CommandRegister.register(new CommandVanish(this));
        CommandRegister.register(new CommandConfig(this));
        CommandRegister.register(new CommandWorld(this, this.worldManager));
        CommandRegister.register(new CommandMenu(this));
        CommandRegister.register(new CommandReloadLocals(this));
    }

    private void registerListeners() {
        //WORLD OPTIONS
        registerListener(new AntiBlockUpdateListeners());
        registerListener(new EntityTargetPlayerListener());
        registerListener(new PlayerWorldDamageListener());
        registerListener(new WeatherChangeListener());
        registerListener(new MobSpawnListener());
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
        new PlayerWorldChangeListener();
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

    private void createTabList() {
        QuickBoard.setUpdatingScoreboard(this, 5, BukkitUnit.SECONDS, (p, scoreboard) -> {
            AriesPlayer player = playerManager.getPlayer(p);
            RankInfo rankInfo = player.getRankInfo();
            Optional<AriesWorld> world = worldManager.getWorld(player);
            String unknownWorld = I18n.translate("scoreboard.line.world_name_display.unknown");
            String unknownWorldStatus = I18n.translate("scoreboard.line.world_status_display.unknown");

            String rankName = rankInfo.getColor() + rankInfo.getDisplayname();
            String worldName = world.map(AriesWorld::getWorldName).orElse(unknownWorld);
            String worldStatus = world.map(w -> w.getOptions().get(WorldOption.WORLD_STATUS, WorldStatus.class)).map(WorldStatus::getColoredName).orElse(unknownWorldStatus);

            scoreboard.updateTitle(I18n.translate("scoreboard.title"));

            scoreboard.updateLine(0, "");
            scoreboard.updateLine(1, I18n.translate("scoreboard.line.your_rank"));
            scoreboard.updateLine(2, I18n.translate("scoreboard.line.your_rank_display", rankName));
            scoreboard.updateLine(3, "");
            scoreboard.updateLine(4, I18n.translate("scoreboard.line.world_name"));
            scoreboard.updateLine(5, I18n.translate("scoreboard.line.world_name_display", worldName));
            scoreboard.updateLine(6, "");
            scoreboard.updateLine(7, I18n.translate("scoreboard.line.world_status"));
            scoreboard.updateLine(8, I18n.translate("scoreboard.line.world_status_display", worldStatus));
            scoreboard.updateLine(9, "");
        });
    }

    private void createScoreboard() {
        QuickTabBuilder builder = QuickTab.builder()
                .prefix((player, target) -> RankInfo.getInfo(target).getPrefix())
                .suffix((player, target) -> {
                    String worldName = AriesSystem.getInstance().getWorldManager().getWorld(target).map(AriesWorld::getWorldName).orElse(I18n.translate("tab_list.player_list.suffix.unknown_world"));
                    return I18n.translate("tab_list.player_list.suffix", worldName);
                })
                .color((player, target) -> RankInfo.getInfo(target).getColor())
                .sortID((player, target) -> RankInfo.getInfo(target).getSortID());
        QuickTab.setUpdatingTabList(builder);
    }

    public void teleportToSpawnWorld(AriesPlayer player) {
        this.serverData.getSpawnWorld().teleport(player, true, true);
    }

}
