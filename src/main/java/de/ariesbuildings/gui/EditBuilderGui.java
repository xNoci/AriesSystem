package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.permission.Permission;
import de.ariesbuildings.permission.RankInfo;
import de.ariesbuildings.utils.Input;
import de.ariesbuildings.world.AriesWorld;
import me.noci.quickutilities.inventory.*;
import me.noci.quickutilities.utils.BukkitUnit;
import me.noci.quickutilities.utils.InventoryPattern;
import me.noci.quickutilities.utils.QuickItemStack;
import me.noci.quickutilities.utils.SkullItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class EditBuilderGui extends PagedQuickGUIProvider {

    private static final QuickItemStack ADD_BUILDER_ITEM_TEMPLATE = new QuickItemStack(XMaterial.OAK_SIGN.parseMaterial(), I18n.translate("gui.edit_builder.add_builder.displayname"))
            .addItemFlags()
            .setLore("", I18n.translate("gui.edit_builder.add_builder.lore"));

    private final AriesWorld world;
    private final QuickGUIProvider previousGui;
    private final GuiItem addBuilderInput;

    protected EditBuilderGui(AriesWorld world, QuickGUIProvider previousGui) {
        super(I18n.translate("gui.edit_builder.title", world.getWorldName()), InventoryConstants.FULL_INV_SIZE);
        this.world = world;
        this.previousGui = previousGui;
        this.addBuilderInput = ADD_BUILDER_ITEM_TEMPLATE.asGuiItem(event -> {
            if (event.getClick() != ClickType.LEFT) return;
            if (!world.hasWorldPermission(event.getPlayer(), Permission.WORLD_ADD_BUILD)) return;
            List<String> signLines = Lists.newArrayList();
            signLines.add(I18n.translate("gui.edit_builder.add_builder.input.line.2"));
            signLines.add(I18n.translate("gui.edit_builder.add_builder.input.line.3"));
            signLines.add(I18n.translate("gui.edit_builder.add_builder.input.line.4"));

            Input.sign(event.getPlayer(), 1, signLines, input -> {
                Optional<UUID> inputUUID = AriesSystem.getInstance().getPlayerManager().getPlayerUUID(input);
                inputUUID.ifPresent(uuid -> {
                    if (world.isBuilder(uuid)) return;
                    world.getBuilders().add(uuid);
                });
                Bukkit.getScheduler().runTaskLater(AriesSystem.getInstance(), () -> new EditBuilderGui(world, previousGui).provide(event.getPlayer()), BukkitUnit.TICKS.toTicks(1));
            }, canceled -> {
                Bukkit.getScheduler().runTaskLater(AriesSystem.getInstance(), () -> new EditBuilderGui(world, previousGui).provide(canceled.getPlayer()), BukkitUnit.TICKS.toTicks(1));
            });
        });
    }

    @Override
    public void init(Player player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        content.fillSlots(GuiItem.empty(), InventoryPattern.box(3, 4));
        if (previousGui != null) content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));
        content.setItem(Slot.getSlot(1, 5), InventoryConstants.worldDisplayIcon(world).asGuiItem());
        if (world.hasWorldPermission(player, Permission.WORLD_ADD_BUILD)) {
            content.setItem(Slot.getSlot(2, 5), addBuilderInput);
        }
    }

    @Override
    public void initPage(Player player, PageContent content) {
        content.setItemSlots(InventoryPattern.box(3, 4));
        content.setPreviousPageItem(Slot.getSlot(6, 1), InventoryConstants.PREVIOUS_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);
        content.setNextPageItem(Slot.getSlot(6, 8), InventoryConstants.NEXT_PAGE, InventoryConstants.ITM_BACKGROUND_BLACK);

        content.setPageContent(builders(player));
    }

    private GuiItem[] builders(Player viewer) {
        List<UUID> builders = Lists.newArrayList();
        world.getWorldCreator().ifPresent(builders::add);
        builders.addAll(world.getBuilders());
        return builders.stream().map(uuid -> builder(viewer, uuid, world.isCreator(uuid))).toArray(GuiItem[]::new);
    }

    private GuiItem builder(Player viewer, UUID uuid, boolean creator) {
        String name = AriesSystem.getInstance().getPlayerManager().getPlayerName(uuid);
        RankInfo rankInfo = RankInfo.getInfo(uuid);

        QuickItemStack playerHead = SkullItem.getPlayerSkull(name);
        playerHead.addItemFlags();
        playerHead.setDisplayName(I18n.translate("gui.edit_builder.player.displayname", name));

        List<String> lore = Lists.newArrayList();
        lore.add("");
        lore.add(I18n.translate("gui.edit_builder.player.lore.rank", rankInfo.getColor() + rankInfo.getName()));
        lore.add(I18n.translate("gui.edit_builder.player.lore.creator." + creator));
        if (world.isCreator(viewer.getUniqueId()) && !creator) {
            lore.add("");
            lore.add(I18n.translate("gui.edit_builder.player.lore.action"));
        }
        playerHead.setLore(lore);

        return playerHead.asGuiItem(event -> {
            event.setCancelled(true);
            if (!world.isCreator(viewer.getUniqueId()) || event.getClick() != ClickType.LEFT) return;

            world.getBuilders().remove(uuid);
            new EditBuilderGui(world, previousGui).provide(viewer);
        });
    }

}
