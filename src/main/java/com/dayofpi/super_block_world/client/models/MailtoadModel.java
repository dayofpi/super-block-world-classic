package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.passive.MailtoadEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class MailtoadModel<T extends MailtoadEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/toad.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return null;
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return new Identifier(Main.MOD_ID, "animations/toad.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone ROOT = this.getAnimationProcessor().getBone("root");
        IBone NECK = this.getAnimationProcessor().getBone("neck");
        IBone LEFT_TWINTAIL = this.getAnimationProcessor().getBone("left_twintail");
        IBone RIGHT_TWINTAIL = this.getAnimationProcessor().getBone("right_twintail");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);

        if (entity.isScared() && !customPredicate.isMoving() || entity.isFrozen()) {
            entity.bodyYaw += (float) (Math.cos((double) entity.age * 3.25) * Math.PI * (double) 0.4f);
        }

        NECK.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        NECK.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));

        LEFT_TWINTAIL.setHidden(!entity.isToadette());
        RIGHT_TWINTAIL.setHidden(!entity.isToadette());


        if (entity.isBaby()) {
            ROOT.setScaleX(0.5F);
            ROOT.setScaleY(0.5F);
            ROOT.setScaleZ(0.5F);
            NECK.setScaleX(2.0F);
            NECK.setScaleY(2.0F);
            NECK.setScaleZ(2.0F);
        }
    }
}
