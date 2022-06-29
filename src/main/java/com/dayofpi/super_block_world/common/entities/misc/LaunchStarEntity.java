package com.dayofpi.super_block_world.common.entities.misc;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.AbstractDecorationEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LaunchStarEntity extends AbstractDecorationEntity {
    private static final TrackedData<Integer> COOLDOWN;
    private static final int MAX_COOLDOWN = 30;

    static {
        COOLDOWN = DataTracker.registerData(LaunchStarEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    public final AnimationState idlingAnimationState = new AnimationState();
    public final AnimationState launchingAnimationState = new AnimationState();
    private int ambientSoundChance;

    public LaunchStarEntity(EntityType<? extends AbstractDecorationEntity> entityType, World world) {
        super(entityType, world);
    }

    public LaunchStarEntity(World world, BlockPos pos, Direction direction) {
        super(ModEntities.LAUNCH_STAR, world, pos);
        this.setFacing(direction);
        this.ignoreCameraFrustum = true;
    }

    public int getCooldown() {
        return this.dataTracker.get(COOLDOWN);
    }

    public void setCooldown(int time) {
        this.dataTracker.set(COOLDOWN, time);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("LaunchCooldown", this.getCooldown());
        nbt.putByte("Facing", (byte) this.facing.getId());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setCooldown(nbt.getInt("LaunchCooldown"));
        this.setFacing(Direction.byId(nbt.getByte("Facing")));
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(COOLDOWN, 0);
    }

    public int getMinAmbientSoundDelay() {
        return 120;
    }

    public void playAmbientSound() {
        SoundEvent soundEvent = Sounds.ENTITY_LAUNCH_STAR_AMBIENT;
        this.playSound(soundEvent, 0.64F, 1.0F);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.world.isClient) {
            if (this.getCooldown() > 0) {
                this.idlingAnimationState.stop();
                this.launchingAnimationState.startIfNotRunning(this.age);
            } else {
                this.launchingAnimationState.stop();
                this.idlingAnimationState.startIfNotRunning(this.age);
            }
            if (random.nextFloat() > 0.8F)
                this.world.addParticle(ParticleTypes.WAX_OFF, this.getParticleX(0.5), this.getRandomBodyY(), this.getParticleZ(0.5), Vec3d.of(this.facing.getVector()).x, Vec3d.of(this.facing.getVector()).y, Vec3d.of(this.facing.getVector()).z);
        }

        List<Entity> list = world.getOtherEntities(this, this.getBoundingBox(), EntityPredicates.EXCEPT_SPECTATOR);
        if (this.getCooldown() > 0) {
            this.setCooldown(this.getCooldown() - 1);
        }

        final int launchTime = MAX_COOLDOWN / 2;
        List<Entity> passengers = new ArrayList<>(List.of());

        if (this.isAlive() && this.random.nextInt(1000) < this.ambientSoundChance++) {
            this.resetSoundDelay();
            this.playAmbientSound();
        }

        if (!list.isEmpty()) {
            passengers.addAll(list);
            if (this.getCooldown() == 0 && !passengers.isEmpty()) {
                this.setCooldown(MAX_COOLDOWN);
                this.playSound(Sounds.ENTITY_LAUNCH_STAR_USE, 5.0F, 1.0F);
            }
            for (Iterator<Entity> iterator = passengers.iterator(); iterator.hasNext(); ) {
                Entity entity = iterator.next();
                if (this.getCooldown() > launchTime) {
                    Vec3d vec3d;
                    double d;
                    if ((d = (vec3d = new Vec3d(this.getX() - entity.getX(), this.getY() - entity.getY(), this.getZ() - entity.getZ())).lengthSquared()) < 64.0) {
                        double e = 1.0 - Math.sqrt(d) / 8.0;
                        entity.setVelocity(entity.getVelocity().add(vec3d.normalize().multiply(e * e * 0.2)));
                    }
                } else if (this.getCooldown() == launchTime) {
                    if (world instanceof ServerWorld) {
                        ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, this.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), this.getY() + 0.5D, this.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                        ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, this.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), this.getY() + 0.5D, this.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                    }
                    entity.setVelocity(Vec3d.of(this.facing.getVector()).multiply(3));
                    iterator.remove();
                }
            }
        }

    }

    private void resetSoundDelay() {
        this.ambientSoundChance = -this.getMinAmbientSoundDelay();
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.getCooldown() > 0)
            return false;
        return super.damage(source, amount);
    }

    @Override
    public boolean canStayAttached() {
        if (!this.world.isSpaceEmpty(this)) {
            return false;
        }
        return this.world.getOtherEntities(this, this.getBoundingBox(), PREDICATE).isEmpty();
    }

    @Override
    public int getWidthPixels() {
        return 16;
    }

    @Override
    public int getHeightPixels() {
        return 16;
    }

    @Override
    public void onBreak(@Nullable Entity entity) {
        this.playSound(SoundEvents.BLOCK_METAL_BREAK, 1.0F, 1.0F);
        if (entity instanceof PlayerEntity player && player.getAbilities().creativeMode) return;
        if (world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) this.dropStack(this.getPickBlockStack());
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this, this.facing.getId(), this.getDecorationBlockPos());
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        this.setFacing(Direction.byId(packet.getEntityData()));
    }

    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(ModItems.LAUNCH_STAR);
    }

    @Override
    public void onPlace() {
        this.playSound(Sounds.ENTITY_LAUNCH_STAR_PLACE, 1.0F, 1.0F);
    }

    @Override
    protected float getEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return 0.0f;
    }

    @Override
    public boolean shouldRender(double distance) {
        double d = 16.0;
        return distance < (d *= 64.0 * getRenderDistanceMultiplier()) * d;
    }

    @Override
    protected void setFacing(Direction facing) {
        Validate.notNull(facing);
        this.facing = facing;
        if (facing.getAxis().isHorizontal()) {
            this.setPitch(0.0f);
            this.setYaw(this.facing.getHorizontal() * 90);
        } else {
            this.setPitch(-90 * facing.getDirection().offset());
            this.setYaw(0.0f);
        }
        this.prevPitch = this.getPitch();
        this.prevYaw = this.getYaw();
        this.updateAttachmentPosition();
    }

    @Override
    protected void updateAttachmentPosition() {
        if (this.facing == null) {
            return;
        }
        double x = (double) this.attachmentPos.getX() + 0.5 - (double) this.facing.getOffsetX() * 0.46875;
        double y = (double) this.attachmentPos.getY() + 0.5 - (double) this.facing.getOffsetY() * 0.46875;
        double z = (double) this.attachmentPos.getZ() + 0.5 - (double) this.facing.getOffsetZ() * 0.46875;
        double xp = this.getWidthPixels();
        double yp = this.getHeightPixels();
        double zp = this.getWidthPixels();
        Direction.Axis axis = this.facing.getAxis();
        switch (axis) {
            case X -> {
                xp = 5.0;
                x += 0.3 * this.facing.getOffsetX();
            }
            case Y -> {
                yp = 5.0;
                y += 0.3 * this.facing.getOffsetY();
            }
            case Z -> {
                zp = 5.0;
                z += 0.3 * this.facing.getOffsetZ();
            }
        }
        this.setPos(x, y, z);
        this.setBoundingBox(new Box(x - (xp /= 32.0), y - (yp /= 32.0), z - (zp /= 32.0), x + xp, y + yp, z + zp));
    }
}
