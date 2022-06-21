package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.FakeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class FakeBlockModel<T extends FakeBlockEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/fake_block.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/fake_block.png");
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return new Identifier(Main.MOD_ID, "animations/fake_block.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        if (customPredicate == null)
            return;
        IBone tail = this.getAnimationProcessor().getBone("tail");
        float progress = (float) (customPredicate.animationTick * 2F * 0.01F);
        tail.setRotationX((MathHelper.cos(entity.limbAngle + progress) * 0.2F * entity.limbDistance));
    }
}
