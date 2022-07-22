package com.dayofpi.super_block_world.common.entities.misc;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.blocks.BrickBlock;
import com.dayofpi.super_block_world.common.blocks.FakeBlock;
import com.dayofpi.super_block_world.common.blocks.ReactiveBlock;
import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.registry.ModTags;
import com.google.common.collect.Sets;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DryBonesShellEntity extends MobEntity implements Mount {
    public DryBonesShellEntity(EntityType<? extends MobEntity> entityType, World world) {
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
        return (double)this.getHeight() - 0.45D + (double)(0.12F * MathHelper.cos(g * 0.8F) * f);
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
                } while(this.world.getFluidState(blockPos).isIn(FluidTags.LAVA) || this.world.getFluidState(blockPos).isIn(ModTags.POISON));

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
        if (this.updateMovementInFluid(FluidTags.LAVA, 0.014) || this.updateMovementInFluid(ModTags.POISON, 0.014)) {
            if (this.getFluidHeight(FluidTags.LAVA) > 0.05D || this.getFluidHeight(ModTags.POISON) > 0.05D)
                this.addVelocity(0.0D, 0.1D, 0.0D);
            else {
                if (this.getVelocity().horizontalLengthSquared() < 0.5) {
                    this.setVelocity(this.getVelocity().multiply(1.8, 0, 1.8));
                }
                if (this.getVelocity().horizontalLength() > 0.05) {
                    Box box = this.getBoundingBox().expand(0.3);

                    List<BlockPos> posList = BlockPos.stream(box).toList();
                    for (BlockPos blockPos : posList) {
                        BlockState blockState = world.getBlockState(blockPos);
                        if (this.getBlockY() >= blockPos.getY()) {
                            if (blockState.getBlock() instanceof ReactiveBlock reactiveBlock) {
                                reactiveBlock.react(world, blockPos, this);
                            }
                            if (blockState.getBlock() instanceof BrickBlock || blockState.getBlock() instanceof FakeBlock) {
                                world.breakBlock(blockPos, true);
                            }
                        }
                    }
                    if (this.world.isClient && random.nextInt(60) == 0)
                        world.addParticle(ParticleTypes.POOF, this.getParticleX(0.5D), this.getY(), this.getParticleZ(0.5D), -this.getVelocity().x, 0.1D, -this.getVelocity().y);
                }
            }
        }
    }

    public boolean canBeControlledByRider() {
        return this.getPrimaryPassenger() instanceof LivingEntity;
    }

    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(ModItems.DRY_BONES_SHELL);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (super.damage(source, amount)) {
            this.remove(Entity.RemovalReason.KILLED);
            this.emitGameEvent(GameEvent.ENTITY_DIE);
            if (!source.isSourceCreativePlayer())
                this.dropItem(ModItems.DRY_BONES_SHELL);
        }
        return super.damage(source, amount);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_SHELL_HIT;
    }

    public static DefaultAttributeContainer.Builder createDryBonesShellAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.02D).add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D);
    }
}
