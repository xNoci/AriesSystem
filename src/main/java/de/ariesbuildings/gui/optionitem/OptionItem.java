package de.ariesbuildings.gui.optionitem;

import de.ariesbuildings.options.Option;
import de.ariesbuildings.options.OptionHolder;
import me.noci.quickutilities.inventory.GuiItem;
import me.noci.quickutilities.inventory.InventoryContent;
import me.noci.quickutilities.inventory.Slot;

public abstract class OptionItem<O extends Option> extends GuiItem {

    protected OptionHolder<O> optionHolder;
    protected O option;

    private final InventoryContent content;
    private final int slot;
    private ClickCondition clickCondition;

    public OptionItem(InventoryContent content, OptionHolder<O> optionHolder, O option, int row, int column) {
        this.slot = Slot.getSlot(row, column);
        this.content = content;
        this.optionHolder = optionHolder;
        this.option = option;

        setAction(event -> {
            if (clickCondition != null && !clickCondition.shouldExecute()) return;

            click();
            update();
        });
        update();
    }

    protected abstract void click();

    protected void setOption(Object value) {
        optionHolder.set(option, value);
    }

    protected void update() {
        content.setItem(slot, this);
        content.applyContent();
    }

}
