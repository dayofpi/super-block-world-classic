package com.dayofpi.super_block_world.common.entities.misc;

import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.registry.ModParticles;
import com.google.common.collect.ImmutableList;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.world.World;

public class StarBitEntity extends ThrownEntity {
    private static final ImmutableList<Item> STAR_BITS = ImmutableList.of(ModItems.PURPLE_STAR_BIT, ModItems.BLUE_STAR_BIT, ModItems.GREEN_STAR_BIT, ModItems.YELLOW_STAR_BIT);

    public StarBitEntity(World world, double x, double y, double z) {
        super(ModEntities.STAR_BIT, x, y, z, world);
    }

    public StarBitEntity(EntityType<StarBitEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        this.playSound(SoundEvents.BLOCK_SMALL_AMETHYST_BUD_BREAK, 3.0F, 1.0F);
        this.dropItem(STAR_BITS.get(random.nextInt(STAR_BITS.size())));
        this.discard();
    }

    public void tick() {
        super.tick();
        world.addParticle(ModParticles.STAR_BIT, this.getX() + (random.nextFloat() * 0.2), this.getY() + (random.nextFloat() * 0.2), this.getZ() + (random.nextFloat() * 0.2), 0.0D, 0.0D, 0.0D);
    }
}
