package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.CheepCheepEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class CheepCheepModel<T extends CheepCheepEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/cheep_cheep.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        if (entity.isSnowy())
            return new Identifier(Main.MOD_ID, "textures/entity/cheep_cheep_snow.png");
        return new Identifier(Main.MOD_ID, "textures/entity/cheep_cheep.png");
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return null;
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        final IBone ROOT = this.getAnimationProcessor().getBone("root");
        final IBone MOUTH = this.getAnimationProcessor().getBone("mouth");
        final IBone RIGHT_WING = this.getAnimationProcessor().getBone("r_wing");
        final IBone LEFT_WING = this.getAnimationProcessor().getBone("l_wing");
        final IBone TAIL = this.getAnimationProcessor().getBone("tail");
        float progress = (float) (customPredicate.animationTick * 5F * 0.07F);

        ROOT.setRotationX(entity.bodyAngle);
        RIGHT_WING.setRotationY(MathHelper.cos(entity.limbAngle * 0.5F) * 1.4F * entity.limbDistance + 0.5F);
        LEFT_WING.setRotationY(MathHelper.cos(entity.limbAngle * 0.5F + 3.1415927F) * 1.4F * entity.limbDistance - 0.5F);
        TAIL.setRotationY(MathHelper.cos(entity.limbAngle * 0.6F) * 1.6F * entity.limbDistance);
        MOUTH.setScaleX(1 + (MathHelper.cos(progress) * 0.07F));
        MOUTH.setScaleY(1 + (MathHelper.cos(progress) * 0.07F));
    }
}
