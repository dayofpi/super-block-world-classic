package com.dayofpi.sbw_main.entity.types;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.item.registry.ModItems;
import com.dayofpi.sbw_main.misc.ModDamageSource;
import com.dayofpi.sbw_main.SoundList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public abstract class AbstractShell extends Entity {

    public AbstractShell(EntityType<?> type, World world) {
        super(type, world);
        this.inanimate = true;
    }

    public void tick() {
        this.move(MovementType.SELF, this.getVelocity());

        if (!this.hasNoGravity()) {
            double fallSpeed = -0.4D;
            if (this.isSubmergedInWater()) {
                fallSpeed = -0.2;
            }
            this.setVelocity(this.getVelocity().add(0.0D, fallSpeed, 0.0D));
        }
        BlockPos blockPos = this.getBlockPos();

        boolean checkBlockCollision = world.isDirectionSolid(blockPos.offset(this.getMovementDirection().getOpposite()), this, this.getMovementDirection().getOpposite());
        boolean checkBlockCollisionOpposite = world.isDirectionSolid(blockPos.offset(this.getMovementDirection()), this, this.getMovementDirection());

        if (checkBlockCollision || checkBlockCollisionOpposite) {
            if (world.getBlockState(blockPos.east()).isSolidBlock(world, blockPos) || world.getBlockState(blockPos.west()).isSolidBlock(world, blockPos)) {
                this.setVelocity(this.getVelocity().multiply(-1.0F, 1.0F, 1.0F));
            } else if (world.getBlockState(blockPos.north()).isSolidBlock(world, blockPos) || world.getBlockState(blockPos.south()).isSolidBlock(world, blockPos)) {
                this.setVelocity(this.getVelocity().multiply(1.0F, 1.0F, -1.0F));
            }
        }

        List<Entity> list = this.world.getOtherEntities(this, this.getBoundingBox().expand(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D), EntityPredicates.canBePushedBy(this));
        if (!list.isEmpty() && !this.isSpinning()) {
            for (Entity entity : list) {
                this.pushAwayFrom(entity);
                this.setVelocity(this.getVelocity().multiply(4.5, 1.0D, 4.5D));
                world.playSound(null, blockPos, SoundList.buzzyDrop, this.getSoundCategory(), 1.0F, 1.0F);
            }
        } else if (!list.isEmpty() && this.isSpinning()) {
            for (Entity entity : list) {
                entity.damage(ModDamageSource.SHELL, 5);
                if (entity instanceof LivingEntity livingEntity && livingEntity.canTakeDamage()) {
                    livingEntity.takeKnockback(0.5, this.getX(), this.getZ());
                }
            }
        }
        super.tick();
    }

    public boolean isSpinning() {
        return this.getVelocity().x != 0 || this.getVelocity().z != 0;
    }

    protected Entity.MoveEffect getMoveEffect() {
        return MoveEffect.EVENTS;
    }

    public boolean shouldSpawnSprintingParticles() {
        return this.isSpinning();
    }

    public boolean damage(DamageSource source, float amount) {
        if (this.isInvulnerableTo(source)) {
            return false;
        } else if (!this.world.isClient && !this.isRemoved()) {
            this.emitGameEvent(GameEvent.ENTITY_DAMAGED, source.getAttacker());
            boolean bl = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity) source.getAttacker()).getAbilities().creativeMode;
            if (!bl && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                ItemStack itemStack = new ItemStack(this.asItem());
                this.dropStack(itemStack);
                world.playSound(null, this.getBlockPos(), SoundList.buzzyDrop, this.getSoundCategory(), 1.0F, 1.0F);
            }
            this.discard();
            return true;
        } else return true;
    }

    public boolean collides() {
        return !this.isRemoved();
    }

    public boolean isPushable() {
        return true;
    }

    public boolean collidesWith(Entity other) {
        return canCollide(this, other);
    }

    public static boolean canCollide(Entity entity, Entity other) {
        return (other.isCollidable() || other.isPushable()) && !entity.isConnectedThroughVehicle(other);
    }

    public ItemStack getPickBlockStack() {
        return new ItemStack(this.asItem());
    }

    public Item asItem() {
        return ModItems.BUZZY_SHELL;
    }

    private void spawnBreakParticles() {
        if (this.world instanceof ServerWorld) {
            ((ServerWorld) this.world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, ModBlocks.GLOOMSTONE.getDefaultState()), this.getX(), this.getBodyY(0.6666666666666666D), this.getZ(), 10, this.getWidth() / 4.0F, this.getHeight() / 4.0F, this.getWidth() / 4.0F, 0.05D);
        }

    }
}
