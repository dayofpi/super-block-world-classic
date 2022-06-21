package com.dayofpi.super_block_world.common.entities.abst;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.blocks.mechanics.ReactiveBlock;
import com.dayofpi.super_block_world.common.entities.mob.KoopaShellEntity;
import com.dayofpi.super_block_world.common.util.entity.Stompable;
import com.dayofpi.super_block_world.registry.more.MobDamageSource;
import com.dayofpi.super_block_world.registry.main.TagInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.List;

public abstract class AbstractShell extends AbstractEnemy implements IAnimatable, Stompable {
    private static final TrackedData<Boolean> HAS_MOB;
    private static final TrackedData<Boolean> SHAKING;
    private final AnimationFactory factory = new AnimationFactory(this);
    private int timer;

    static {
        HAS_MOB = DataTracker.registerData(KoopaShellEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
        SHAKING = DataTracker.registerData(KoopaShellEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(HAS_MOB, false);
        this.dataTracker.startTracking(SHAKING, false);
    }

    protected AbstractShell(EntityType<? extends AbstractEnemy> entityType, World world) {
        super(entityType, world);
        this.experiencePoints = 0;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.hasMob()) {
            this.leaveShell();
            return false;
        } else return super.damage(source, amount);
    }

    protected abstract void leaveShell();

    protected abstract Item getShellItem();

    public boolean isShaking() {
        return this.dataTracker.get(SHAKING);
    }

    public void setShaking(boolean shaking) {
        this.dataTracker.set(SHAKING, shaking);
    }

    @Override
    protected void dropInventory() {
        super.dropInventory();
        if (!this.hasMob()) {
            this.dropItem(this.getShellItem());
        }
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundInit.ENTITY_SHELL_BREAK;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(this.getStepSound(), 0.4f, this.getSoundPitch());
    }

    SoundEvent getStepSound() {
        return SoundInit.ENTITY_SHELL_SPIN;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.hasMob() && !this.world.isClient) {
            ++timer;
            if (timer >= 100) {
                this.setShaking(true);
                this.setVelocity(this.getVelocity().multiply(0.4));
            }
            if (timer == 150) {
                this.leaveShell();
            }
        }

        if (this.isAlive() && this.getVelocity().horizontalLengthSquared() > 0.02D) {
            List<Entity> damageable = world.getOtherEntities(this, this.getBoundingBox().contract(0.D, 0.2D, 0.D).expand(0.15D, 0.0D, 0.15D), Entity::isLiving);
            damageable.forEach(entity -> entity.damage(MobDamageSource.shell(this), 4));

            for (BlockPos blockPos : BlockPos.iterateOutwards(this.getBlockPos(), 1, 0, 1)) {
                BlockState state = world.getBlockState(blockPos);
                if (this.collidesWithStateAtPos(blockPos, state)) {
                    if (state.isSolidBlock(world, blockPos)) {
                        this.setVelocity(this.getVelocity().multiply(-1, 1, -1));
                    }
                }
                if (state.isIn(TagInit.BRICKS)) {
                    world.breakBlock(blockPos, true);
                } else if (state.getBlock() instanceof ReactiveBlock reactiveBlock) {
                    reactiveBlock.activate(state, world, blockPos);
                }
            }
        }

        final double maxSpeed = 0.05D;
        double horizontalMultiplier = 2D;
        Vec3d vec3d = this.getVelocity();
        if (this.getVelocity().horizontalLengthSquared() < maxSpeed)
            this.setVelocity(vec3d.multiply(horizontalMultiplier, 1, horizontalMultiplier));
    }

    @Nullable
    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(this.getShellItem());
    }

    public boolean hasMob() {
        return this.dataTracker.get(HAS_MOB);
    }

    public void setHasMob(boolean hasMob) {
        this.dataTracker.set(HAS_MOB, hasMob);
    }

    private <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (event.isMoving() || !(event.getLimbSwingAmount() > -0.15F && event.getLimbSwingAmount() < 0.15F)) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("spin", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return AbstractEnemy.createEnemyAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 1.0D);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public void stompResult(LivingEntity livingEntity) {
        if (this.getVelocity().horizontalLengthSquared() > 0.0D) {
            this.setVelocity(0.0D, this.getVelocity().y, 0.0D);
        } else this.pushAwayFrom(livingEntity);
    }
}
