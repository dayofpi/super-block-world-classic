package com.dayofpi.super_block_world.main.common.world.feature.type;

import com.dayofpi.super_block_world.main.common.block.reactive.CoinBlock;
import com.dayofpi.super_block_world.main.common.block_entity.CoinBlockBE;
import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.SimpleBlockFeature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class BlockSingleFeature extends SimpleBlockFeature {
    public BlockSingleFeature(Codec<SimpleBlockFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleBlockFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        BlockState state = context.getWorld().getBlockState(pos);
        Inventory blockEntity = (Inventory) context.getWorld().getBlockEntity(pos);
        if (world.getBlockState(pos).isOf(BlockRegistry.COIN_BLOCK) && blockEntity instanceof CoinBlockBE) {
            if (world.getRandom().nextInt(30) == 0) {
                world.setBlockState(pos, state.with(CoinBlock.TYPE, 3), 2);
                blockEntity.getStack(0).setCount(15);
            } else if (world.getRandom().nextInt(3) == 0) {
                blockEntity.getStack(0).setCount(5);
            }
        }
        return super.generate(context);
    }
}
