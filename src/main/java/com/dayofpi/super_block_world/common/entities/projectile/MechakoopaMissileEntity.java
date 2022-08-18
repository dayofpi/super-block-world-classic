package com.dayofpi.super_block_world.common.entities.projectile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.blocks.BrickBlock;
import com.dayofpi.super_block_world.common.blocks.ReactiveBlock;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MechakoopaMissileEntity extends PersistentProjectileEntity {
    @Nullable
    LivingEntity target;

    public MechakoopaMissileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public MechakoopaMissileEntity(World world, LivingEntity owner, @Nullable LivingEntity target) {
        this(ModEntities.MECHAKOOPA_MISSILE, world);
        this.pickupType = PickupPermission.DISALLOWED;
        this.target = target;
        this.inGroundTime = 0;
        this.setOwner(owner);
        this.setPosition(owner.getX(), owner.getEyeY(), owner.getZ());
    }

    private void blow() {
        if (world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
            for (BlockPos blockPos : BlockPos.iterateOutwards(this.getBlockPos(), 1, 1, 1)) {
                BlockState blockState = world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (block instanceof BrickBlock)
                    world.breakBlock(blockPos, true);
                else if (block instanceof ReactiveBlock reactiveBlock)
                    reactiveBlock.react(world, blockPos, null);
            }
        }

        Box box = this.getBoundingBox().expand(0.7D, 0.5D, 0.7D);
        List<Entity> list = world.getOtherEntities(this, box, Entity::isLiving);
        if (!list.isEmpty()) {
            list.forEach(entity -> entity.damage(DamageSource.thrownProjectile(this, this.getOwner()), 8));
        }

        this.playSound(Sounds.ENTITY_BULLET_IMPACT, 1.0F, 1.0F);
        if (!world.isClient)
            ((ServerWorld) world).spawnParticles(ParticleTypes.EXPLOSION, this.getX(), this.getY(), this.getZ(), 1, 0.0D, 0.0D, 0.0D, 0.0D);
        this.discard();
    }

    @Override
    public void tick() {
        if (age >= 200) {
            blow();
            return;
        }

        if (this.world.isClient) {
            world.addParticle(ParticleTypes.SMOKE, this.getX() + (random.nextFloat() * 0.2), this.getY() + (random.nextFloat() * 0.2), this.getZ() + (random.nextFloat() * 0.2), 0.0D, 0.0D, 0.0D);
        }

        if (this.target != null && this.target.isDead())
            this.target = null;

        Vec3d velocity = this.getVelocity();
        Vec3d pos = this.getPos();
        Vec3d alteredPos = pos.add(velocity);


        if (this.target != null && this.getOwner() != null) {
            Vec3d distance = new Vec3d(target.getX() - this.getX(), (target.getEyeY() - 0.2) - this.getEyeY(), target.getZ() - this.getZ());
            this.setVelocity(this.getVelocity().add(distance.multiply(0.4D)));
            this.setVelocity(this.getVelocity().multiply(0.12D));
        } else if (!this.hasNoGravity()) {
            Vec3d vec3d4 = this.getVelocity();
            this.setVelocity(vec3d4.x, vec3d4.y - 0.05D, vec3d4.z);
        }

        this.setPitch(updateRotation(this.prevPitch, this.getPitch()));
        this.setYaw(updateRotation(this.prevYaw, this.getYaw()));
        this.setPosition(alteredPos);
        this.velocityDirty = true;
        this.tickCollision(pos, alteredPos);
    }

    private void tickCollision(Vec3d pos, Vec3d alteredPos) {
        HitResult hitResult = this.world.raycast(new RaycastContext(pos, alteredPos, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, this));

        if (hitResult.getType() != HitResult.Type.MISS) {
            alteredPos = hitResult.getPos();
        }

        while (!this.isRemoved()) {
            EntityHitResult entityHitResult = this.getEntityCollision(pos, alteredPos);
            if (entityHitResult != null) {
                hitResult = entityHitResult;
            }

            if (hitResult instanceof EntityHitResult) {
                Entity entity = ((EntityHitResult) hitResult).getEntity();
                Entity entity2 = this.getOwner();
                if (entity instanceof PlayerEntity && entity2 instanceof PlayerEntity && !((PlayerEntity) entity2).shouldDamagePlayer((PlayerEntity) entity)) {
                    hitResult = null;
                    entityHitResult = null;
                }
            }

            if (hitResult != null) {
                this.onCollision(hitResult);
                this.velocityDirty = true;
            }

            if (entityHitResult == null || this.getPierceLevel() <= 0) {
                break;
            }

            hitResult = null;
        }
        this.checkBlockCollision();
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        if (entity == this.getOwner() || !(getOwner() instanceof LivingEntity))
            return;
        blow();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        this.blow();
    }

    @Override
    protected ItemStack asItemStack() {
        return null;
    }
}
