package de.ariesbuildings.world.creator.types;

import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.WorldCreator;

public class TutorialWorldCreatorType implements WorldCreatorType {

    private WorldCreatorType template = new VoidWorldCreatorType();

    @Override
    public void applyWorldCreator(WorldCreator creator) {
        template.applyWorldCreator(creator);
    }

    @Override
    public void applyWorldGeneration(World world) {
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setTime(6000);
        world.setSpawnLocation(0, 65, 0);

        //TODO CREATE TUTORIAL WORLD

    }

}
