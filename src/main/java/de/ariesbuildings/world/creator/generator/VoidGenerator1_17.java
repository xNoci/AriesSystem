package de.ariesbuildings.world.creator.generator;

import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class VoidGenerator1_17 extends VoidGenerator {

    public VoidGenerator1_17() {
        super(Biome.PLAINS);
    }

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int chunkX, int chunkZ, @NotNull ChunkData chunkData) {
    }

    @Override
    public @Nullable BiomeProvider getDefaultBiomeProvider(@NotNull WorldInfo worldInfo) {
        return new SingleBiomeProvider(super.getBiome());
    }

    public static class SingleBiomeProvider extends BiomeProvider {

        private final Biome biome;

        public SingleBiomeProvider(Biome biome) {
            this.biome = biome;
        }

        @NotNull
        @Override
        public Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
            return this.biome;
        }

        @NotNull
        @Override
        public List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
            return Collections.singletonList(this.biome);
        }
    }

}
