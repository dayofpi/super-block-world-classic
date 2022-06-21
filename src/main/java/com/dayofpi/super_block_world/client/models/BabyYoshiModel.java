package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.passive.BabyYoshiEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class BabyYoshiModel<T extends BabyYoshiEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/baby_yoshi.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/baby_yoshi/green.png");
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return new Identifier(Main.MOD_ID, "animations/baby_yoshi.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        final IBone HEAD = this.getAnimationProcessor().getBone("head");
        EntityModelData modelData = (EntityModelData) customPredicate.getExtraData().get(0);
        HEAD.setRotationX(modelData.headPitch * ((float) Math.PI / 180F) * 0.5F);
        HEAD.setRotationY(modelData.netHeadYaw * ((float) Math.PI / 180F) * 0.5F);
        HEAD.setRotationZ(MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 0.5F * entity.limbDistance);

        final IBone BODY = this.getAnimationProcessor().getBone("body");
        BODY.setRotationY(MathHelper.cos(entity.limbAngle * 0.6662F) * 0.5F * entity.limbDistance);

        final IBone TAIL = this.getAnimationProcessor().getBone("tail");
        TAIL.setRotationY(MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * entity.limbDistance);


        final IBone RIGHT_ARM = this.getAnimationProcessor().getBone("right_arm");
        final IBone LEFT_ARM = this.getAnimationProcessor().getBone("left_arm");
        RIGHT_ARM.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance);
        LEFT_ARM.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance);
    }
}