package com.dayofpi.super_block_world.entity.entities.projectile;


import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.entity.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class GoopEntity extends ThrownEntity {
    public GoopEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    public GoopEntity(World world, LivingEntity owner) {
        this(ModEntities.GOOP, owner, world);
    }

    protected GoopEntity(EntityType<? extends ThrownEntity> type, LivingEntity owner, World world) {
        super(type, owner.getX(), owner.getEyeY() - (double)0.1f, owner.getZ(), world);
        this.setOwner(owner);
    }

    @Override
    public void tick() {
        super.tick();
        Vec3d velocity = this.getVelocity();
        this.setVelocity(velocity.multiply(0.8F));
        if (!world.isClient) {
            ServerWorld serverWorld = (ServerWorld)world;
            serverWorld.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, this.getBlockState()), this.getBlockPos().getX(), this.getBlockPos().getY(), this.getBlockPos().getZ(), 1, 0.0, 0.0, 0.0, 1.0);
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        this.discard();
        Entity entity = entityHitResult.getEntity();
        this.playSound(Sounds.ENTITY_PROJECTILE_HIT, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 4);
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.discard();
        this.playSound(SoundEvents.BLOCK_SLIME_BLOCK_PLACE, 1.0F, 1.2F);
        if (!world.isClient()) {
            BlockPos blockPos = blockHitResult.getBlockPos();
            for (BlockPos pos : BlockPos.iterateOutwards(blockPos, 1, 1, 1)) {
                BlockState blockState = this.world.getBlockState(pos);
                blockState.onProjectileHit(this.world, blockState, blockHitResult, this);

                if (blockState.isIn(BlockTags.MOSS_REPLACEABLE)) {
                    world.setBlockState(pos, this.getBlockState());
                }
                if (pos == blockPos || this.random.nextFloat() > 0.3F) {
                    if (GoopEntity.canPlaceAt(this.getWorld(), pos.up())) {
                        world.setBlockState(pos.up(), this.getLayerState());
                    }
                    else if (blockState.isOf(this.getLayerState().getBlock()) && blockState.get(Properties.LAYERS) < 8) {
                        world.setBlockState(pos, blockState.with(Properties.LAYERS, blockState.get(Properties.LAYERS) + 1));
                    }
                }
                if (!world.isClient) {
                    ServerWorld serverWorld = (ServerWorld)world;
                    for (int i = 0; i < 3; ++i) {
                        serverWorld.spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, this.getBlockState()), (double)pos.getX() + world.random.nextDouble(), pos.getY() + 1, (double)pos.getZ() + world.random.nextDouble(), 1, 0.0, 0.0, 0.0, 1.0);
                    }
                }
            }
        }
    }

    protected BlockState getLayerState() {
        return ModBlocks.GOOP.getDefaultState();
    }

    public BlockState getBlockState() {
        return ModBlocks.GOOP_BLOCK.getDefaultState();
    }

    public static boolean canPlaceAt(WorldView world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.down());
        if (!world.getBlockState(pos).isAir())
            return false;
        if (blockState.isIn(BlockTags.SNOW_LAYER_CANNOT_SURVIVE_ON)) {
            return false;
        }
        if (blockState.isIn(BlockTags.SNOW_LAYER_CAN_SURVIVE_ON)) {
            return true;
        }
        return Block.isFaceFullSquare(blockState.getCollisionShape(world, pos), Direction.UP) || blockState.isOf(ModBlocks.GOOP) && blockState.get(SnowBlock.LAYERS) == 8;
    }

    @Override
    protected float getGravity() {
        return 0.08F;
    }

    @Override
    protected void initDataTracker() {

    }
}
