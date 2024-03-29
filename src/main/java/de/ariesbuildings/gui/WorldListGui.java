package de.ariesbuildings.gui;

import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.guiitem.InventoryConstants;
import de.ariesbuildings.gui.provider.AriesPagedGuiProvider;
import de.ariesbuildings.gui.provider.AriesProvider;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.WorldVisibility;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.PageContent;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.InventoryPattern;
import me.noci.quickutilities.utils.QuickItemStack;

import java.util.List;

public class WorldListGui extends AriesPagedGuiProvider {

    private static String inventoryTitle(WorldVisibility visibility) {
        return switch (visibility) {
            case PUBLIC -> I18n.translate("gui.world_list.title.public");
            case PRIVATE -> I18n.translate("gui.world_list.title.private");
            case ARCHIVED -> I18n.translate("gui.world_list.title.archived");
        };
    }

    private final WorldVisibility visibility;
    private final AriesProvider previousGui;

    public WorldListGui(WorldVisibility visibility) {
        this(visibility, null);
    }

    public WorldListGui(WorldVisibility visibility, AriesProvider previousGui) {
        super(inventoryTitle(visibility), InventoryConstants.FULL_INV_SIZE);
        this.visibility = visibility;
        this.previousGui = previousGui;
    }

    @Override
    protected void init(AriesPlayer player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        content.fillSlots(GuiItem.empty(), InventoryPattern.box(2, 4));
        if (previousGui != null) content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));
    }

    @Override
    protected void initPage(AriesPlayer player, PageContent content) {
        content.setItemSlots(InventoryPattern.box(2, 4));
        content.setPreviousPageItem(Slot.getSlot(6, 1), InventoryConstants.PREVIOUS_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);
        content.setNextPageItem(Slot.getSlot(6, 8), InventoryConstants.NEXT_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);

        List<AriesWorld> worlds = AriesSystem.getInstance().getWorldManager().getWorlds(visibility, player);

        GuiItem[] guiItems = worlds.stream().map(world -> createWorldGuiItem(world, player, this)).toArray(GuiItem[]::new);
        content.setPageContent(guiItems);
        content.updatePage();
    }

    private static GuiItem createWorldGuiItem(AriesWorld world, AriesPlayer player, WorldListGui previousGui) {
        QuickItemStack worldItem = InventoryConstants.worldDisplayIcon(world);

        world.getWorldCreator().ifPresent(uuid -> {
            if (uuid.equals(player.getUUID())) {
                worldItem.glow();
            }
        });

        String interactInfoLore = I18n.translate("gui.world_list.item.world_display.lore.interact_info");
        List<String> lore = worldItem.getLore();
        if (lore != null) {
            lore.add("");
            lore.add(interactInfoLore);
            worldItem.setLore(lore);
        }

        return worldItem.asGuiItem(event -> {
            switch (event.getClick()) {
                case LEFT -> world.teleport(event.getPlayer(), true);
                case RIGHT -> new WorldSettingsGui(world, previousGui).provide(event.getPlayer());
            }
        });
    }
}
