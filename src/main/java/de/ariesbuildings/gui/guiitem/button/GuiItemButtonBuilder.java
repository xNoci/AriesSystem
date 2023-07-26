package de.ariesbuildings.gui.guiitem.button;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.AriesPlayer;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.provider.AriesProvider;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.utils.QuickItemStack;
import me.noci.quickutilities.utils.Require;
import org.bukkit.event.inventory.ClickType;

import java.util.List;
import java.util.function.BiConsumer;

public class GuiItemButtonBuilder {

    private final XMaterial material;
    private final String displayName;
    private List<String> lore = null;
    private boolean shouldGlow = false;
    private ClickType clickType = null;
    private BiConsumer<AriesPlayer, GuiItemButton> clickHandler = null;

    protected GuiItemButtonBuilder(XMaterial material, String displayName) {
        Require.checkState(material != null, "Material cannot be null.");
        this.material = material;
        this.displayName = I18n.tryTranslate(displayName).orElse(displayName);
    }

    public GuiItemButtonBuilder lore(String... lore) {
        Require.checkState(this.lore == null, "Cannot set lore twice.");
        this.lore = List.of(lore);
        return this;
    }

    public GuiItemButtonBuilder glow() {
        this.shouldGlow = true;
        return this;
    }

    public GuiItemButtonBuilder clickType(ClickType clickType) {
        Require.checkState(this.clickType == null, "Cannot set click type twice.");
        this.clickType = clickType;
        return this;
    }

    public GuiItemButtonBuilder provider(AriesProvider provider) {
        Require.checkState(this.clickHandler == null, "Cannot set click handler (provider) twice.");
        this.clickHandler = (player, item) -> provider.provide(player);
        return this;
    }

    public GuiItemButtonBuilder click(BiConsumer<AriesPlayer, GuiItemButton> click) {
        Require.checkState(this.clickHandler == null, "Cannot set click handler (provider) twice.");
        this.clickHandler = click;
        return this;
    }

    public GuiItemButton build(InventoryContent content, int slot) {
        QuickItemStack item = new QuickItemStack(material.parseMaterial());
        item.addItemFlags();
        item.setDisplayName(Require.nonBlank(displayName).orElse(material.name()));

        if (lore != null) {
            item.setLore(lore);
        }

        if (shouldGlow) {
            item.glow();
        }

        GuiItemButton button = new GuiItemButton(item, content, slot);
        button.setAction(event -> {
            if (clickType != null && event.getClick() != clickType) return;
            if (this.clickHandler != null) {
                AriesPlayer player = AriesSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer());
                this.clickHandler.accept(player, button);
            }
        });
        button.update();
        return button;
    }

}
