package com.dayofpi.super_block_world.world.feature.types;

import com.dayofpi.super_block_world.registry.block.PlantBlocks;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class StrawberryCoralFeature extends Feature<DefaultFeatureConfig> {
   public StrawberryCoralFeature(Codec<DefaultFeatureConfig> codec) {
      super(codec);
   }

   public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
      int i = 0;
      StructureWorldAccess world = context.getWorld();
      BlockPos blockPos = context.getOrigin();
      Random random = context.getRandom();
      int j = world.getTopY(Heightmap.Type.OCEAN_FLOOR, blockPos.getX(), blockPos.getZ());
      BlockPos blockPos2 = new BlockPos(blockPos.getX(), j, blockPos.getZ());
      if (world.getBlockState(blockPos2).isOf(Blocks.WATER)) {
         BlockState tip = BlockInit.COIN_BLOCK.getDefaultState().with(Properties.WATERLOGGED, true);
         if (random.nextFloat() > 0.2F ) {
            tip = PlantBlocks.STRAWBERRY_CORAL.getDefaultState();
         }
         BlockState body = PlantBlocks.STRAWBERRY_CORAL.getDefaultState();

         int k = 1 + random.nextInt(10);

         for(int l = 0; l <= k; ++l) {
            if (world.getBlockState(blockPos2).isOf(Blocks.WATER) && world.getBlockState(blockPos2.up()).isOf(Blocks.WATER) && body.canPlaceAt(world, blockPos2)) {
               if (l == k) {
                  world.setBlockState(blockPos2, tip, Block.NOTIFY_LISTENERS);
                  ++i;
               } else {
                  world.setBlockState(blockPos2, body, Block.NOTIFY_LISTENERS);
               }
            } else if (l > 0) {
               BlockPos blockPos3 = blockPos2.down();
               if (tip.canPlaceAt(world, blockPos3)) {
                  world.setBlockState(blockPos3, tip, Block.NOTIFY_LISTENERS);
                  ++i;
               }
               break;
            }

            blockPos2 = blockPos2.up();
         }
      }

      return i > 0;
   }
}
