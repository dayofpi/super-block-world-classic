package com.dayofpi.super_block_world.common.entities;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entities.abst.AbstractEnemy;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.google.common.collect.Sets;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Iterator;
import java.util.Set;

public class DryBonesShellEntity extends AbstractEnemy implements IAnimatable, Mount {
    private final AnimationFactory factory = new AnimationFactory(this);

    public DryBonesShellEntity(EntityType<? extends AbstractEnemy> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 0;
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        player.startRiding(this);
        return ActionResult.success(this.world.isClient);
    }

    @Override
    public double getMountedHeightOffset() {
        float f = Math.min(0.25F, this.limbDistance);
        float g = this.limbAngle;
        return (double)this.getHeight() - 1.4D + (double)(0.12F * MathHelper.cos(g * 0.8F) * f);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.ENTITY_SHELL_BREAK;
    }

    @Override
    @Nullable
    public Entity getPrimaryPassenger() {
        return this.getFirstPassenger();
    }

    @Override
    public void travel(Vec3d movementInput) {
        if (!this.isAlive()) {
            return;
        }
        if (!(this.hasPassengers() && this.canBeControlledByRider())) {
            this.airStrafingSpeed = 0.02f;
            super.travel(movementInput);
            return;
        }
        LivingEntity livingEntity = (LivingEntity)this.getPrimaryPassenger();
        if (livingEntity != null) {
            this.setYaw(livingEntity.getYaw());
            this.prevYaw = this.getYaw();
            this.setPitch(livingEntity.getPitch() * 0.5f);
            this.setRotation(this.getYaw(), this.getPitch());
            this.headYaw = this.bodyYaw = this.getYaw();
            float f = livingEntity.sidewaysSpeed * 0.5f;
            float g = livingEntity.forwardSpeed;

            if (g <= 0.0f) {
                g *= 0.25f;
            }

            this.airStrafingSpeed = this.getMovementSpeed() * 0.1f;
            if (this.isLogicalSideForUpdatingMovement()) {
                this.setMovementSpeed((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) * 0.05f);
                super.travel(new Vec3d(f, movementInput.y, g));
            } else if (livingEntity instanceof PlayerEntity) {
                this.setVelocity(Vec3d.ZERO);
            }
        }
        this.updateLimbs(this, true);
        this.tryCheckBlockCollision();
    }

    public Vec3d updatePassengerForDismount(LivingEntity passenger) {
        Vec3d[] vec3ds = new Vec3d[]{getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), passenger.getYaw()), getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), passenger.getYaw() - 22.5F), getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), passenger.getYaw() + 22.5F), getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), passenger.getYaw() - 45.0F), getPassengerDismountOffset(this.getWidth(), passenger.getWidth(), passenger.getYaw() + 45.0F)};
        Set<BlockPos> set = Sets.newLinkedHashSet();
        double d = this.getBoundingBox().maxY;
        double e = this.getBoundingBox().minY - 0.5D;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for (Vec3d vec3d : vec3ds) {
            mutable.set(this.getX() + vec3d.x, d, this.getZ() + vec3d.z);

            for (double f = d; f > e; --f) {
                set.add(mutable.toImmutable());
                mutable.move(Direction.DOWN);
            }
        }

        Iterator<BlockPos> var17 = set.iterator();

        while(true) {
            BlockPos blockPos;
            double g;
            do {
                do {
                    if (!var17.hasNext()) {
                        return new Vec3d(this.getX(), this.getBoundingBox().maxY, this.getZ());
                    }

                    blockPos = var17.next();
                } while(this.world.getFluidState(blockPos).isIn(FluidTags.LAVA));

                g = this.world.getDismountHeight(blockPos);
            } while(!Dismounting.canDismountInBlock(g));

            Vec3d f = Vec3d.ofCenter(blockPos, g);

            for (EntityPose entityPose : passenger.getPoses()) {
                Box box = passenger.getBoundingBox(entityPose);
                if (Dismounting.canPlaceEntityAt(this.world, passenger, box.offset(f))) {
                    passenger.setPose(entityPose);
                    return f;
                }
            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.updateMovementInFluid(FluidTags.LAVA, 0.014)) {
            if (this.getFluidHeight(FluidTags.LAVA) > 0.05D)
                this.addVelocity(0.0D, 0.1D, 0.0D);
            else {
                if (this.getVelocity().horizontalLengthSquared() < 0.1)
                    this.setVelocity(this.getVelocity().multiply(1.8, 0, 1.8));
            }
        }
    }

    @Override
    public boolean canBeControlledByRider() {
        return this.getPrimaryPassenger() instanceof LivingEntity;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundInit.ENTITY_BUZZY_DROP;
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        this.dropItem(ItemInit.DRY_BONES_SHELL);
    }

    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(ItemInit.DRY_BONES_SHELL);
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("spin", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return AbstractEnemy.createEnemyAttributes()
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.02D)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
