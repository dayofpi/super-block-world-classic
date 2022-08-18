package com.dayofpi.super_block_world.common.entities.hostile;

import com.dayofpi.super_block_world.audio.Sounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class DarkGoombaEntity extends GoombaEntity {
    public DarkGoombaEntity(EntityType<? extends DarkGoombaEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected @Nullable SoundEvent getAmbientSound() {
        if (this.getSize() == 1)
            return Sounds.ENTITY_DARK_GOOMBA_AMBIENT;
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return Sounds.ENTITY_DARK_GOOMBA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return Sounds.ENTITY_DARK_GOOMBA_DEATH;
    }

    @Override
    protected boolean isDark() {
        return true;
    }
}
