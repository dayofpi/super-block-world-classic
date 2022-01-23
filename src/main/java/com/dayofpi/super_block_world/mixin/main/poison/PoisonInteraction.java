package com.dayofpi.super_block_world.mixin.main.poison;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.util.entity.ModDamageSource;
import com.dayofpi.super_block_world.registry.main.TagInit;
import com.dayofpi.super_block_world.registry.more.ParticleInit;
import net.minecraft.entity.*;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class PoisonInteraction extends Entity {
    public PoisonInteraction(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract EntityGroup getGroup();

    @Shadow public abstract EntityDimensions getDimensions(EntityPose pose);

    @Inject(at = @At("TAIL"), method = "baseTick")
    void baseTick(CallbackInfo info) {
        if (!this.getType().isIn(TagInit.POISON_IMMUNE) && this.getGroup() != EntityGroup.UNDEAD) {
            if (this.updateMovementInFluid(TagInit.POISON, 0.0023)) {
                if (this.damage(ModDamageSource.POISON, 4.0F)) {
                    World world = this.getWorld();
                    if (world.isClient)
                        world.addParticle(ParticleTypes.LARGE_SMOKE, this.getBlockPos().getX() + 0.5, this.getBlockPos().getY() + 1, this.getBlockPos().getZ() + 0.5, 0.0D, 0.0D, 0.0D);
                    else
                        this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.4F, 2.0F + this.random.nextFloat() * 0.4F);
                }
            }
        }
    }

    protected void onSwimmingStart() {
        if (this.updateMovementInFluid(TagInit.POISON, 0.0023)) {
            // Replace water splash particles
            Entity entity = this.hasPassengers() && this.getPrimaryPassenger() != null ? this.getPrimaryPassenger() : this.getWorld().getEntityById(this.getId());
            if (entity != null) {
                this.playSound(SoundInit.FLUID_POISON_SWIM, 0.4F, 1.0F + (this.random.nextFloat() - this.random.nextFloat() * 0.5F));
                float y = (float) MathHelper.floor(this.getY());
                double x;
                double z;
                for (int i = 0; (float) i < 1.0F + this.getDimensions(EntityPose.STANDING).width * 20.0F; ++i) {
                    x = (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getDimensions(EntityPose.STANDING).width;
                    z = (this.random.nextDouble() * 2.0D - 1.0D) * (double) this.getDimensions(EntityPose.STANDING).width;
                    this.getWorld().addParticle(ParticleInit.POISON_BUBBLE, this.getX() + x, y + 1.0F, this.getZ() + z, 0, 0, 0);
                }
            }
        } else super.onSwimmingStart();
    }
}

