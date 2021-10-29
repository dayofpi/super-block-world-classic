package com.dayofpi.sbw_mixin.living_entity;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.types.WarpPipeBlock;
import com.dayofpi.sbw_main.entity.registry.ModEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class PipeTeleporting extends Entity {
    private int pipeCooldown;
    public PipeTeleporting(EntityType<?> type, World world) {
        super(type, world);
    }

    public int getPipeCooldown() {
        return pipeCooldown;
    }

    public void setPipeCooldown(int cooldown) {
        this.pipeCooldown = cooldown;
    }

    public void setSneaking(boolean sneaking) {
        if (sneaking) {
            this.warpToPipe();
        }
        super.setSneaking(sneaking);
    }

    public void warpToPipe() {
        BlockPos currentPos = this.getBlockPos().down();

        if (this.getPipeCooldown() == 0 && world.getBlockState(currentPos).isOf(ModBlocks.WARP_PIPE) && world.getBlockState(currentPos).get(Properties.FACING) == Direction.UP) {
            BlockPos newPos = WarpPipeBlock.warpPipeTree.getNearestBlock(currentPos, world, this.getHeadYaw());
            if (newPos != null) {
                if (!world.isClient) {
                    world.playSound(null, currentPos, ModSounds.BLOCK_WARP_PIPE_TELEPORT, SoundCategory.PLAYERS, 0.5F, 1.0F);
                    this.setPipeCooldown(20);
                    this.requestTeleport(newPos.getX() + 0.5, newPos.getY() + 1.0F, newPos.getZ() + 0.5);
                    world.sendEntityStatus(this, (byte) 46);
                    world.playSound(null, newPos, ModSounds.BLOCK_WARP_PIPE_TELEPORT, SoundCategory.PLAYERS, 0.5F, 1.0F);
                }
            }

        }
    }

    @Inject(at = @At("TAIL"), method = "baseTick")
    void baseTick(CallbackInfo info) {
        if (((LivingEntityAccessors)this).activeStatusEffects().containsKey(ModEffects.STAR_POWER))  {
            for(int i = 0; i < 1; ++i) {
                world.addParticle(ParticleTypes.GLOW, true, this.getX() + (random.nextBoolean() ? random.nextFloat() : -random.nextFloat()), this.getRandomBodyY(), this.getZ() + (random.nextBoolean() ? random.nextFloat() : -random.nextFloat()), 0, 0, 0);
            }
        }
        if (this.getPipeCooldown() > 0) {
            --this.pipeCooldown;
        }
    }
}

