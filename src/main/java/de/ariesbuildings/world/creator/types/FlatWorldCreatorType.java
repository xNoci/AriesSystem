package de.ariesbuildings.world.creator.types;

import com.cryptomorin.xseries.ReflectionUtils;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.world.AriesWorld;
import de.ariesbuildings.world.RawLocation;
import de.ariesbuildings.world.creator.generator.VoidGenerator1_13;
import de.ariesbuildings.world.creator.generator.VoidGenerator1_17;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

import java.util.concurrent.Callable;

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

    @Override
    public void applyDefaultSettings(AriesWorld world) {
        if(ReflectionUtils.supports(17)) {
            world.setWorldSpawn(new RawLocation(0.5, -60, 0.5));
        }
    }
}
