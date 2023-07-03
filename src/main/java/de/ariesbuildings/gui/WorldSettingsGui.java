package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.optionitem.OptionItemBuilder;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.WorldStatus;
import de.ariesbuildings.world.WorldVisibility;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.QuickGUIProvider;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.InventoryPattern;
import me.noci.quickutilities.utils.QuickItemStack;
import org.bukkit.entity.Player;

public class WorldSettingsGui extends QuickGUIProvider {

    private final QuickGUIProvider previousGui;
    private final AriesWorld world;

    public WorldSettingsGui(AriesWorld world) {
        this(world, null);
    }

    public WorldSettingsGui(AriesWorld world, QuickGUIProvider previousGui) {
        super(I18n.translate("gui.world_settings.title"), InventoryConstants.FULL_INV_SIZE);
        this.previousGui = previousGui;
        this.world = world;
    }

    @Override
    public void init(Player player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        content.fillSlots(GuiItem.empty(), InventoryPattern.box(3, 4));
        if (previousGui != null) content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));
        content.setItem(Slot.getSlot(1, 5), InventoryConstants.worldDisplayIcon(world).asGuiItem());

        String visibilityDisplayname = I18n.translate("gui.world_settings.item.world_visibility_.displayname");
        OptionItemBuilder.of(WorldOption.WORLD_VISIBILITY, WorldVisibility.class)
                .slot(3, 2)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_VISIBILITY))
                .mapValue(WorldVisibility.PUBLIC, new QuickItemStack(XMaterial.WRITTEN_BOOK.parseMaterial(), visibilityDisplayname).setLore("", I18n.translate("gui.world_settings.item.world_visibility_.lore.public")).addItemFlags())
                .mapValue(WorldVisibility.PRIVATE, new QuickItemStack(XMaterial.BOOK.parseMaterial(), visibilityDisplayname).setLore("", I18n.translate("gui.world_settings.item.world_visibility_.lore.private")).addItemFlags())
                .mapValue(WorldVisibility.ARCHIVED, new QuickItemStack(XMaterial.BOOKSHELF.parseMaterial(), visibilityDisplayname).setLore("", I18n.translate("gui.world_settings.item.world_visibility_.lore.archived")).addItemFlags())
                .build();

        String statusDisplayname = I18n.translate("gui.world_settings.item.world_status.displayname");
        OptionItemBuilder.of(WorldOption.WORLD_STATUS, WorldStatus.class)
                .slot(3, 3)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_STATUS))
                .mapValue(WorldStatus.CREATED, new QuickItemStack(XMaterial.RED_WOOL.parseMaterial(), statusDisplayname).setLore("", I18n.translate("gui.world_settings.item.world_status_.lore.created")).addItemFlags())
                .mapValue(WorldStatus.WAITING, new QuickItemStack(XMaterial.LIGHT_BLUE_WOOL.parseMaterial(), statusDisplayname).setLore("", I18n.translate("gui.world_settings.item.world_status_.lore.waiting")).addItemFlags())
                .mapValue(WorldStatus.WORK_IN_PROGRESS, new QuickItemStack(XMaterial.YELLOW_WOOL.parseMaterial(), statusDisplayname).setLore("", I18n.translate("gui.world_settings.item.world_status_.lore.wip")).addItemFlags())
                .mapValue(WorldStatus.FINISHED, new QuickItemStack(XMaterial.LIME_WOOL.parseMaterial(), statusDisplayname).setLore("", I18n.translate("gui.world_settings.item.world_status_.lore.finished")).addItemFlags())
                .mapValue(WorldStatus.REWORK, new QuickItemStack(XMaterial.PINK_WOOL.parseMaterial(), statusDisplayname).setLore("", I18n.translate("gui.world_settings.item.world_status_.lore.rework")).addItemFlags())
                .build();

        String abuDisplayname = I18n.translate("gui.world_settings.item.abu.displayname");
        OptionItemBuilder.of(WorldOption.ANTI_BLOCK_UPDATE, boolean.class)
                .slot(3, 4)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_ANTI_BLOCK_UPDATE))
                .mapValue(true, new QuickItemStack(XMaterial.WATER_BUCKET.parseMaterial(), abuDisplayname).setLore("", I18n.translate("gui.world_settings.item.option.lore_true")).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.BUCKET.parseMaterial(), abuDisplayname).setLore("", I18n.translate("gui.world_settings.item.option.lore_false")).addItemFlags())
                .build();

        String playerDamageDisplayname = I18n.translate("gui.world_settings.item.player_damage.displayname");
        OptionItemBuilder.of(WorldOption.PLAYER_DAMAGE, boolean.class)
                .slot(3, 5)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_PLAYER_DAMAGE))
                .mapValue(true, new QuickItemStack(XMaterial.DIAMOND_SWORD.parseMaterial(), playerDamageDisplayname).setLore("", I18n.translate("gui.world_settings.item.option.lore_true")).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.WOODEN_SWORD.parseMaterial(), playerDamageDisplayname).setLore("", I18n.translate("gui.world_settings.item.option.lore_false")).addItemFlags())
                .build();

        String etpDisplayname = I18n.translate("gui.world_settings.item.entity_target_player.displayname");
        OptionItemBuilder.of(WorldOption.ENTITY_TARGET_PLAYER, boolean.class)
                .slot(3, 6)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_ENTITY_TARGET_PLAYER))
                .mapValue(true, new QuickItemStack(XMaterial.CREEPER_SPAWN_EGG.parseMaterial(), etpDisplayname).setLore("", I18n.translate("gui.world_settings.item.option.lore_true")).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.WOLF_SPAWN_EGG.parseMaterial(), etpDisplayname).setLore("", I18n.translate("gui.world_settings.item.option.lore_false")).addItemFlags())
                .build();

        String weatherCycleDisplayname = I18n.translate("gui.world_settings.item.weather_cycle.displayname");
        OptionItemBuilder.of(WorldOption.WEATHER_CYCLE, boolean.class)
                .slot(3, 7)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_WEATHER_CYCLE))
                .mapValue(true, new QuickItemStack(XMaterial.LIGHTNING_ROD.or(XMaterial.DAYLIGHT_DETECTOR).parseMaterial(), weatherCycleDisplayname).setLore("", I18n.translate("gui.world_settings.item.option.lore_true")).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.COBWEB.parseMaterial(), weatherCycleDisplayname).setLore("", I18n.translate("gui.world_settings.item.option.lore_false")).addItemFlags())
                .build();
    }

}
