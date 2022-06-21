package com.dayofpi.super_block_world.common.blocks;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.hostile.FakeBlockEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;

@SuppressWarnings("deprecation")
public class FakeBlock extends Block {
    public FakeBlock(Settings settings) {
        super(settings);
    }

    private void spawn(ServerWorld world, BlockPos pos) {
        FakeBlockEntity fakeBlockEntity = ModEntities.FAKE_BLOCK.create(world);
        if (fakeBlockEntity != null) {
            fakeBlockEntity.refreshPositionAndAngles((double) pos.getX() + 0.5D, pos.getY(), (double) pos.getZ() + 0.5D, 0.0F, 0.0F);
            world.spawnEntity(fakeBlockEntity);
            fakeBlockEntity.playSound(Sounds.ENTITY_GENERIC_TRANSFORM, 1.0F, 1.0F);
            fakeBlockEntity.playSpawnEffects();
        }
    }

    @Override
    public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.onStacksDropped(state, world, pos, stack, dropExperience);
        if (world.getGameRules().getBoolean(GameRules.DO_TILE_DROPS) && EnchantmentHelper.getLevel(Enchantments.SILK_TOUCH, stack) == 0) {
            this.spawn(world, pos);
        }
    }

    @Override
    public void onDestroyedByExplosion(World world, BlockPos pos, Explosion explosion) {
        if (world instanceof ServerWorld) {
            this.spawn((ServerWorld) world, pos);
        }
    }
}
