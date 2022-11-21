package de.ariesbuildings.world.creator.types;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.world.creator.generator.VoidGenerator1_13;
import de.ariesbuildings.world.creator.generator.VoidGenerator1_17;
import me.noci.quickutilities.utils.ReflectionUtils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class NormalWorldCreatorType implements WorldCreatorType {

    @Override
    public void applyWorldCreator(WorldCreator creator) {
        creator.type(WorldType.NORMAL);
    }

    @Override
    public void applyWorldGeneration(World world) {
        world.setDifficulty(AriesSystemConfig.WORLD_DIFFICULTY);
        world.setTime(AriesSystemConfig.WORLD_TIME);
    }

}
