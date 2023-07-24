package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.guiitem.InventoryConstants;
import de.ariesbuildings.gui.guiitem.optionitem.OptionItemBuilder;
import de.ariesbuildings.gui.provider.AriesGuiProvider;
import de.ariesbuildings.gui.provider.AriesProvider;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.WorldStatus;
import de.ariesbuildings.world.WorldVisibility;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.InventoryPattern;
import me.noci.quickutilities.utils.QuickItemStack;

public class WorldSettingsGui extends AriesGuiProvider {

    private final AriesProvider previousGui;
    private final AriesWorld world;

    public WorldSettingsGui(AriesWorld world) {
        this(world, null);
    }

    public WorldSettingsGui(AriesWorld world, AriesProvider previousGui) {
        super(I18n.translate("gui.world_settings.title"), InventoryConstants.FULL_INV_SIZE);
        this.previousGui = previousGui;
        this.world = world;
    }

    @Override
    protected void init(AriesPlayer player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        content.fillSlots(GuiItem.empty(), InventoryPattern.box(3, 4));
        if (previousGui != null) content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));
        content.setItem(Slot.getSlot(1, 5), InventoryConstants.worldDisplayIcon(world).asGuiItem());
        content.setItem(Slot.getSlot(2, 5), new QuickItemStack(XMaterial.OAK_SIGN.parseMaterial())
                .setDisplayName(I18n.translate("gui.world_settings.item.builder_list.displayname"))
                .setLore(
                        "",
                        I18n.translate("gui.world_settings.item.builder_list.lore.creator", world.getCreatorAsString()),
                        I18n.translate("gui.world_settings.item.builder_list.lore.builders", world.getBuildersAsString()),
                        "",
                        I18n.translate("gui.world_settings.item.builder_list.lore.click_to_edit")
                )
                .asGuiItem(event -> {
                    new EditBuilderGui(world, this).provide(event.getPlayer());
                }));

        OptionItemBuilder.of(WorldOption.WORLD_VISIBILITY, WorldVisibility.class)
                .slot(3, 2)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_VISIBILITY))
                .mapValue(WorldVisibility.PUBLIC, new QuickItemStack(XMaterial.WRITTEN_BOOK.parseMaterial()).setLore("", I18n.translate("gui.world_settings.item.world_visibility_.lore.public")).addItemFlags())
                .mapValue(WorldVisibility.PRIVATE, new QuickItemStack(XMaterial.BOOK.parseMaterial()).setLore("", I18n.translate("gui.world_settings.item.world_visibility_.lore.private")).addItemFlags())
                .mapValue(WorldVisibility.ARCHIVED, new QuickItemStack(XMaterial.BOOKSHELF.parseMaterial()).setLore("", I18n.translate("gui.world_settings.item.world_visibility_.lore.archived")).addItemFlags())
                .build();

        OptionItemBuilder.of(WorldOption.WORLD_STATUS, WorldStatus.class)
                .slot(3, 3)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_STATUS))
                .mapValue(WorldStatus.CREATED, new QuickItemStack(XMaterial.RED_WOOL.parseMaterial()).setLore("", I18n.translate("gui.world_settings.item.world_status_.lore.status", WorldStatus.CREATED.getColoredName())).addItemFlags())
                .mapValue(WorldStatus.WAITING, new QuickItemStack(XMaterial.LIGHT_BLUE_WOOL.parseMaterial()).setLore("", I18n.translate("gui.world_settings.item.world_status_.lore.status", WorldStatus.WAITING.getColoredName())).addItemFlags())
                .mapValue(WorldStatus.WORK_IN_PROGRESS, new QuickItemStack(XMaterial.YELLOW_WOOL.parseMaterial()).setLore("", I18n.translate("gui.world_settings.item.world_status_.lore.status", WorldStatus.WORK_IN_PROGRESS.getColoredName())).addItemFlags())
                .mapValue(WorldStatus.FINISHED, new QuickItemStack(XMaterial.LIME_WOOL.parseMaterial()).setLore("", I18n.translate("gui.world_settings.item.world_status_.lore.status", WorldStatus.FINISHED.getColoredName())).addItemFlags())
                .mapValue(WorldStatus.REWORK, new QuickItemStack(XMaterial.PINK_WOOL.parseMaterial()).setLore("", I18n.translate("gui.world_settings.item.world_status_.lore.status", WorldStatus.REWORK.getColoredName())).addItemFlags())
                .build();

        OptionItemBuilder.of(WorldOption.ANTI_BLOCK_UPDATE, boolean.class)
                .slot(3, 4)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_ANTI_BLOCK_UPDATE))
                .mapValue(true, new QuickItemStack(XMaterial.WATER_BUCKET.parseMaterial()).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.BUCKET.parseMaterial()).addItemFlags())
                .build();

        OptionItemBuilder.of(WorldOption.PLAYER_DAMAGE, boolean.class)
                .slot(3, 5)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_PLAYER_DAMAGE))
                .mapValue(true, new QuickItemStack(XMaterial.DIAMOND_SWORD.parseMaterial()).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.WOODEN_SWORD.parseMaterial()).addItemFlags())
                .build();

        OptionItemBuilder.of(WorldOption.ENTITY_TARGET_PLAYER, boolean.class)
                .slot(3, 6)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_ENTITY_TARGET_PLAYER))
                .mapValue(true, new QuickItemStack(XMaterial.ARROW.parseMaterial()).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.ARMOR_STAND.parseMaterial()).addItemFlags())
                .build();

        OptionItemBuilder.of(WorldOption.ALLOW_HOSTILE_SPAWNING, boolean.class)
                .slot(3, 7)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_HOSTILE_SPAWN))
                .mapValue(true, new QuickItemStack(XMaterial.CREEPER_SPAWN_EGG.parseMaterial()).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.SKELETON_SPAWN_EGG.parseMaterial()).addItemFlags())
                .build();

        OptionItemBuilder.of(WorldOption.ALLOW_FRIENDLY_SPAWNING, boolean.class)
                .slot(3, 8)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_FRIENDLY_SPAWN))
                .mapValue(true, new QuickItemStack(XMaterial.OCELOT_SPAWN_EGG.parseMaterial()).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.WOLF_SPAWN_EGG.parseMaterial()).addItemFlags())
                .build();

        OptionItemBuilder.of(WorldOption.WEATHER_CYCLE, boolean.class)
                .slot(4, 2)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .clickCondition(event -> world.hasWorldPermission(event.getPlayer(), Permission.WORLD_OPTION_WEATHER_CYCLE))
                .mapValue(true, new QuickItemStack(XMaterial.LIGHTNING_ROD.or(XMaterial.DAYLIGHT_DETECTOR).parseMaterial()).setLore("", I18n.translate("gui.world_settings.item.option.lore_true")).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.DEAD_BUSH.parseMaterial()).setLore("", I18n.translate("gui.world_settings.item.option.lore_false")).addItemFlags())
                .build();
    }

}
