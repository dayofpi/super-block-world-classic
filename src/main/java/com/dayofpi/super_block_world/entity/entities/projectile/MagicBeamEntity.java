package com.dayofpi.super_block_world.entity.entities.projectile;


import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.hostile.FakeBlockEntity;
import com.dayofpi.super_block_world.entity.entities.hostile.MudTrooperEntity;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.entity.ModEntities;
import com.dayofpi.super_block_world.ModParticles;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicBeamEntity extends ThrownEntity {
    public MagicBeamEntity(EntityType<? extends ThrownEntity> entityType, World world) {
        super(entityType, world);
    }

    public MagicBeamEntity(World world, LivingEntity owner) {
        super(ModEntities.MAGIC_BEAM, owner, world);
    }

    public void tick() {
        super.tick();
        if (this.world.isClient() && this.random.nextInt(3) == 0) {
            for (int i = 0; i < 3; ++i) {
                world.addParticle(ModParticles.MAGIC, this.getX() + (random.nextFloat() * 0.4), this.getY() + (random.nextFloat() * 0.4), this.getZ() + (random.nextFloat() * 0.4), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        this.discard();
        Entity entity = entityHitResult.getEntity();
        this.playSound(Sounds.ENTITY_PROJECTILE_HIT, 1.0F, 1.0F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 5);
        if (entity instanceof CreeperEntity)
            ((CreeperEntity) entity).ignite();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.discard();
        BlockPos blockPos = blockHitResult.getBlockPos();
        BlockState blockState = this.world.getBlockState(blockPos);
        blockState.onProjectileHit(this.world, blockState, blockHitResult, this);
        if (!world.isClient()) {
            if (blockState.isOf(ModBlocks.TOADSTONE_BRICKS)) {
                FakeBlockEntity fakeBlockEntity = ModEntities.FAKE_BLOCK.create(this.world);
                if (fakeBlockEntity == null)
                    return;
                fakeBlockEntity.updatePosition(blockPos.getX() + 0.5, blockPos.getY(), blockPos.getZ() + 0.5);
                this.world.removeBlock(blockPos, false);
                this.world.spawnEntity(fakeBlockEntity);
            }
            else if (blockState.isIn(BlockTags.DIRT) && HostileEntity.isSpawnDark((ServerWorld)this.world, blockPos.up(), this.world.random)) {
                MudTrooperEntity mudTrooperEntity = ModEntities.MUD_TROOPER.create(this.world);
                if (mudTrooperEntity == null)
                    return;
                mudTrooperEntity.updatePosition(blockPos.getX() + 0.5, blockPos.getY() + 1, blockPos.getZ() + 0.5);
                this.world.spawnEntity(mudTrooperEntity);
            }
        }
    }

    @Override
    protected float getGravity() {
        return 0.0F;
    }

    @Override
    protected void initDataTracker() {

    }
}
