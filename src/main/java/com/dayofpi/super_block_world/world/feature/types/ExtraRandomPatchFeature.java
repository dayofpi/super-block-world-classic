package com.dayofpi.super_block_world.world.feature.types;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.blocks.block_entities.QuestionBlockBE;
import com.dayofpi.super_block_world.common.blocks.mechanics.CoinBlock;
import com.dayofpi.super_block_world.common.blocks.block_entities.CoinBlockBE;
import com.dayofpi.super_block_world.world.feature.utility.feature_config.ExtraRandomPatchFeatureConfig;
import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.common.util.key.ModBiomeKeys;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
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
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class ExtraRandomPatchFeature extends Feature<ExtraRandomPatchFeatureConfig> {
    public ExtraRandomPatchFeature(Codec<ExtraRandomPatchFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<ExtraRandomPatchFeatureConfig> context) {
        ExtraRandomPatchFeatureConfig config = context.getConfig();
        Random random = context.getRandom();
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        int i = 0;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int x = config.xSpread();
        int y = config.ySpread();
        int z = config.zSpread();
        if (random.nextBoolean()) {
            for (int l = 0; l < config.tries(); ++l) {
                mutable.set(blockPos, random.nextInt(x) - random.nextInt(x), random.nextInt(y) - random.nextInt(y), random.nextInt(z) - random.nextInt(z));
                if (!config.feature().get().generateUnregistered(world, context.getGenerator(), random, mutable)) continue;
                ++i;
            }
        } else {
            for (int l = 0; l < config.tries(); ++l) {
                mutable.set(blockPos, random.nextInt(z) - random.nextInt(z), random.nextInt(y) - random.nextInt(y), random.nextInt(x) - random.nextInt(x));
                if (!config.feature().get().generateUnregistered(world, context.getGenerator(), random, mutable)) continue;
                ++i;
            }
        }

        BlockState state = world.getBlockState(mutable);
        boolean water = world.isWater(mutable);

        if (water && (state.isOf(BlockInit.QUESTION_BLOCK) || state.isOf(BlockInit.COIN_BLOCK))) {
            world.setBlockState(mutable, state.with(Properties.WATERLOGGED, true), 2);
        }

        LootContext lootContext = new LootContext.Builder(world.toServerWorld()).parameter(LootContextParameters.BLOCK_STATE, state).parameter(LootContextParameters.TOOL, ItemStack.EMPTY).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(mutable)).build(LootContextTypes.BLOCK);
        LootTable lootTable = lootContext.getWorld().getServer().getLootManager().getTable(new Identifier(Main.MOD_ID, "question_blocks/sky"));

        if (state.isOf(BlockInit.QUESTION_BLOCK) && world.getBlockEntity(mutable) instanceof QuestionBlockBE blockEntity) {
            if (mutable.getY() >= 108)
                blockEntity.setStack(0, lootTable.generateLoot(lootContext).get(0));
        }

        if (world.getBlockEntity(mutable) instanceof CoinBlockBE blockEntity && world.getBlockState(mutable).isOf(BlockInit.COIN_BLOCK)) {
            if (random.nextInt(3) == 0 && mutable.getY() > 0) {
                blockEntity.getStack(0).setCount(5);
                if (mutable.getY() < 63) {
                    if (world.getBiomeKey(mutable).isPresent() && world.getBiomeKey(mutable).get() == ModBiomeKeys.SHERBET_LAND)
                        world.setBlockState(mutable, state.with(CoinBlock.TYPE, 4), 2);
                    else
                        world.setBlockState(mutable, state.with(CoinBlock.TYPE, 2), 2);
                }
                else
                    world.setBlockState(mutable, state.with(CoinBlock.TYPE, 1), 2);
            }
        }
        return i > 0;
    }
}
