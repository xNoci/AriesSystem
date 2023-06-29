package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XItemStack;
import com.cryptomorin.xseries.XMaterial;
import com.cryptomorin.xseries.XPotion;
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

        String gamemodeOptionDisplayname = I18n.translate("gui.player_settings.item.gamemode.displayname");
        OptionItemBuilder.of(PlayerOption.DEFAULT_GAMEMODE, GameMode.class)
                .inventoryContent(content)
                .slot(4, 2)
                .optionHolder(player.getOptions())
                .mapValue(GameMode.SURVIVAL, new QuickItemStack(XMaterial.WOODEN_PICKAXE.parseMaterial(), gamemodeOptionDisplayname).setLore("", I18n.translate("gui.player_settings.item.gamemode.lore.survival")).addItemFlags())
                .mapValue(GameMode.CREATIVE, new QuickItemStack(XMaterial.DIAMOND_PICKAXE.parseMaterial(), gamemodeOptionDisplayname).setLore("", I18n.translate("gui.player_settings.item.gamemode.lore.creative")).addItemFlags())
                .mapValue(GameMode.ADVENTURE, new QuickItemStack(XMaterial.GOLDEN_PICKAXE.parseMaterial(), gamemodeOptionDisplayname).setLore("", I18n.translate("gui.player_settings.item.gamemode.lore.adventure")).addItemFlags())
                .mapValue(GameMode.SPECTATOR, new QuickItemStack(XMaterial.STONE_PICKAXE.parseMaterial(), gamemodeOptionDisplayname).setLore("", I18n.translate("gui.player_settings.item.gamemode.lore.spectator")).addItemFlags())
                .build();

        String glowOptionDisplayname = I18n.translate("gui.player_settings.item.glow.displayname");
        OptionItemBuilder.of(PlayerOption.GLOW, Boolean.class)
                .inventoryContent(content)
                .slot(4, 4)
                .optionHolder(player.getOptions())
                .mapValue(true, new QuickItemStack(XMaterial.TOTEM_OF_UNDYING.parseMaterial(), glowOptionDisplayname).glow().setLore("", I18n.translate("gui.player_settings.item.option.lore_true")).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.TOTEM_OF_UNDYING.parseMaterial(), glowOptionDisplayname).setLore("", I18n.translate("gui.player_settings.item.option.lore_false")).addItemFlags())
                .build();

        String voidTeleportOptionDisplayname = I18n.translate("gui.player_settings.item.void_tp.displayname");
        OptionItemBuilder.of(PlayerOption.VOID_DAMAGE_TELEPORT, Boolean.class)
                .inventoryContent(content)
                .slot(4, 6)
                .optionHolder(player.getOptions())
                .mapValue(true, new QuickItemStack(XMaterial.END_PORTAL_FRAME.parseMaterial(), voidTeleportOptionDisplayname).setLore("", I18n.translate("gui.player_settings.item.option.lore_true")).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.BEDROCK.parseMaterial(), voidTeleportOptionDisplayname).setLore("", I18n.translate("gui.player_settings.item.option.lore_false")).addItemFlags())
                .build();


        String vanishOptionDisplayname = I18n.translate("gui.player_settings.item.vanish.displayname");
        OptionItemBuilder.of(PlayerOption.VANISH, Boolean.class)
                .inventoryContent(content)
                .slot(4, 8)
                .optionHolder(player.getOptions())
                .mapValue(true, new QuickItemStack(InventoryConstants.INVISIBLE_POTION).setDisplayName(vanishOptionDisplayname).setLore("", I18n.translate("gui.player_settings.item.option.lore_true")).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.GLASS_BOTTLE.parseMaterial(), vanishOptionDisplayname).setLore("", I18n.translate("gui.player_settings.item.option.lore_false")).addItemFlags())
                .build();
    }


}
