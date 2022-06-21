package com.dayofpi.super_block_world.world.structure.types;

import com.mojang.serialization.Codec;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.MarginedStructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.Optional;
import java.util.function.Predicate;

public class WarpPortalFeature extends MarginedStructureFeature<StructurePoolFeatureConfig> {
    public WarpPortalFeature(Codec<StructurePoolFeatureConfig> codec, boolean modifyBoundingBox, boolean surface, Predicate<StructureGeneratorFactory.Context<StructurePoolFeatureConfig>> predicate) {
        super(codec, (context) -> {
            if (!predicate.test(context)) {
                return Optional.empty();
             } else {
                int x = context.chunkPos().getStartX();
                int z = context.chunkPos().getStartZ();
                int y = context.chunkGenerator().getHeight(x, z, Heightmap.Type.OCEAN_FLOOR_WG, context.world());

                BlockPos blockPos = new BlockPos(x, y, z);
                return StructurePoolBasedGenerator.generate(context, PoolStructurePiece::new, blockPos, modifyBoundingBox, surface);
            }
        });
    }
}
