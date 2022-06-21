package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.passive.GladGoombaEntity;
import com.dayofpi.super_block_world.common.entities.passive.GladParagoombaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class GladGoombaModel<T extends GladGoombaEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/goomba.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/goomba/glad_goomba.png");
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return new Identifier(Main.MOD_ID, "animations/goomba.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        final IBone ROOT = this.getAnimationProcessor().getBone("root");
        final IBone HEAD = this.getAnimationProcessor().getBone("head");
        final IBone LEFT_WING = this.getAnimationProcessor().getBone("left_wing");
        final IBone RIGHT_WING = this.getAnimationProcessor().getBone("right_wing");

        EntityModelData modelData = (EntityModelData) customPredicate.getExtraData().get(0);

        if (entity.getHealth() > 0) {
            HEAD.setRotationX(modelData.headPitch * ((float) Math.PI / 180F));
            HEAD.setRotationY(modelData.netHeadYaw * ((float) Math.PI / 180F));
        } else {
            HEAD.setRotationX(0);
            HEAD.setRotationY(0);
        }

        float progress = (float) (customPredicate.animationTick * 5F * 0.017453292F);
        RIGHT_WING.setRotationX(MathHelper.cos(progress) * 0.2F - 0.1F);
        LEFT_WING.setRotationX(MathHelper.cos(progress) * 0.2F - 0.1F);
        LEFT_WING.setHidden(!(entity instanceof GladParagoombaEntity));
        RIGHT_WING.setHidden(!(entity instanceof GladParagoombaEntity));

        if (entity.isBaby()) {
            ROOT.setScaleX(0.5F);
            ROOT.setScaleY(0.5F);
            ROOT.setScaleZ(0.5F);
            HEAD.setScaleX(1.3F);
            HEAD.setScaleY(1.3F);
            HEAD.setScaleZ(1.3F);
        }
    }
}
