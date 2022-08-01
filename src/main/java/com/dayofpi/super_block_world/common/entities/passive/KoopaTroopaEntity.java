package com.dayofpi.super_block_world.common.entities.passive;

import com.dayofpi.super_block_world.common.entities.misc.KoopaShellEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class KoopaTroopaEntity extends AbstractKoopa {
    public KoopaTroopaEntity(EntityType<? extends KoopaTroopaEntity> entityType, World world) {
        super(entityType, world);
    }

    private static boolean isThereNoLight(WorldAccess world, BlockPos pos) {
        return !(world.getLightLevel(LightType.BLOCK, pos) > 0);
    }

    @SuppressWarnings("unused")
    public static boolean canKoopaSpawn(EntityType<? extends KoopaTroopaEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (world.getBiome(pos).isIn(ModTags.SURFACE_KOOPA_SPAWN))
            return isThereNoLight(world, pos);
        else return isThereNoLight(world, pos) && world.getBlockState(pos.down()).isIn(ModTags.VANILLATE);
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

    @Override
    public void onStomped() {
        KoopaShellEntity koopaShell = ModEntities.KOOPA_SHELL.create(this.world);
        if (koopaShell != null) {
            if (this.isSaddled()) {
                this.dropItem(Items.SADDLE);
            }
            koopaShell.setVariant(this.getKoopaColor());
            koopaShell.setOccupied(true);
            koopaShell.refreshPositionAndAngles(this.getX(), this.getY(), this.getZ(), this.getYaw(), this.getPitch());
            koopaShell.setAiDisabled(this.isAiDisabled());
            if (this.hasCustomName()) {
                koopaShell.setCustomName(this.getCustomName());
                koopaShell.setCustomNameVisible(this.isCustomNameVisible());
            }
            world.spawnEntity(koopaShell);
            this.discard();
        }
    }
}
