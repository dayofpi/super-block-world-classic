package com.dayofpi.super_block_world.mixin.main.warp_pipe;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.blocks.WarpPipeBlock;
import com.dayofpi.super_block_world.registry.more.ParticleInit;
import com.dayofpi.super_block_world.registry.more.StatusEffectInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public abstract class PipeTeleporting extends LivingEntity {
    private int pipeCooldown;

    public PipeTeleporting(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    public void setSneaking(boolean sneaking) {
        if (sneaking) {
            this.warpToPipe();
        }
        super.setSneaking(sneaking);
    }

    private void warpToPipe() {
        if (this.getPipeCooldown() == 0) {
            BlockPos entityPos = this.getBlockPos();
            BlockPos floor = this.getBlockPos().down();
            boolean onPipe = world.getBlockState(entityPos).isAir() && world.getBlockState(floor).isIn(TagInit.WARP_PIPES) && world.getBlockState(floor).get(Properties.FACING) == Direction.UP;
            boolean inPipe = world.getBlockState(entityPos).isIn(TagInit.WARP_PIPES) && world.getBlockState(entityPos).get(Properties.FACING) == Direction.UP;
            BlockPos blockPos = WarpPipeBlock.warpPipeTree.getNearestBlock(floor, world);

            if (onPipe && blockPos != null) {
                this.warpToPipe(blockPos);
            } else if (inPipe) {
                blockPos = WarpPipeBlock.warpPipeTree.getNearestBlock(entityPos, world);
                if (blockPos != null)
                    this.warpToPipe(blockPos);
            }
        }
    }

    private void warpToPipe(BlockPos blockPos) {
        this.requestTeleport(blockPos.getX() + 0.5, blockPos.getY() + 1.0F, blockPos.getZ() + 0.5);
        this.setPipeCooldown(20);
        world.sendEntityStatus(this, (byte) 46);
        world.playSound(null, blockPos, SoundInit.BLOCK_WARP_PIPE_TELEPORT, SoundCategory.PLAYERS, 0.5F, this.getSoundPitch());

    }

    public int getPipeCooldown() {
        return pipeCooldown;
    }

    public void setPipeCooldown(int cooldown) {
        this.pipeCooldown = cooldown;
    }

    @Override
    public void baseTick() {
        super.baseTick();
        if (this.isAlive()) {
            if (this.hasStatusEffect(StatusEffectInit.STAR_POWER)) {
                for (int i = 0; i < 1; ++i) {
                    world.addParticle(ParticleInit.STAR_BIT, true, this.getX() + (random.nextBoolean() ? random.nextFloat() : -random.nextFloat()), this.getRandomBodyY(), this.getZ() + (random.nextBoolean() ? random.nextFloat() : -random.nextFloat()), 0, 0, 0);
                }
            }
            if (this.getPipeCooldown() > 0) {
                --this.pipeCooldown;
            }
        }
    }
}

