package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.ai.pathing.PathNodeType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

public class DinoRhinoEntity extends AnimalEntity implements Monster {
    public DinoRhinoEntity(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
        this.setPathfindingPenalty(PathNodeType.LAVA, 8.0f);
        this.experiencePoints = 10;
    }

    public static DefaultAttributeContainer.Builder createDinoRhinoAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.3f).add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 0.6f).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 5.0);
    }

    @SuppressWarnings("unused")
    public static boolean canDinoRhinoSpawn(EntityType<DinoRhinoEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return pos.getY() >= 63;
    }

    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new AnimalMateGoal(this, 1.0));
        this.goalSelector.add(2, new TemptGoal(this, 1.25, Ingredient.ofItems(ModBlocks.FIRE_TULIP), false));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.0D, false));
        this.goalSelector.add(4, new WanderAroundFarGoal(this, 0.7D));
        this.goalSelector.add(5, new LookAroundGoal(this));
        this.goalSelector.add(5, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.add(2, new RevengeGoal(this));
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return Sounds.ENTITY_DINO_RHINO_AMBIENT;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_DINO_RHINO_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_DINO_RHINO_DEATH;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.4f, 0.8f);
    }

    @Override
    public boolean canImmediatelyDespawn(double distanceSquared) {
        return !this.isPersistent();
    }

    public boolean isBreedingItem(ItemStack stack) {
        return stack.isOf(ModBlocks.FIRE_TULIP.asItem());
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        ActionResult actionResult = super.interactMob(player, hand);
        if (actionResult.isAccepted() && this.isBreedingItem(itemStack)) {
            this.setPersistent();
            this.world.playSoundFromEntity(null, this, Sounds.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0f, MathHelper.nextBetween(this.world.random, 0.8f, 1.2f));
        }
        return actionResult;
    }

    @Override
    public DinoRhinoEntity createChild(ServerWorld serverWorld, PassiveEntity passiveEntity) {
        DinoRhinoEntity dinoRhinoEntity = ModEntities.DINO_RHINO.create(serverWorld);
        if (dinoRhinoEntity != null) {
            dinoRhinoEntity.setPersistent();
        }
        return dinoRhinoEntity;
    }

    @Override
    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        if (world.getRandom().nextFloat() < 0.3f) {
            this.setBaby(true);
        }
        return super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
    }
}
