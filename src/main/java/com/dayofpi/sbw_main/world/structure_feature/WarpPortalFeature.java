package com.dayofpi.sbw_main.world.structure_feature;

import com.mojang.serialization.Codec;
import net.minecraft.class_6622;
import net.minecraft.class_6626;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;

public class WarpPortalFeature extends StructureFeature<DefaultFeatureConfig> {
    public WarpPortalFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec, WarpPortalFeature::method_38675);
    }

    private static void method_38675(class_6626 arg, DefaultFeatureConfig defaultFeatureConfig, class_6622.class_6623 arg2) {
        Heightmap.Type type = Heightmap.Type.WORLD_SURFACE_WG;
        if (arg2.method_38707(type)) {
            int x = arg2.chunkPos().getStartX();
            int z = arg2.chunkPos().getStartZ();
            int y = arg2.chunkGenerator().getHeight(x, z, Heightmap.Type.WORLD_SURFACE_WG, arg2.heightAccessor());
            BlockRotation blockRotation = BlockRotation.random(arg2.random());
            BlockPos blockPos = new BlockPos(x, y, z);
            WarpPortalGenerator.addPiece(arg2.structureManager(), blockPos, blockRotation, arg);
        }
    }
}
