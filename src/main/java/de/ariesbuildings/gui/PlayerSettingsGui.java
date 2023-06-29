package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.optionitem.OptionItemBuilder;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.RankInfo;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.QuickGUIProvider;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.QuickItemStack;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class PlayerSettingsGui extends QuickGUIProvider {

    private final QuickGUIProvider previousGui;

    public PlayerSettingsGui() {
        this(null);
    }

    public PlayerSettingsGui(QuickGUIProvider previousGui) {
        super(I18n.translate("gui.player_settings.title"), InventoryConstants.FULL_INV_SIZE);
        this.previousGui = previousGui;
    }

    @Override
    public void init(Player p, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        if (previousGui != null) content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));
        AriesPlayer player = AriesSystem.getInstance().getPlayerManager().getPlayer(p);
        RankInfo rankInfo = player.getRankInfo();

        QuickItemStack playerInfo = new QuickItemStack(XMaterial.PLAYER_HEAD.parseMaterial())
                .setDisplayName(I18n.translate("gui.player_settings.item.player_info.displayname", player.getName()))
                .addItemFlags()
                .setSkullOwner(player.getName())
                .setLore(
                        "",
                        I18n.translate("gui.player_settings.item.player_info.lore.rank", rankInfo.getColor() + rankInfo.getName())
                );
        content.setItem(Slot.getSlot(2, 5), playerInfo.asGuiItem());

        OptionItemBuilder.of(PlayerOption.DEFAULT_GAMEMODE, GameMode.class)
                .inventoryContent(content)
                .slot(3, 2)
                .optionHolder(player.getOptions())
                //TODO ITEMS
                .mapValue(GameMode.SURVIVAL, new QuickItemStack(XMaterial.ORANGE_DYE.parseMaterial(), "SURVIVAL"))
                .mapValue(GameMode.CREATIVE, new QuickItemStack(XMaterial.RED_DYE.parseMaterial(), "CREATIVE"))
                .mapValue(GameMode.ADVENTURE, new QuickItemStack(XMaterial.YELLOW_DYE.parseMaterial(), "ADVENTURE"))
                .mapValue(GameMode.SPECTATOR, new QuickItemStack(XMaterial.GREEN_DYE.parseMaterial(), "SPECTATOR"))
                .build();

        OptionItemBuilder.of(PlayerOption.GLOW, Boolean.class)
                .inventoryContent(content)
                .slot(3, 3)
                .optionHolder(player.getOptions())
                //TODO ITEMS
                .mapValue(true, new QuickItemStack(XMaterial.GREEN_DYE.parseMaterial(), "TRUE"))
                .mapValue(false, new QuickItemStack(XMaterial.RED_DYE.parseMaterial(), "FALSE"))
                .build();

        OptionItemBuilder.of(PlayerOption.VOID_DAMAGE_TELEPORT, Boolean.class)
                .inventoryContent(content)
                .slot(3, 4)
                .optionHolder(player.getOptions())
                //TODO ITEMS
                .mapValue(true, new QuickItemStack(XMaterial.GREEN_DYE.parseMaterial(), "TRUE"))
                .mapValue(false, new QuickItemStack(XMaterial.RED_DYE.parseMaterial(), "FALSE"))
                .build();

        OptionItemBuilder.of(PlayerOption.VANISH, Boolean.class)
                .inventoryContent(content)
                .slot(3, 5)
                .optionHolder(player.getOptions())
                //TODO ITEMS
                .mapValue(true, new QuickItemStack(XMaterial.GREEN_DYE.parseMaterial(), "TRUE"))
                .mapValue(false, new QuickItemStack(XMaterial.RED_DYE.parseMaterial(), "FALSE"))
                .build();
    }


}
