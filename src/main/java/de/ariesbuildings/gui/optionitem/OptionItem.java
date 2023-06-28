package de.ariesbuildings.gui.optionitem;

import com.cryptomorin.xseries.XMaterial;
import com.google.common.collect.Maps;
import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.ClickHandler;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.SlotClickEvent;
import me.noci.quickutilities.utils.QuickItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public abstract class OptionItem<OptionType extends Option, OptionValue> extends GuiItem {

    private static final QuickItemStack OPTION_VALUE_NOT_SET = new QuickItemStack(XMaterial.BARRIER.parseMaterial()).glow().addItemFlags();

    protected final HashMap<OptionValue, QuickItemStack> valueMap = Maps.newHashMap();

    protected OptionHolder<OptionType> optionHolder;
    protected OptionType option;
    protected OptionValue currentValue;

    private final InventoryContent content;
    private final int slot;

    protected OptionItem(OptionValue currentValue, OptionHolder<OptionType> optionHolder, OptionType option, InventoryContent content, int slot) {
        this.optionHolder = optionHolder;
        this.option = option;
        this.currentValue = currentValue;
        this.content = content;
        this.slot = slot;

        setAction(this::slotClickEvent);
        update();
    }

    protected abstract void updateCurrentValue();

    protected void setOption(Object value) {
        optionHolder.set(option, value);
    }

    protected void update() {
        content.setItem(slot, this);
        content.applyContent();
    }

    public void mapValue(OptionValue value, QuickItemStack itemStack) {
        valueMap.put(value, itemStack);
        if (currentValue == value) setItem(itemStack);
        update();
    }

    private void slotClickEvent(SlotClickEvent event) {
        //TODO if (clickCondition != null && !clickCondition.shouldExecute()) return;
        updateCurrentValue();
        QuickItemStack item = valueMap.get(this.currentValue);
        if(item == null) {
            item = OPTION_VALUE_NOT_SET;
            item.setDisplayName("[PLACEHOLDER] Item for value '%s' not found.".formatted(this.currentValue)); //TODO Add language file
        }
        setItem(item);
        setOption(this.currentValue);
        update();
    }

}
