package com.dayofpi.super_block_world.world.feature.types;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.blocks.block_entities.CoinBlockBE;
import com.dayofpi.super_block_world.common.blocks.block_entities.QuestionBlockBE;
import com.dayofpi.super_block_world.common.blocks.mechanics.CoinBlock;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
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

        LootContext lootContext = new LootContext.Builder(world.toServerWorld()).parameter(LootContextParameters.BLOCK_STATE, state).parameter(LootContextParameters.TOOL, ItemStack.EMPTY).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(pos)).build(LootContextTypes.BLOCK);
        LootTable lootTable = lootContext.getWorld().getServer().getLootManager().getTable(new Identifier(Main.MOD_ID, "question_blocks/sky"));

        if (state.isOf(BlockInit.QUESTION_BLOCK) && blockEntity instanceof QuestionBlockBE) {
            if (pos.getY() >= 110)
                blockEntity.setStack(0, lootTable.generateLoot(lootContext).get(0));
        }

        if (state.isOf(BlockInit.COIN_BLOCK) && blockEntity instanceof CoinBlockBE) {
            if (world.getRandom().nextInt(30) == 0)
                world.setBlockState(pos, state.with(CoinBlock.TYPE, 6).with(Properties.WATERLOGGED, water), 2);
            else if (world.getRandom().nextInt(30) == 0) {
                world.setBlockState(pos, state.with(CoinBlock.TYPE, 3).with(Properties.WATERLOGGED, water), 2);
                blockEntity.getStack(0).setCount(15);
            } else if (world.getRandom().nextInt(3) == 0) {
                blockEntity.getStack(0).setCount(5);
            }
        }
        return super.generate(context);
    }
}
