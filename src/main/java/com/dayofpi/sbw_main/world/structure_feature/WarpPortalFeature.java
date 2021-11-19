package com.dayofpi.sbw_main.world.structure_feature;

import com.mojang.serialization.Codec;
import net.minecraft.class_6834;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.feature.MarginedStructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.Optional;
import java.util.function.Predicate;

public class WarpPortalFeature extends MarginedStructureFeature<StructurePoolFeatureConfig> {
    public WarpPortalFeature(Codec<StructurePoolFeatureConfig> codec, int structureStartY, boolean modifyBoundingBox, boolean surface, Predicate<class_6834.class_6835<StructurePoolFeatureConfig>> predicate) {
        super(codec, (context) -> {
            if (!predicate.test(context)) {
                return Optional.empty();
             } else {
                BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), structureStartY, context.chunkPos().getStartZ());
                StructurePools.initDefaultPools();
                return StructurePoolBasedGenerator.generate(context, PoolStructurePiece::new, blockPos, modifyBoundingBox, surface);
            }
        });
    }
}
