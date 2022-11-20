package de.ariesbuildings.world.creator.types;

import de.ariesbuildings.config.AriesSystemConfig;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class FlatWorldCreatorType implements WorldCreatorType {

    @Override
    public void applyWorldCreator(WorldCreator creator) {
        creator.type(WorldType.FLAT);
    }

    @Override
    public void applyWorldGeneration(World world) {
        world.setDifficulty(AriesSystemConfig.WORLD_DIFFICULTY);
        world.setTime(AriesSystemConfig.WORLD_TIME);
    }

}
