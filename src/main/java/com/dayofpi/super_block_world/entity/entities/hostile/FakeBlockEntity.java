package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.client.registry.GlobalReceivers;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class FakeBlockEntity extends HostileEntity {
    public final AnimationState attackingAnimationState = new AnimationState();
    private boolean twirling = false;
    private static final TrackedData<Integer> ATTACK_COOLDOWN = DataTracker.registerData(FakeBlockEntity.class, TrackedDataHandlerRegistry.INTEGER);

    public FakeBlockEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createFakeBlockAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.22D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 3.0D);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(3, new PounceAtTargetGoal(this, 0.5F));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACK_COOLDOWN, 0);
    }

    public void setAttackCooldown(int cooldown) {
        this.dataTracker.set(ATTACK_COOLDOWN, cooldown);
    }

    private int getAttackCooldown() {
        return this.dataTracker.get(ATTACK_COOLDOWN);
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.BLOCK_TOADSTONE_BREAK;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(Sounds.BLOCK_TOADSTONE_STEP, 0.15F, 1.0F);
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return false;
    }

    public boolean tryAttack(Entity target) {
        return false;
    }

    private boolean isTwirling() {
        return this.twirling;
    }

    public void setTwirling(boolean twirling) {
        this.twirling = twirling;
    }

    @Override
    public void tick() {
        if (this.getAttackCooldown() > 0) {
            this.setAttackCooldown(this.getAttackCooldown() - 1);
            if (this.getAttackCooldown() < 50)
                this.setTarget(null);
        }
        if (this.world.isClient) {
            if (this.isTwirling()) {
                if (!this.attackingAnimationState.isRunning()) {
                    this.attackingAnimationState.start(this.age);
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeInt(this.getId());
                    buf.writeIdentifier(this.getEntityWorld().getRegistryKey().getValue());
                    ClientPlayNetworking.send(GlobalReceivers.FAKE_BLOCK_EVENT, buf);
                }
            } else this.attackingAnimationState.stop();
        }
        super.tick();
    }

    public void tickMovement() {
        super.tickMovement();
        if (this.isAlive()) {
            LivingEntity target = this.getTarget();
            if (!this.world.isClient && this.world.getServer() != null) {
                for (ServerPlayerEntity player : PlayerLookup.all(this.world.getServer())) {
                    this.setTwirling(target != null && this.distanceTo(target) <= 3F);
                    PacketByteBuf buf = PacketByteBufs.create();
                    buf.writeInt(this.getId());
                    buf.writeBoolean(this.isTwirling());
                    ServerPlayNetworking.send(player, GlobalReceivers.FAKE_BLOCK_STATE, buf);
                }
            }

            Vec3d vec3d = this.getVelocity();
            if (!this.onGround && vec3d.y < 0.0D) {
                this.setVelocity(vec3d.multiply(1.0D, 0.6D, 1.0D));
            }
        }
    }
}
