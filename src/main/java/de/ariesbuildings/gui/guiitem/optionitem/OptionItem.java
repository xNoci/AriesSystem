package de.ariesbuildings.gui.guiitem.optionitem;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.ariesbuildings.I18n;
import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import lombok.AccessLevel;
import lombok.Setter;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.SlotClickEvent;
import me.noci.quickutilities.utils.QuickItemStack;
import me.noci.quickutilities.utils.Require;
import org.bukkit.ChatColor;
import org.bukkit.event.inventory.ClickType;

import java.util.HashMap;
import java.util.List;

public abstract class OptionItem<OptionType extends Option, OptionValue> extends GuiItem {

    private static final QuickItemStack OPTION_VALUE_NOT_SET = new QuickItemStack(XMaterial.BARRIER.parseMaterial()).glow().addItemFlags();

    protected final HashMap<OptionValue, QuickItemStack> valueMap = Maps.newHashMap();

    protected OptionHolder<OptionType> optionHolder;
    protected OptionType option;
    protected OptionValue currentValue;

    @Setter(AccessLevel.PROTECTED) private ClickCondition clickCondition = null;
    private final InventoryContent content;
    private final int slot;

    protected OptionItem(OptionValue currentValue, OptionHolder<OptionType> optionHolder, OptionType option, InventoryContent content, int slot) {
        this.optionHolder = optionHolder;
        this.option = option;
        this.currentValue = currentValue;
        this.content = content;
        this.slot = slot;
        setAction(this::slotClickEvent);
    }

    protected void setOption(Object value) {
        optionHolder.set(option, value);
    }

    public void mapValue(OptionValue value, QuickItemStack itemStack) {
        valueMap.put(value, itemStack);
        if (currentValue == value) setItem(itemStack);
        update();
    }

    protected void updateDisplayedItem() {
        QuickItemStack item = getItem();
        updateItemLore(item);
        setItem(item);
        update();
    }

    protected QuickItemStack getItem() {
        QuickItemStack item = valueMap.get(this.currentValue);
        if (item != null) {
            item = (QuickItemStack) item.clone();
            item.setDisplayName(I18n.translate("gui.option_item.displayname", option.getName()));
            return item;
        }

        item = (QuickItemStack) OPTION_VALUE_NOT_SET.clone();
        item.setDisplayName(I18n.translate("gui.option_item.value_mapping_not_found.displayname", this.currentValue));
        return item;
    }

    protected abstract boolean updateCurrentValue(ClickType clickType);

    private void update() {
        content.setItem(slot, this);
        content.applyContent();
    }

    private void updateItemLore(QuickItemStack item) {
        List<String> lore = item.getLore() != null ? item.getLore() : Lists.newArrayList();
        Require.nonBlank(option.getDescription()).ifPresent(description -> {
            String[] descriptions = I18n.translate("gui.option_item.description", description).split("\n");
            int index = 0;
            String lastLine = null;
            for (String desc : descriptions) {
                String lastColor = lastLine == null ? "" : ChatColor.getLastColors(lastLine);
                lastLine = desc;
                lore.add(index, lastColor + desc);
                index++;
            }
        });

        item.setLore(lore);
    }

    private void slotClickEvent(SlotClickEvent event) {
        if (clickCondition != null && !clickCondition.shouldExecute(event)) return;
        boolean valueChanged = updateCurrentValue(event.getClick());
        if (!valueChanged) return;
        updateDisplayedItem();
        setOption(this.currentValue);
    }

}
