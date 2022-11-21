package de.ariesbuildings.world.creator.generator;

import lombok.Getter;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;

public abstract class VoidGenerator extends ChunkGenerator {

    @Getter private final Biome biome;

    public VoidGenerator(Biome biome) {
        this.biome = biome;
    }

}
