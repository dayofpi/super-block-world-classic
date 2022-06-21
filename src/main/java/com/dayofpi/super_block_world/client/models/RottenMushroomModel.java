package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.RottenMushroomEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class RottenMushroomModel<T extends RottenMushroomEntity> extends AnimatedGeoModel<T> {

    @Override
    public Identifier getModelResource(T object) {
        return new Identifier(Main.MOD_ID, "geo/rotten_mushroom.geo.json");
    }

    @Override
    public Identifier getTextureResource(T object) {
        return new Identifier(Main.MOD_ID, "textures/entity/rotten_mushroom.png");
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return new Identifier(Main.MOD_ID, "animations/rotten_mushroom.animation.json");
    }
}