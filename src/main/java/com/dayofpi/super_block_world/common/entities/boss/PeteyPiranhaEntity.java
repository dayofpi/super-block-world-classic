package com.dayofpi.super_block_world.common.entities.boss;

import com.dayofpi.super_block_world.audio.ModMusic;
import com.dayofpi.super_block_world.registry.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.Item;
import net.minecraft.sound.MusicSound;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class PeteyPiranhaEntity extends ModBossEntity{
    public PeteyPiranhaEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public MusicSound getBossMusic() {
        return ModMusic.BOSS_1;
    }

    @Override
    protected @Nullable Item getRareItem() {
        return ModItems.MUSIC_DISC_FIRE_FACTORY;
    }
}
