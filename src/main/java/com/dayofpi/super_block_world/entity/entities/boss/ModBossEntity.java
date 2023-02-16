package com.dayofpi.super_block_world.entity.entities.boss;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.item.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class ModBossEntity extends HostileEntity {
    protected ServerBossBar bossBar;
    private static final TrackedData<Integer> NEXT_ATTACK;

    static {
        NEXT_ATTACK = DataTracker.registerData(ModBossEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
    protected ModBossEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected float getSoundVolume() {
        return 2.0f;
    }

    public static Optional<? extends LivingEntity> getAttackTarget(ModBossEntity boss) {
        return boss.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
    }

    private void setCurrentPosAsHome() {
        GlobalPos globalPos = GlobalPos.create(this.world.getRegistryKey(), this.getBlockPos());
        this.getBrain().remember(MemoryModuleType.HOME, globalPos);
    }

    public static void cooldown(LivingEntity entity, int cooldown) {
        entity.getBrain().remember(MemoryModuleType.SONIC_BOOM_COOLDOWN, Unit.INSTANCE, cooldown);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setCurrentPosAsHome();
        this.setNextAttack(random.nextInt(this.getMaxAttacks()));
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(NEXT_ATTACK, 0);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl = super.damage(source, amount);
        if (this.world.isClient) {
            return false;
        }
        if (this.brain.hasMemoryModule(MemoryModuleType.ATTACK_TARGET) || !bl || !(source.getAttacker() instanceof LivingEntity livingEntity)) {
            return bl;
        }
        if (this.canTarget(livingEntity) && !LookTargetUtil.isNewTargetTooFar(this, livingEntity, 4.0)) {
            this.setAttackTarget(livingEntity);
        }
        return true;
    }

    private void setAttackTarget(LivingEntity entity) {
        this.brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        this.brain.remember(MemoryModuleType.ATTACK_TARGET, entity, 200L);
    }

    @Override
    protected void mobTick() {
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        if (this.world instanceof ServerWorld) {
            for (ServerPlayerEntity player : this.bossBar.getPlayers()) {
                if (player.distanceTo(this) > 48) {
                    this.bossBar.removePlayer(player);
                }
            }
            for (PlayerEntity player : world.getPlayers(TargetPredicate.createNonAttackable(), this, Box.from(BlockBox.create(this.getBlockPos().add(30, 10, 30), this.getBlockPos().add(-30, -10, -30))))) {
                if (player instanceof ServerPlayerEntity) {
                    this.bossBar.addPlayer((ServerPlayerEntity) player);
                }
            }
        }
        this.setAttacking(this.brain.hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
        super.mobTick();
    }

    @Override
    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
    }

    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    public abstract MusicSound getBossMusic();

    private void openLocks() {
        boolean shouldPlay = true;
        for (BlockPos blockPos : BlockPos.iterateOutwards(this.getBlockPos(), 64, 64, 64)) {
            if (world.getBlockState(blockPos).isOf(ModBlocks.BOWSER_LOCK)) {
                if (shouldPlay)
                    world.playSound(null, blockPos, Sounds.BLOCK_BOWSER_LOCK_OPEN, SoundCategory.BLOCKS, 0.8F, 1.0F);
                shouldPlay = false;
                world.setBlockState(blockPos, Blocks.AIR.getDefaultState());
                for (int i = 0; i < 4; ++i) {
                    ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, blockPos.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    protected Item getStar() {
        return ModItems.POWER_STAR;
    }

    @Nullable
    protected abstract Item getRareItem();

    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        ItemEntity itemEntity = this.dropItem(this.getStar());
        if (itemEntity != null) {
            itemEntity.setCovetedItem();
        }
        if (!world.isClient)
            this.openLocks();
        if (this.getRareItem() != null && random.nextInt(3) == 0)
            this.dropItem(this.getRareItem());
    }

    public abstract int getMaxAttacks();

    public int getNextAttack() {
        return this.dataTracker.get(NEXT_ATTACK);
    }

    public void setNextAttack(int nextAttack) {
        this.dataTracker.set(NEXT_ATTACK, nextAttack);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("NextAttack", this.getNextAttack());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (this.hasCustomName()) {
            this.bossBar.setName(this.getDisplayName());
        }
        this.setNextAttack(nbt.getInt("NextAttack"));
    }

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
            this.discard();
        } else {
            this.despawnCounter = 0;
        }
    }
}
