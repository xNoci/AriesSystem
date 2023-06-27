package de.ariesbuildings.gui;

import com.google.common.collect.Lists;
import de.ariesbuildings.I18n;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.WorldVisibility;
import me.noci.quickutilities.inventory.*;
import me.noci.quickutilities.utils.InventoryPattern;
import org.bukkit.entity.Player;

import java.util.List;

public class WorldListGui extends PagedQuickGUIProvider {

    private static String inventoryTitle(WorldVisibility visibility) {
        return switch (visibility) {
            case PUBLIC -> I18n.translate("gui.world_list.title.public");
            case PRIVATE -> I18n.translate("gui.world_list.title.private");
            case ARCHIVED -> I18n.translate("gui.world_list.title.archived");
        };
    }

    private final WorldVisibility visibility;
    private final QuickGUIProvider previousGui;

    public WorldListGui(WorldVisibility visibility, QuickGUIProvider previousGui) {
        super(inventoryTitle(visibility), InventoryConstants.FULL_INV_SIZE);
        this.visibility = visibility;
        this.previousGui = previousGui;
    }

    @Override
    public void init(Player player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        content.fillSlots(GuiItem.empty(), InventoryPattern.box(2, 4));
        content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));
    }

    @Override
    public void initPage(Player player, PageContent content) {
        content.setItemSlots(InventoryPattern.box(2, 4));
        content.setPreviousPageItem(Slot.getSlot(6, 1), InventoryConstants.PREVIOUS_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);
        content.setNextPageItem(Slot.getSlot(6, 8), InventoryConstants.NEXT_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);

        List<AriesWorld> worlds = Lists.newArrayList(); //TODO get valid worlds

        GuiItem[] guiItems = worlds.stream().map(WorldListGui::createWorldGuiItem).toArray(GuiItem[]::new);
        content.setPageContent(guiItems);
        content.updatePage();
    }

    private static GuiItem createWorldGuiItem(AriesWorld world) {
        //TODO Create item for world
        return GuiItem.empty();
    }
}
