package de.ariesbuildings.gui;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.guiitem.InventoryConstants;
import de.ariesbuildings.gui.guiitem.optionitem.OptionItemBuilder;
import de.ariesbuildings.gui.provider.AriesGuiProvider;
import de.ariesbuildings.gui.provider.AriesProvider;
import de.ariesbuildings.options.OptionNotify;
import de.ariesbuildings.options.PlayerOption;
import de.ariesbuildings.permission.RankInfo;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.Slot;
import me.noci.quickutilities.utils.QuickItemStack;
import org.bukkit.GameMode;

public class PlayerSettingsGui extends AriesGuiProvider {

    private final AriesProvider previousGui;

    public PlayerSettingsGui() {
        this(null);
    }

    public PlayerSettingsGui(AriesProvider previousGui) {
        super(I18n.translate("gui.player_settings.title"), InventoryConstants.FULL_INV_SIZE);
        this.previousGui = previousGui;
    }

    @Override
    protected void init(AriesPlayer player, InventoryContent content) {
        content.fill(InventoryConstants.BACKGROUND_BLACK);
        if (previousGui != null) content.setItem(Slot.getSlot(6, 9), InventoryConstants.openPreviousGui(previousGui));
        RankInfo rankInfo = player.getRankInfo();

        QuickItemStack playerInfo = new QuickItemStack(XMaterial.PLAYER_HEAD.parseMaterial())
                .setDisplayName(I18n.translate("gui.player_settings.item.player_info.displayname", player.getName()))
                .addItemFlags()
                .setSkullOwner(player.getName())
                .setLore("", I18n.translate("gui.player_settings.item.player_info.lore.rank", rankInfo.getColor() + rankInfo.getName()));
        content.setItem(Slot.getSlot(2, 5), playerInfo.asGuiItem());

        OptionItemBuilder.of(PlayerOption.DEFAULT_GAMEMODE, GameMode.class)
                .inventoryContent(content)
                .slot(4, 2)
                .optionHolder(player.getOptions())
                .mapValue(GameMode.SURVIVAL, new QuickItemStack(XMaterial.WOODEN_PICKAXE.parseMaterial()).setLore("", I18n.translate("gui.player_settings.item.gamemode.lore.survival")).addItemFlags())
                .mapValue(GameMode.CREATIVE, new QuickItemStack(XMaterial.DIAMOND_PICKAXE.parseMaterial()).setLore("", I18n.translate("gui.player_settings.item.gamemode.lore.creative")).addItemFlags())
                .mapValue(GameMode.ADVENTURE, new QuickItemStack(XMaterial.GOLDEN_PICKAXE.parseMaterial()).setLore("", I18n.translate("gui.player_settings.item.gamemode.lore.adventure")).addItemFlags())
                .mapValue(GameMode.SPECTATOR, new QuickItemStack(XMaterial.STONE_PICKAXE.parseMaterial()).setLore("", I18n.translate("gui.player_settings.item.gamemode.lore.spectator")).addItemFlags())
                .build();

        OptionItemBuilder.of(PlayerOption.GLOW, boolean.class)
                .inventoryContent(content)
                .slot(4, 4)
                .optionHolder(player.getOptions())
                .mapValue(true, new QuickItemStack(XMaterial.TOTEM_OF_UNDYING.parseMaterial()).glow().addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.TOTEM_OF_UNDYING.parseMaterial()).addItemFlags())
                .build();

        OptionItemBuilder.of(PlayerOption.VOID_DAMAGE_TELEPORT, boolean.class)
                .inventoryContent(content)
                .slot(4, 6)
                .optionHolder(player.getOptions())
                .mapValue(true, new QuickItemStack(XMaterial.END_PORTAL_FRAME.parseMaterial()).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.BEDROCK.parseMaterial()).addItemFlags())
                .build();

        OptionItemBuilder.of(PlayerOption.VANISH, boolean.class)
                .inventoryContent(content)
                .slot(4, 8)
                .optionHolder(player.getOptions())
                .mapValue(true, new QuickItemStack(InventoryConstants.INVISIBLE_POTION).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.GLASS_BOTTLE.parseMaterial()).addItemFlags())
                .build();

        OptionItemBuilder.of(PlayerOption.FLY_SPEED, int.class)
                .inventoryContent(content)
                .slot(5, 2)
                .optionHolder(player.getOptions())
                .lowerBound(1)
                .upperBound(10)
                .increment(1)
                .integerItem(new QuickItemStack(XMaterial.FEATHER.parseMaterial()))
                .build();

        OptionItemBuilder.of(PlayerOption.NOTIFY_OPTION_CHANGE, OptionNotify.class)
                .inventoryContent(content)
                .slot(5, 4)
                .optionHolder(player.getOptions())
                .mapValue(OptionNotify.ALWAYS, new QuickItemStack(XMaterial.NETHER_STAR.parseMaterial()).setLore("", I18n.translate("gui.player_settings.item.option_notify.lore.always")))
                .mapValue(OptionNotify.NEVER, new QuickItemStack(XMaterial.IRON_BARS.parseMaterial()).setLore("", I18n.translate("gui.player_settings.item.option_notify.lore.never")))
                .mapValue(OptionNotify.ONLY_PLAYER, new QuickItemStack(XMaterial.ARMOR_STAND.parseMaterial()).setLore("", I18n.translate("gui.player_settings.item.option_notify.lore.only_player")))
                .mapValue(OptionNotify.ONLY_WORLD, new QuickItemStack(XMaterial.GRASS_BLOCK.parseMaterial()).setLore("", I18n.translate("gui.player_settings.item.option_notify.lore.only_world")))
                .build();

        OptionItemBuilder.of(PlayerOption.PLAY_PING_SOUND, boolean.class)
                .inventoryContent(content)
                .slot(5, 6)
                .optionHolder(player.getOptions())
                .mapValue(true, new QuickItemStack(XMaterial.BELL.or(XMaterial.JUKEBOX).parseMaterial()).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.GRAY_CARPET.parseMaterial()).addItemFlags())
                .build();

        OptionItemBuilder.of(PlayerOption.REMEMBER_LOCATION, boolean.class)
                .inventoryContent(content)
                .slot(5, 8)
                .optionHolder(player.getOptions())
                .mapValue(true, new QuickItemStack(XMaterial.MAP.parseMaterial()).addItemFlags())
                .mapValue(false, new QuickItemStack(XMaterial.ITEM_FRAME.parseMaterial()).addItemFlags())
                .build();

    }


}
