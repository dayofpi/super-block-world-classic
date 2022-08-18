package com.dayofpi.super_block_world.common.block_entities;

import com.dayofpi.super_block_world.registry.ModBlockEntities;
import com.dayofpi.super_block_world.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class ChinchoTorchBE extends BlockEntity {
    private boolean lit;

    public ChinchoTorchBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHINCHO_TORCH, pos, state);
    }

    public static void tick(World world, BlockPos blockPos, BlockState state, ChinchoTorchBE blockEntity) {
        if (state.isOf(ModBlocks.CHINCHO_TORCH) && blockEntity != null) {
            if (state.get(Properties.LIT) != world.isNight() && !world.isClient()) {
                world.setBlockState(blockPos, state.with(Properties.LIT, world.isNight()));
                world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, blockPos);
            } else if (blockEntity.isLit() != state.get(Properties.LIT)) {
                blockEntity.setLit(state.get(Properties.LIT));
            }
            if (blockEntity.isLit()) {
                Box box = (new Box(blockPos)).expand(3.5);
                Random random = world.getRandom();
                ChinchoTorchBE.attackUndead(world, blockPos, box, random);
            }
        }
    }

    private static void attackUndead(World world, BlockPos blockPos, Box box, Random random) {
        List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, box, livingEntity -> livingEntity.getGroup() == EntityGroup.UNDEAD);
        if (list.isEmpty())
            return;
        for (LivingEntity entity : list) {
            if (entity.damage(DamageSource.MAGIC, 2.0F) && world instanceof ServerWorld) {
                ((ServerWorld) world).spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, blockPos.getX() + random.nextFloat(), blockPos.getY() + 1.0D, blockPos.getZ() + random.nextFloat(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                ((ServerWorld) world).spawnParticles(ParticleTypes.SOUL_FIRE_FLAME, entity.getX() + random.nextFloat(), entity.getY() + 1.0D, entity.getZ() + random.nextFloat(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    public boolean isLit() {
        return lit;
    }

    private void setLit(boolean lit) {
        this.lit = lit;
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putBoolean("Lit", this.isLit());
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.setLit(nbt.getBoolean("Lit"));
    }
}
