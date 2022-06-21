package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.mob.KoopaShellEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import javax.annotation.Nullable;

@Environment(EnvType.CLIENT)
public class KoopaShellModel<T extends KoopaShellEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelLocation(T object) {
        return new Identifier(Main.MOD_ID, "geo/koopa_shell.geo.json");
    }

    @Override
    public Identifier getTextureLocation(T object) {
        return new Identifier(Main.MOD_ID, "textures/entity/shell/green.png");
    }

    @Override
    public Identifier getAnimationFileLocation(T animatable) {
        return new Identifier(Main.MOD_ID, "animations/koopa_shell.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        if (entity.isShaking()) {
            entity.bodyYaw += (float)(Math.cos((double) entity.age * 3.25) * Math.PI * (double)0.4f);
        }
    }
}
