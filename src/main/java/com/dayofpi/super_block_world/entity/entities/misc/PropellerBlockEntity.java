package com.dayofpi.super_block_world.entity.entities.misc;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.criterion.ModCriteria;
import com.dayofpi.super_block_world.entity.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class PropellerBlockEntity extends Entity {
    protected static final TrackedData<Integer> TARGET_HEIGHT = DataTracker.registerData(PropellerBlockEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private BlockState blockState = ModBlocks.PROPELLER_BLOCK.getDefaultState();

    public PropellerBlockEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    private PropellerBlockEntity(World world, double x, double y, double z, BlockState blockState) {
        this(ModEntities.PROPELLER_BLOCK, world);
        this.setBlockState(blockState);
        this.intersectionChecked = true;
        this.setPosition(x, y, z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(TARGET_HEIGHT, 0);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setTargetHeight(nbt.getInt("TargetHeight"));
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("TargetHeight", this.getTargetHeight());
    }

    public int getTargetHeight() {
        return this.dataTracker.get(TARGET_HEIGHT);
    }

    public void setTargetHeight(int height) {
        this.dataTracker.set(TARGET_HEIGHT, height);
    }

    @Override
    public void tick() {
        super.tick();
        BlockPos ceiling = this.getBlockPos().up();
        BlockState state = world.getBlockState(ceiling);

        if (!PistonBlock.isMovable(state, world, ceiling, Direction.UP, false, Direction.UP) || this.getY() >= this.getTargetHeight() || this.getY() >= this.world.getTopY() - 1) {
            if (this.world.setBlockState(this.getBlockPos(), this.blockState, Block.NOTIFY_ALL) && !this.world.isClient) {
                ((ServerWorld)this.world).getChunkManager().threadedAnvilChunkStorage.sendToOtherNearbyPlayers(this, new BlockUpdateS2CPacket(this.getBlockPos(), this.world.getBlockState(this.getBlockPos())));
                this.discard();
            }
        }
        else {
            this.setVelocity(0.0D, 0.2D, 0.0D);
            this.move(MovementType.SELF, this.getVelocity());
                List<LivingEntity> list = world.getEntitiesByClass(LivingEntity.class, Box.from(Vec3d.ofCenter(ceiling)), EntityPredicates.VALID_ENTITY);
                if (!list.isEmpty())
                    list.forEach(entity -> {
                        if (entity instanceof ServerPlayerEntity && !world.isClient)
                            ModCriteria.RIDE_PROPELLER_BLOCK.trigger((ServerPlayerEntity) entity);
                        if (entity.getVelocity().getY() != 0) {
                            Vec3d vec3d = entity.getVelocity();
                            entity.setVelocity(vec3d.x, this.getVelocity().y, vec3d.z);
                        }
                    });
        }
    }

    public static void spawnFromBlock(World world, BlockPos blockPos, BlockState blockState) {
        PropellerBlockEntity entity = new PropellerBlockEntity(world, (double)blockPos.getX() + 0.5, blockPos.getY(), (double)blockPos.getZ() + 0.5, blockState);
        int i = world.getReceivedRedstonePower(blockPos);

        world.setBlockState(blockPos, blockState.getFluidState().getBlockState(), Block.NOTIFY_ALL);
        world.playSound(null, blockPos, Sounds.BLOCK_PROPELLER_BLOCK_RISE, SoundCategory.BLOCKS, 1.0F, 1.0F);
        world.emitGameEvent(null, GameEvent.BLOCK_CHANGE, blockPos);

        entity.setTargetHeight(blockPos.getY() + i);
        world.spawnEntity(entity);
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean canHit() {
        return !this.isRemoved();
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    @Override
    protected Entity.MoveEffect getMoveEffect() {
        return Entity.MoveEffect.NONE;
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this, Block.getRawIdFromState(this.getBlockState()));
    }

    public BlockState getBlockState() {
        return this.blockState;
    }

    private void setBlockState(BlockState blockState) {
        this.blockState = blockState;
    }
}