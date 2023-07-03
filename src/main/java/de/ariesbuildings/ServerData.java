package de.ariesbuildings;

import de.ariesbuildings.config.ServerDataSerializer;
import de.ariesbuildings.world.AriesWorld;
import lombok.Getter;
import lombok.Setter;

public class ServerData {

    private final ServerDataSerializer settingsData = new ServerDataSerializer();

    @Getter @Setter private boolean tutorialWorldLoaded = false;
    @Setter private String spawnWorld = "tutorial";

    public AriesWorld getSpawnWorld() {
        var manager = AriesSystem.getInstance().getWorldManager();
        return manager.getWorld(this.spawnWorld).orElse(manager.getWorlds().get(0));
    }

    public String getSpawnWorldName() {
        return this.spawnWorld;
    }

    public void serialize() {
        settingsData.serialize(this);
    }

    public void deserialize() {
        settingsData.deserialize(this);
    }

}
