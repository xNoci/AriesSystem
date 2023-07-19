package de.ariesbuildings.gui.provider;

import de.ariesbuildings.AriesPlayer;

@FunctionalInterface
public interface AriesProvider {
    void provide(AriesPlayer player);
}
