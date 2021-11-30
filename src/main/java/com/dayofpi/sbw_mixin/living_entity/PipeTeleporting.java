package com.dayofpi.sbw_mixin.living_entity;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.type.warp_pipe.WarpPipeBlock;
import com.dayofpi.sbw_main.entity.registry.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class PipeTeleporting extends Entity {
    @Shadow
    @Final
    private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;
    private int pipeCooldown;

    public PipeTeleporting(EntityType<?> type, World world) {
        super(type, world);
    }

    public void setSneaking(boolean sneaking) {
        if (sneaking) {
            this.warpToPipe();
        }
        super.setSneaking(sneaking);
    }

    public void warpToPipe() {
        if (!world.isClient) {
            BlockPos entityPos = this.getBlockPos();
            BlockPos floor = this.getBlockPos().down();

            if (this.getPipeCooldown() == 0) {
                if (world.getBlockState(entityPos).isAir() && world.getBlockState(floor).isOf(ModBlocks.WARP_PIPE) && world.getBlockState(floor).get(Properties.FACING) == Direction.UP) {
                    BlockPos destination = WarpPipeBlock.warpPipeTree.getNearestBlock(floor, world, this.getHeadYaw());
                    if (destination != null) {
                        world.playSound(null, floor, ModSounds.BLOCK_WARP_PIPE_TELEPORT, SoundCategory.PLAYERS, 0.5F, 1.0F);
                        this.requestTeleport(destination.getX() + 0.5, destination.getY() + 1.0F, destination.getZ() + 0.5);
                        this.setPipeCooldown(20);
                        world.sendEntityStatus(this, (byte) 46);
                        world.playSound(null, destination, ModSounds.BLOCK_WARP_PIPE_TELEPORT, SoundCategory.PLAYERS, 0.5F, 1.0F);
                    }
                }
            }
        }
    }

    public int getPipeCooldown() {
        return pipeCooldown;
    }

    public void setPipeCooldown(int cooldown) {
        this.pipeCooldown = cooldown;
    }

    @Inject(at = @At("TAIL"), method = "baseTick")
    void baseTick(CallbackInfo info) {
        if (this.activeStatusEffects.containsKey(ModEffects.STAR_POWER)) {
            for (int i = 0; i < 1; ++i) {
                world.addParticle(ParticleTypes.GLOW, true, this.getX() + (random.nextBoolean() ? random.nextFloat() : -random.nextFloat()), this.getRandomBodyY(), this.getZ() + (random.nextBoolean() ? random.nextFloat() : -random.nextFloat()), 0, 0, 0);
            }
        }
        if (this.getPipeCooldown() > 0) {
            --this.pipeCooldown;
        }
    }
}

