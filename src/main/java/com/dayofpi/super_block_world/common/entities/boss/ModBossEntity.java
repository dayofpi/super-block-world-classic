package com.dayofpi.super_block_world.common.entities.boss;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.MusicSound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public abstract class ModBossEntity extends HostileEntity {
    private static final TrackedData<BlockPos> ARENA_POS;

    static {
        ARENA_POS = DataTracker.registerData(ModBossEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);
    }

    protected ServerBossBar bossBar;

    protected ModBossEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ARENA_POS, BlockPos.ORIGIN);
    }

    @Nullable
    @Override
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        this.setArenaPos(this.getBlockPos());
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }

    public BlockPos getArenaPos() {
        return this.dataTracker.get(ARENA_POS);
    }

    private void setArenaPos(BlockPos arenaPos) {
        this.dataTracker.set(ARENA_POS, arenaPos);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("ArenaX", this.getArenaPos().getX());
        nbt.putInt("ArenaY", this.getArenaPos().getY());
        nbt.putInt("ArenaZ", this.getArenaPos().getZ());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setArenaPos(new BlockPos(nbt.getInt("ArenaX"), nbt.getInt("ArenaY"), nbt.getInt("ArenaZ")));

    }

    public void setCustomName(@Nullable Text name) {
        super.setCustomName(name);
        this.bossBar.setName(this.getDisplayName());
    }

    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        this.bossBar.addPlayer(player);
    }

    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        this.bossBar.removePlayer(player);
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

    protected void dropEquipment(DamageSource source, int lootingMultiplier, boolean allowDrops) {
        super.dropEquipment(source, lootingMultiplier, allowDrops);
        ItemEntity itemEntity = this.dropItem(ModItems.POWER_STAR);
        if (itemEntity != null) {
            itemEntity.setCovetedItem();
        }
        if (!world.isClient)
            this.openLocks();
    }

    public void checkDespawn() {
        if (this.world.getDifficulty() == Difficulty.PEACEFUL && this.isDisallowedInPeaceful()) {
            this.discard();
        } else {
            this.despawnCounter = 0;
        }
    }
}
