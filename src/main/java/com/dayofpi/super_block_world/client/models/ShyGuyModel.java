package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.ShyGuyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class ShyGuyModel<T extends ShyGuyEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/shy_guy.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/shy_guy.png");
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return null;
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        final IBone BODY = this.getAnimationProcessor().getBone("body");
        BODY.setRotationZ(MathHelper.cos(entity.limbAngle * 0.6662F) * 0.5F * entity.limbDistance);

        final IBone RIGHT_FOOT = this.getAnimationProcessor().getBone("right_foot");
        final IBone LEFT_FOOT = this.getAnimationProcessor().getBone("left_foot");

        RIGHT_FOOT.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance);
        LEFT_FOOT.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance);


        final IBone RIGHT_ARM = this.getAnimationProcessor().getBone("right_arm");
        final IBone LEFT_ARM = this.getAnimationProcessor().getBone("left_arm");
        if (entity.isAttacking()) {
            RIGHT_ARM.setRotationZ(1.5F);
            LEFT_ARM.setRotationZ(-1.5F);
            return;
        }

        RIGHT_ARM.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance);
        LEFT_ARM.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance);
    }
}