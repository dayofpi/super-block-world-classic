package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.UnagiEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class UnagiModel<T extends UnagiEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/unagi.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/unagi.png");
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return new Identifier(Main.MOD_ID, "animations/unagi.animation.json");
    }
}
