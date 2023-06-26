package de.ariesbuildings.world.creator.types;

import com.cryptomorin.xseries.ReflectionUtils;
import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.config.AriesSystemConfig;
import de.ariesbuildings.world.creator.generator.VoidGenerator1_13;
import de.ariesbuildings.world.creator.generator.VoidGenerator1_17;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;

public class VoidWorldCreatorType implements WorldCreatorType {

    @Override
    public void applyWorldCreator(WorldCreator creator) {
        creator.generateStructures(false);
        creator.type(WorldType.FLAT);

        ReflectionUtils
                .v(17, () -> creator.generator(new VoidGenerator1_17()))
                .v(13, () -> creator.generator(new VoidGenerator1_13()))
                .orElse(() -> creator.generatorSettings("2;0;1"));
    }

    @Override
    public void applyWorldGeneration(World world) {
        world.setDifficulty(AriesSystemConfig.WORLD_DIFFICULTY);
        world.setTime(AriesSystemConfig.WORLD_TIME);
        world.setSpawnLocation(0, 65, 0);

        Material defaultMaterial = XMaterial.matchXMaterial(AriesSystemConfig.WORLD_VOID_BLOCK_TYPE)
                .orElse(XMaterial.SMOOTH_STONE)
                .parseMaterial();

        if (AriesSystemConfig.WORLD_VOID_PLACE_SPAWN_BLOCK) {
            world.getBlockAt(0, 64, 0).setType(defaultMaterial);
        }
    }

}
