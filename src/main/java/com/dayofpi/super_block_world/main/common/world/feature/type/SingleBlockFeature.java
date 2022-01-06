package com.dayofpi.super_block_world.main.common.world.feature.type;

import com.dayofpi.super_block_world.main.common.block.item_block.CoinBlock;
import com.dayofpi.super_block_world.main.common.block_entity.CoinBlockBE;
import com.dayofpi.super_block_world.main.registry.main.BlockInit;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.SimpleBlockFeature;
import net.minecraft.world.gen.feature.SimpleBlockFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;

public class SingleBlockFeature extends SimpleBlockFeature {
    public SingleBlockFeature(Codec<SimpleBlockFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<SimpleBlockFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        BlockState state = context.getConfig().toPlace().getBlockState(context.getRandom(), pos);
        Inventory blockEntity = (Inventory) context.getWorld().getBlockEntity(pos);
        boolean water = world.isWater(pos);

        if (water && (state.isOf(BlockInit.QUESTION_BLOCK) || state.isOf(BlockInit.COIN_BLOCK))) {
            world.setBlockState(pos, state.with(Properties.WATERLOGGED, true), 2);
        }

        if (state.isOf(BlockInit.COIN_BLOCK) && blockEntity instanceof CoinBlockBE) {
            if (world.getRandom().nextInt(30) == 0)
                world.setBlockState(pos, state.with(CoinBlock.TYPE, 5).with(Properties.WATERLOGGED, water), 2);

            if (world.getRandom().nextInt(30) == 0) {
                world.setBlockState(pos, state.with(CoinBlock.TYPE, 3).with(Properties.WATERLOGGED, water), 2);
                blockEntity.getStack(0).setCount(15);
            } else if (world.getRandom().nextInt(3) == 0) {
                blockEntity.getStack(0).setCount(5);
            }
        }
        return super.generate(context);
    }
}
