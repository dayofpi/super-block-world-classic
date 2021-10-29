package com.dayofpi.sbw_main.entity.types.mobs;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.entity.goals.HammerBroJumpGoal;
import com.dayofpi.sbw_main.entity.goals.HammerAttackGoal;
import com.dayofpi.sbw_main.entity.types.projectiles.HammerEntity;
import com.dayofpi.sbw_main.item.registry.ModItems;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.*;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class HammerBroEntity extends EnemyEntity implements RangedAttackMob {
    public HammerBroEntity(EntityType<? extends EnemyEntity> entityType, World world) {
        super(entityType, world);
    }

    public static boolean canSpawn(EntityType<HammerBroEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        boolean allowedBlocks = world.getBlockState(pos.down()).isIn(BlockTags.SAND) || world.getBlockState(pos.down()).isOf(ModBlocks.TOADSTONE);
        return allowedBlocks && world.getBiome(pos).getTemperature() >= 0.9 && !(world.getLightLevel(LightType.BLOCK, pos) > 0) || isSpawnDark((ServerWorldAccess) world, pos, random) && !world.isSkyVisible(pos);
    }

    public static DefaultAttributeContainer.Builder createAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 12)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 20)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2D);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(1, new HammerBroJumpGoal(this, 0.6F));
        this.goalSelector.add(2, new HammerAttackGoal(this, 1.0F, 30, 10.0F));
        this.goalSelector.add(5, new WanderAroundFarGoal(this, 0.8D));
        this.goalSelector.add(6, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(6, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this ));
        this.targetSelector.add(2, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        HammerEntity hammerEntity = new HammerEntity(world, this);
        double d = target.getX() - this.getX();
        double e = target.getBodyY(0.3333333333333333D) - hammerEntity.getY();
        double f = target.getZ() - this.getZ();
        double g = Math.sqrt(d * d + f * f);
        hammerEntity.setVelocity(d, e + g * 0.20000000298023224D, f, 1.5F, (float)(14 - this.world.getDifficulty().getId() * 4));
        this.playSound(ModSounds.ITEM_PROJECTILE_THROW, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(hammerEntity);
    }

    @Nullable
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable NbtCompound entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        this.initEquipment(difficulty);
        this.updateEnchantments(difficulty);

        return entityData;
    }

    protected void initEquipment(LocalDifficulty difficulty) {
        super.initEquipment(difficulty);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModItems.HAMMER));
    }
}
