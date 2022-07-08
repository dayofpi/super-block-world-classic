package com.dayofpi.super_block_world.common.entities.passive;

import com.dayofpi.super_block_world.registry.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class KoopaTroopaEntity extends AbstractKoopa {
    public KoopaTroopaEntity(EntityType<? extends KoopaTroopaEntity> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("unused")
    public static boolean canKoopaSpawn(EntityType<? extends KoopaTroopaEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return !(world.getLightLevel(LightType.BLOCK, pos) > 0);
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
        if (source == DamageSource.LAVA)
            this.convertTo(ModEntities.DRY_BONES, false);
    }

    public static DefaultAttributeContainer.Builder createKoopaAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f).add(EntityAttributes.GENERIC_MAX_HEALTH, 16.0).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 4.0);
    }

    @Override
    protected boolean shouldWalk() {
        return super.shouldWalk() && this.onGround;
    }
}
