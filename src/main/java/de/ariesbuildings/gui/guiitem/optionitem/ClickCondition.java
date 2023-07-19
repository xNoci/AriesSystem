package de.ariesbuildings.gui.guiitem.optionitem;

import me.noci.quickutilities.inventory.SlotClickEvent;

@FunctionalInterface
public interface ClickCondition {
    boolean shouldExecute(SlotClickEvent event);
}
