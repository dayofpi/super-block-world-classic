package com.dayofpi.sbw_main.block.types;

import net.minecraft.block.BlockState;
import net.minecraft.block.NoteBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class JumpBlock extends NoteBlock {
    public JumpBlock(Settings $$0) {
        super($$0);
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

    private void playNote(World world, BlockPos pos) {
        if (world.getBlockState(pos.up()).isAir()) {
            world.addSyncedBlockEvent(pos, this, 0, 0);
        }

    }

    protected void bounce(Entity entity) {
        Vec3d vec3d = entity.getVelocity();
        World world = entity.world;
        BlockPos blockPos = entity.getLandingPos();
        this.playNote(world, blockPos);
        entity.setVelocity(vec3d.x, 0.9, vec3d.z);

    }
}
