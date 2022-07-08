package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.entities.passive.YoshiEntity;
import com.dayofpi.super_block_world.registry.ModTags;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.RevengeGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvent;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.stream.Stream;

public class PiranhaPlantEntity extends HostileEntity {
    public final AnimationState bitingAnimationState = new AnimationState();
    private boolean songPlaying;

    public PiranhaPlantEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createPiranhaPlantAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 14).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D);
    }

    @SuppressWarnings("unused")
    public static boolean canPiranhaPlantSpawn(EntityType<? extends PiranhaPlantEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos blockPos, Random random) {
        return !(world.getLightLevel(LightType.BLOCK, blockPos) > 0) && isBlockValid(world, blockPos.down());
    }

    private static boolean isBlockValid(ServerWorldAccess world, BlockPos blockPos) {
        return world.getBlockState(blockPos).isIn(BlockTags.DIRT) || world.getBlockState(blockPos).isIn(ModTags.WARP_PIPES);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(2, new PiranhaPlantAttackGoal(this, 0.0D, false));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, FuzzyEntity.class, false));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, YoshiEntity.class, false));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, false));
        this.targetSelector.add(1, new RevengeGoal(this));
    }

    @Override
    protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
        return dimensions.height * 1.2F;
    }

    @Override
    public double squaredAttackRange(LivingEntity target) {
        return super.squaredAttackRange(target);
    }

    @Override
    public void takeKnockback(double strength, double x, double z) {
    }

    public float getPathfindingFavor(BlockPos pos, WorldView world) {
        return 0.0F;
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.playSound(Sounds.ENTITY_PIRANHA_PLANT_BITE, 1.0F, this.getSoundPitch());
        return super.tryAttack(target);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.isAttacking() ? null : Sounds.ENTITY_PIRANHA_PLANT_SLEEP;
    }

    @Override
    public int getMinAmbientSoundDelay() {
        return 120;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_PIRANHA_PLANT_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_PIRANHA_PLANT_DEATH;
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        if (isSongPlaying() && this.getAttacker() == null)
            return false;
        return super.canTarget(target) && this.distanceTo(target) < 4;
    }

    public boolean isSongPlaying() {
        return this.songPlaying;
    }

    @Override
    public void tick() {
        super.tick();
        Stream<BlockState> stream = world.getStatesInBox(this.getBoundingBox().expand(4));
        this.songPlaying = stream.anyMatch(blockState -> blockState.isOf(Blocks.JUKEBOX) && blockState.get(JukeboxBlock.HAS_RECORD));

        if (!world.isClient())
            return;
        this.tickAnimation();
        if (this.isSleeping() && random.nextInt(10) == 0) {
            world.addParticle(ParticleTypes.BUBBLE, this.getParticleX(1.0D), this.getY() + 1.0D, this.getParticleZ(1.0D), 0.0D, 0.05D, 0.0D);
        }
    }

    protected void tickAnimation() {
        if (this.isAttacking()) {
            this.bitingAnimationState.startIfNotRunning(this.age);
        }
        else this.bitingAnimationState.stop();
    }

    protected <P extends IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        if (this.isAttacking()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("bite", true));
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    static class PiranhaPlantAttackGoal extends MeleeAttackGoal {
        public PiranhaPlantAttackGoal(PiranhaPlantEntity mob, double speed, boolean pauseWhenMobIdle) {
            super(mob, speed, pauseWhenMobIdle);
        }

        @Override
        protected double getSquaredMaxAttackDistance(LivingEntity entity) {
            return this.mob.getWidth() * 3.5F * this.mob.getWidth() * 3.5F + entity.getWidth();
        }
    }
}
