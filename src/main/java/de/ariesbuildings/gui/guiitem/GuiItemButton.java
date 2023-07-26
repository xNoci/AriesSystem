package de.ariesbuildings.gui.guiitem;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.AriesSystem;
import de.ariesbuildings.I18n;
import de.ariesbuildings.gui.provider.AriesProvider;
import me.noci.quickutilities.inventory.ClickHandler;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.utils.QuickItemStack;
import me.noci.quickutilities.utils.Require;
import org.bukkit.event.inventory.ClickType;

import java.util.List;

public class GuiItemButton {

    public static GuiItemButton builder(XMaterial material, String displayName) {
        return new GuiItemButton(material, displayName);
    }

    private final XMaterial material;
    private final String displayName;
    private List<String> lore = null;
    private boolean shouldGlow = false;
    private ClickType clickType = null;
    private ClickHandler event = null;

    private GuiItemButton(XMaterial material, String displayName) {
        Require.checkState(material != null, "Material cannot be null.");
        this.material = material;
        this.displayName = I18n.tryTranslate(displayName).orElse(displayName);
    }

    public GuiItemButton lore(String... lore) {
        Require.checkState(this.lore == null, "Cannot set lore twice.");
        this.lore = List.of(lore);
        return this;
    }

    public GuiItemButton glow() {
        this.shouldGlow = true;
        return this;
    }

    public GuiItemButton clickType(ClickType clickType) {
        Require.checkState(this.clickType == null, "Cannot set click type twice.");
        this.clickType = clickType;
        return this;
    }

    public GuiItemButton provider(AriesProvider provider) {
        Require.checkState(this.event == null, "Cannot set click event (provider) twice.");
        this.event = event -> provider.provide(AriesSystem.getInstance().getPlayerManager().getPlayer(event.getPlayer()));
        return this;
    }

    public GuiItemButton event(ClickHandler event) {
        Require.checkState(this.event == null, "Cannot set click event (provider) twice.");
        this.event = event;
        return this;
    }

    public GuiItem build() {
        QuickItemStack item = new QuickItemStack(material.parseMaterial());
        item.addItemFlags();
        item.setDisplayName(Require.nonBlank(displayName).orElse(material.name()));

        if (lore != null) {
            item.setLore(lore);
        }

        if (shouldGlow) {
            item.glow();
        }

        return item.asGuiItem(event -> {
            if (clickType != null && event.getClick() != clickType) return;
            if (this.event != null) this.event.handle(event);
        });
    }


}
