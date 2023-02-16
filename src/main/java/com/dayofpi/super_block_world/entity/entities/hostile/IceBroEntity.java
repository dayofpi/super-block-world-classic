package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.entity.entities.projectile.IceballEntity;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.entity.ModEntities;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class IceBroEntity extends AbstractBro {
    public IceBroEntity(EntityType<? extends AbstractBro> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("unused")
    public static boolean canSpawn(EntityType<? extends AbstractBro> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        if (world.getBlockState(pos.down()).isOf(ModBlocks.FROSTED_VANILLATE))
            return !(world.getLightLevel(LightType.BLOCK, pos) > 0);
        return AbstractBro.canSpawn(type, world, spawnReason, pos, random);
    }

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        IceballEntity iceballEntity = new IceballEntity(ModEntities.ICEBALL, this, world);
        iceballEntity.setVelocity(this, this.getPitch(), this.getHeadYaw(), 0.0F, 1.0F, 0.0F);
        this.playSound(Sounds.ITEM_ICE_FLOWER, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(iceballEntity);
        this.swingHand(Hand.MAIN_HAND);
    }
}
