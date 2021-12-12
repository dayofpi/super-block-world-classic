package com.dayofpi.sbw_main.common.block.type;

import net.minecraft.block.BlockState;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

import java.util.Random;

@SuppressWarnings("deprecation")
public class FireTulipBlock extends FlowerBlock {
    public FireTulipBlock(StatusEffect suspiciousStewEffect, int effectDuration, Settings settings) {
        super(suspiciousStewEffect, effectDuration, settings);
    }

    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isSideSolidFullSquare(world, pos, Direction.UP);
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity && entity.getType() != EntityType.BEE) {
            if (!world.isClient && (entity.lastRenderX != entity.getX() || entity.lastRenderZ != entity.getZ())) {
                double d = Math.abs(entity.getX() - entity.lastRenderX);
                double e = Math.abs(entity.getZ() - entity.lastRenderZ);
                if (d >= 0.003000000026077032D || e >= 0.003000000026077032D) {
                    entity.damage(DamageSource.HOT_FLOOR, 1.0F);
                }
            }
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(3) == 0) {
            double d = (double) pos.getX() + random.nextFloat();
            double e = (double) pos.getY() + (random.nextFloat() * 0.5);
            double f = (double) pos.getZ() + random.nextFloat();
            world.addParticle(ParticleTypes.FLAME, d, e, f, 0.0D, 0.0D, 0.0D);
        }}
}