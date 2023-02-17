package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;

public class LavaBubbleEntity extends MagmaCubeEntity {
    public float angle;
    public LavaBubbleEntity(EntityType<? extends MagmaCubeEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public boolean canSpawn(WorldView world) {
        return world.doesNotIntersectEntities(this);
    }

    @Override
    protected ParticleEffect getParticles() {
        return ParticleTypes.FALLING_LAVA;
    }

    @SuppressWarnings("unused")
    public static boolean canLavaBubbleSpawn(EntityType<LavaBubbleEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        BlockPos.Mutable mutable = pos.mutableCopy();
        do {
            mutable.move(Direction.UP);
        } while (world.getFluidState(mutable).isIn(FluidTags.LAVA));
        return world.getBlockState(mutable).isAir();
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.BUCKET) && this.getSize() == 2) {
            for (int j = 0; j < 2 * 8; ++j) {
                float f = this.random.nextFloat() * ((float)Math.PI * 2);
                float g = this.random.nextFloat() * 0.5f + 0.5f;
                float h = MathHelper.sin(f) * 2 * 0.5f * g;
                float k = MathHelper.cos(f) * 2 * 0.5f * g;
                this.world.addParticle(this.getParticles(), this.getX() + (double)h, this.getY(), this.getZ() + (double)k, 0.0, 0.0, 0.0);
            }
            player.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1.0f, 1.0f);
            ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, Items.LAVA_BUCKET.getDefaultStack());
            player.setStackInHand(hand, itemStack2);
            this.discard();
            return ActionResult.success(this.world.isClient);
        }
        return super.interactMob(player, hand);
    }

    @Override
    public void tickMovement() {
        if (!this.isOnGround()) {
            this.angle += 0.2f;
        } else this.angle = 0.0f;
        this.angle = MathHelper.clamp(angle, 0.0f, 30.0f);
        super.tickMovement();
    }

    @Override
    protected void swimUpward(TagKey<Fluid> fluid) {
        if (fluid == FluidTags.LAVA) {
            if (this.isSubmergedIn(FluidTags.LAVA)) {
                Vec3d vec3d = this.getVelocity();
                this.setVelocity(vec3d.x, 0.22f + (float) this.getSize() * 0.05f, vec3d.z);
                this.velocityDirty = true;
            }
        } else {
            super.swimUpward(fluid);
        }
    }

    @Override
    public void setSize(int size, boolean heal) {
        super.setSize(size, heal);
        EntityAttributeInstance attributeInstance = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (attributeInstance != null) attributeInstance.setBaseValue(size);
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_LAVA_BUBBLE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_LAVA_BUBBLE_DEATH;
    }

    @Override
    protected SoundEvent getJumpSound() {
        return Sounds.ENTITY_LAVA_BUBBLE_JUMP;
    }

    @Override
    protected SoundEvent getSquishSound() {
        return Sounds.ENTITY_LAVA_BUBBLE_JUMP;
    }

    @Override
    public boolean isOnGround() {
        if (this.isInLava() && this.getFluidHeight(FluidTags.LAVA) < 0.2)
            return true;
        return super.isOnGround();
    }
}
