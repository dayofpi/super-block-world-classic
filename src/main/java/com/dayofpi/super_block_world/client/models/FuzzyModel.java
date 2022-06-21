package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.FuzzyEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FuzzyModel<T extends FuzzyEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/fuzzy.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/fuzzy.png");
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return new Identifier(Main.MOD_ID, "animations/fuzzy.animation.json");
    }
}
