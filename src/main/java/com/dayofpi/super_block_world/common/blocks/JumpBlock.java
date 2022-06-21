package com.dayofpi.super_block_world.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

public class JumpBlock extends NoteBlock {
    public JumpBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance) {
        if (entity.bypassesLandingEffects()) {
            super.onLandedUpon(world, state, pos, entity, fallDistance);
        } else {
            entity.handleFallDamage(fallDistance, 0.0F, DamageSource.FALL);
        }

    }

    @Override
    public void onEntityLand(BlockView world, Entity entity) {
        this.bounce(entity);
    }

    private void playNote(@Nullable Entity entity, World world, BlockPos pos) {
        if (!world.getBlockState(pos.up()).isAir()) {
            return;
        }
        world.addSyncedBlockEvent(pos, this, 0, 0);
        world.emitGameEvent(entity, GameEvent.NOTE_BLOCK_PLAY, pos);
    }

    private void bounce(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        World world = entity.world;
        BlockPos blockPos = entity.getSteppingPos();
        if (vec3d.y < 0.2D) {
            double d = entity instanceof LivingEntity ? 1.0D : 0.8D;
            entity.setVelocity(vec3d.x, -vec3d.y * d, vec3d.z);
            if (entity.fallDistance > 0)
                this.playNote(entity, world, blockPos);
        }
    }
}
