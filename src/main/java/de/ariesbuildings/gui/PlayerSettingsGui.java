package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.permission.RankInfo;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.QuickGUIProvider;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.QuickItemStack;
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
    }


}
