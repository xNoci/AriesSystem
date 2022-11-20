package de.ariesbuildings.world.creator.generator;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class VoidGenerator1_13 extends VoidGenerator {

    public VoidGenerator1_13() {
        super(Biome.PLAINS);
    }

    @Override
    public @NotNull ChunkData generateChunkData(@NotNull World world, @NotNull Random random, int x, int z, @NotNull BiomeGrid biome) {
        biome.setBiome(x, z, super.getBiome());
        return createChunkData(world);
    }
}
