package com.dayofpi.super_block_world.common.entities.abst;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.registry.main.TagInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.common.util.entity.ModEntityDamageSource;
import com.dayofpi.super_block_world.common.util.entity.CustomSpawnPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public abstract class AbstractShell extends LivingEntity {
    private static final TrackedData<Boolean> ACTIVE;

    static {
        ACTIVE = DataTracker.registerData(AbstractShell.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ACTIVE, false);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if (!this.isActive()) {
            this.playSound(SoundInit.ENTITY_BUZZY_DROP, 1.0F, this.getSoundPitch());
            this.setActive(true);
        } else {
            if (entity instanceof LivingEntity livingEntity) {
                entity.damage(ModEntityDamageSource.mobDrop(this), 4.0F);
                if (livingEntity.getEquippedStack(EquipmentSlot.FEET).isOf(ItemInit.JUMP_BOOTS) && livingEntity.getY() > this.getY()) {
                    this.setActive(false);
                    this.playSound(SoundInit.ENTITY_BUZZY_DROP, 1.0F, this.getSoundPitch());
                }
            }
        }
    }

    public void tick() {
        if (this.isActive()) {
            if (this.world.isClient && random.nextFloat() > 0.6F)
                world.addParticle(ParticleTypes.SWEEP_ATTACK, this.getX(), this.getY() + 0.5, this.getZ(), 0, 0, 0);
            Vec3d vec3d = this.getVelocity();
            double speedLimit = 0.16D;
            boolean xLimit = vec3d.x > speedLimit || vec3d.x < -speedLimit;
            boolean zLimit = vec3d.z > speedLimit || vec3d.z < -speedLimit;
            this.setVelocity(vec3d.multiply(xLimit ? 1.0D : 2.0D, 1.0D, zLimit ? 1.0D : 2.0D));
            for (BlockPos blockPos : BlockPos.iterateOutwards(this.getBlockPos(), 1, 0, 1)) {
                if (world.getBlockState(blockPos).isIn(TagInit.BRICKS)) {
                    world.breakBlock(blockPos, true);
                }
            }
        }
        super.tick();
    }

        public boolean isActive() {
        return this.dataTracker.get(ACTIVE);
    }

    public void setActive(boolean active) {
        this.dataTracker.set(ACTIVE, active);
    }

    protected AbstractShell(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    public ItemStack getPickBlockStack() {
        return this.asItemStack();
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return LivingEntity.createLivingAttributes();
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return CustomSpawnPacket.create(this, Main.PacketID);
    }

    public boolean damage(DamageSource source, float amount) {
    if (this.isInvulnerableTo(source)) {
        return false;
    } else if (source instanceof ModEntityDamageSource damageSource) {
        if (damageSource.isStomp() && this.isActive()) {
            this.setActive(false);
        }
    } else if (!this.world.isClient && !this.isRemoved()) {
        boolean bl = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity)source.getAttacker()).getAbilities().creativeMode;
        if (!bl && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            this.dropItem(this.asItemStack().getItem());
        }
        this.playSound(SoundInit.ENTITY_BUZZY_DROP, 1.0F, 1.0F);
        this.discard();
        return true;
    }
    return true;
    }

    protected abstract ItemStack asItemStack();
}
