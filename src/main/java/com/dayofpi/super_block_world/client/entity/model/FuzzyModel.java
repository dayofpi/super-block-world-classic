package com.dayofpi.super_block_world.client.entity.model;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.entity.mob.FuzzyEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class FuzzyModel<T extends FuzzyEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelLocation(T object) {
        return new Identifier(Main.MOD_ID, "geo/fuzzy.geo.json");
    }

    @Override
    public Identifier getTextureLocation(T object) {
        return new Identifier(Main.MOD_ID, "textures/entity/fuzzy.png");
    }

    @Override
    public Identifier getAnimationFileLocation(T animatable) {
        return new Identifier(Main.MOD_ID, "animations/fuzzy.animation.json");
    }
}
