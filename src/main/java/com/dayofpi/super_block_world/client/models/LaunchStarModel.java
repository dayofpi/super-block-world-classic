package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.misc.LaunchStarEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class LaunchStarModel<T extends LaunchStarEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T object) {
        return new Identifier(Main.MOD_ID, "geo/launch_star.geo.json");
    }

    @Override
    public Identifier getTextureResource(T object) {
        return null;
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return new Identifier(Main.MOD_ID, "animations/launch_star.animation.json");
    }
}
