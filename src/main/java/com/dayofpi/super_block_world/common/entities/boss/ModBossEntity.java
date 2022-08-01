package com.dayofpi.super_block_world.common.entities.boss;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
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

    protected ModBossEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static Optional<? extends LivingEntity> getAttackTarget(ModBossEntity boss) {
        return boss.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
    }

    private void setCurrentPosAsHome() {
        GlobalPos globalPos = GlobalPos.create(this.world.getRegistryKey(), this.getBlockPos());
        this.getBrain().remember(MemoryModuleType.HOME, globalPos);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setCurrentPosAsHome();
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    @Override
    protected void mobTick() {
        this.bossBar.setPercent(this.getHealth() / this.getMaxHealth());
        if (this.world instanceof ServerWorld) {
            for (ServerPlayerEntity player : this.bossBar.getPlayers()) {
                if (player.distanceTo(this) > 32) {
                    this.bossBar.removePlayer(player);
                }
            }
            for (PlayerEntity player : world.getPlayers(TargetPredicate.createNonAttackable(), this, Box.from(BlockBox.create(this.getBlockPos().add(30, 10, 30), this.getBlockPos().add(-30, -10, -30))))) {
                if (player instanceof ServerPlayerEntity) {
                    this.bossBar.addPlayer((ServerPlayerEntity) player);
                }
            }
        }
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

    @Override
    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
            this.discard();
        } else {
            this.despawnCounter = 0;
        }
    }
}
