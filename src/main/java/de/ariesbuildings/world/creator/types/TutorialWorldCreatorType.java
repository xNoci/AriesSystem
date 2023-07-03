package de.ariesbuildings.world.creator.types;

import com.cryptomorin.xseries.XMaterial;
import de.ariesbuildings.options.WorldOption;
import de.ariesbuildings.world.*;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class TutorialWorldCreatorType implements WorldCreatorType {

    @Override
    public void applyWorldCreator(WorldCreator creator) {
        WorldType.VOID.getCreator().applyWorldCreator(creator);
    }

    @Override
    public void applyWorldGeneration(World world) {
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setTime(6000);
        world.setSpawnLocation(0, 65, 0);

        //TODO CREATE TUTORIAL WORLD
        for (int x = -5; x < 5; x++) {
            for (int z = -5; z < 5; z++) {
                world.getBlockAt(x, 64, z).setType(XMaterial.SMOOTH_STONE.parseMaterial());
            }
        }

    }

    @Override
    public void applyDefaultSettings(AriesWorld world) {
        world.setWorldSpawn(new RawLocation(0.5, 65, 0.5));
        world.setVisibility(WorldVisibility.ARCHIVED);
        world.getOptions().set(WorldOption.WORLD_STATUS, WorldStatus.FINISHED);
    }

}
