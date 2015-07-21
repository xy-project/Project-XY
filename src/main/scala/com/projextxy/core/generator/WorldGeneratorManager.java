package com.projextxy.core.generator;

import com.projextxy.core.CoreBlocks;
import com.projextxy.core.CoreBlocks$;
import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderEnd;
import net.minecraft.world.gen.ChunkProviderFlat;
import net.minecraft.world.gen.ChunkProviderHell;
import net.minecraft.world.gen.feature.WorldGenMinable;

import java.util.Random;

public class WorldGeneratorManager implements IWorldGenerator {
    public static final WorldGeneratorManager instance = new WorldGeneratorManager();

    @Override
    public void generate(Random r, int chunkX, int chunkZ, World w, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (chunkGenerator instanceof ChunkProviderHell || chunkGenerator instanceof ChunkProviderEnd || chunkGenerator instanceof ChunkProviderFlat)
            return;

        if (w.provider.dimensionId == -1 || w.provider.dimensionId == 1)
            return;

        for (int i = 0; i <= 10; i++) {
            final int x = chunkX * 16 + r.nextInt(16);
            final int y = 20 + r.nextInt(40);
            final int z = chunkZ * 16 + r.nextInt(16);
            new WorldGenMinable(CoreBlocks$.MODULE$.blockXyOre(), w.rand.nextInt(CoreBlocks$.MODULE$.blockXyOre().colors().length()), 5, Blocks.stone).generate(w, r, x, y, z);
        }

    }
}