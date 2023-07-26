package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.guiitem.InventoryConstants;
import de.ariesbuildings.gui.guiitem.button.GuiItemButton;
import de.ariesbuildings.gui.provider.AriesPagedGuiProvider;
import de.ariesbuildings.gui.provider.AriesProvider;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.permission.RankInfo;
import de.ariesbuildings.utils.Input;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.creator.CreatorID;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.PageContent;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.BukkitUnit;
import me.noci.quickutilities.utils.InventoryPattern;
import me.noci.quickutilities.utils.QuickItemStack;
import me.noci.quickutilities.utils.SkullItem;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.ClickType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EditBuilderGui extends AriesPagedGuiProvider {

    private final AriesWorld world;
    private final AriesProvider previousGui;


    protected EditBuilderGui(AriesWorld world, AriesProvider previousGui) {
        super(I18n.translate("gui.edit_builder.title", world.getWorldName()), InventoryConstants.FULL_INV_SIZE);
        this.world = world;
        this.previousGui = previousGui;
    }

    @Override
    protected void init(AriesPlayer player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        content.fillSlots(GuiItem.empty(), InventoryPattern.box(3, 4));
        if (previousGui != null) content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));
        content.setItem(Slot.getSlot(1, 5), InventoryConstants.worldDisplayIcon(world).asGuiItem());
        if (world.hasWorldPermission(player, Permission.WORLD_ADD_BUILD)) {
            GuiItemButton.builder(XMaterial.OAK_SIGN, "gui.edit_builder.add_builder.displayname")
                    .lore("", I18n.translate("gui.edit_builder.add_builder.lore"))
                    .clickType(ClickType.LEFT)
                    .click((ariesPlayer, button) -> handleAddBuilder(ariesPlayer))
                    .build(content, Slot.getSlot(2, 5));
        }
    }

    @Override
    protected void initPage(AriesPlayer player, PageContent content) {
        content.setItemSlots(InventoryPattern.box(3, 4));
        content.setPreviousPageItem(Slot.getSlot(6, 1), InventoryConstants.PREVIOUS_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);
        content.setNextPageItem(Slot.getSlot(6, 8), InventoryConstants.NEXT_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);

        content.setPageContent(builders(player));
    }

    private GuiItem[] builders(AriesPlayer viewer) {
        List<UUID> builders = Lists.newArrayList();
        world.getWorldCreator()
                .filter(uuid -> CreatorID.match(uuid).isEmpty()) //If creator isn't a player don't show him
                .ifPresent(builders::add);
        builders.addAll(world.getBuilders());
        return builders.stream().map(uuid -> builder(viewer, uuid, world.isCreator(uuid))).toArray(GuiItem[]::new);
    }

    private GuiItem builder(AriesPlayer viewer, UUID uuid, boolean creator) {
        String name = AriesSystem.getInstance().getPlayerManager().getPlayerName(uuid);
        RankInfo rankInfo = RankInfo.getInfo(uuid);

        QuickItemStack playerHead = SkullItem.getPlayerSkull(name);
        playerHead.addItemFlags();
        playerHead.setDisplayName(I18n.translate("gui.edit_builder.player.displayname", name));

        List<String> lore = Lists.newArrayList();
        lore.add("");
        lore.add(I18n.translate("gui.edit_builder.player.lore.rank", rankInfo.getColor() + rankInfo.getName()));
        lore.add(I18n.translate("gui.edit_builder.player.lore.creator." + creator));
        if (world.isCreator(viewer.getUUID()) && !creator) {
            lore.add("");
            lore.add(I18n.translate("gui.edit_builder.player.lore.action"));
        }
        playerHead.setLore(lore);

        return playerHead.asGuiItem(event -> {
            event.setCancelled(true);
            if (!world.isCreator(viewer.getUUID()) || event.getClick() != ClickType.LEFT) return;

            world.getBuilders().remove(uuid);
            new EditBuilderGui(world, previousGui).provide(viewer);
        });
    }

    private void handleAddBuilder(AriesPlayer player) {
        if (!world.hasWorldPermission(player, Permission.WORLD_ADD_BUILD)) return;
        List<String> signLines = Lists.newArrayList();
        signLines.add(I18n.translate("gui.edit_builder.add_builder.input.line.2"));
        signLines.add(I18n.translate("gui.edit_builder.add_builder.input.line.3"));
        signLines.add(I18n.translate("gui.edit_builder.add_builder.input.line.4"));

        Input.sign(player, 1, signLines, input -> {
            Optional<UUID> inputUUID = AriesSystem.getInstance().getPlayerManager().getPlayerUUID(input);
            inputUUID.ifPresent(uuid -> {
                if (world.isBuilder(uuid)) return;
                world.getBuilders().add(uuid);
            });
            Bukkit.getScheduler().runTaskLater(AriesSystem.getInstance(), () -> new EditBuilderGui(world, previousGui).provide(player), BukkitUnit.TICKS.toTicks(1));
        }, canceled -> {
            Bukkit.getScheduler().runTaskLater(AriesSystem.getInstance(), () -> new EditBuilderGui(world, previousGui).provide(player), BukkitUnit.TICKS.toTicks(1));
        });
    }

}
