package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.optionitem.OptionItemBuilder;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.world.AriesWorld;
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
        if(previousGui != null) content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));
        content.setItem(Slot.getSlot(1, 5), InventoryConstants.worldDisplayIcon(world).asGuiItem());

        //TODO Item Designs
        OptionItemBuilder.of(WorldOption.ANTI_BLOCK_UPDATE, Boolean.class)
                .slot(3, 2)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .mapValue(true, new QuickItemStack(XMaterial.GREEN_DYE.parseMaterial(), "TRUE"))
                .mapValue(false, new QuickItemStack(XMaterial.RED_DYE.parseMaterial(), "FALSE"))
                .build();

        OptionItemBuilder.of(WorldOption.PLAYER_DAMAGE, Boolean.class)
                .slot(3, 3)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .mapValue(true, new QuickItemStack(XMaterial.GREEN_DYE.parseMaterial(), "TRUE"))
                .mapValue(false, new QuickItemStack(XMaterial.RED_DYE.parseMaterial(), "FALSE"))
                .build();

        OptionItemBuilder.of(WorldOption.ENTITY_TARGET_PLAYER, Boolean.class)
                .slot(3, 4)
                .inventoryContent(content)
                .optionHolder(world.getOptions())
                .mapValue(true, new QuickItemStack(XMaterial.GREEN_DYE.parseMaterial(), "TRUE"))
                .mapValue(false, new QuickItemStack(XMaterial.RED_DYE.parseMaterial(), "FALSE"))
                .build();
    }

}
