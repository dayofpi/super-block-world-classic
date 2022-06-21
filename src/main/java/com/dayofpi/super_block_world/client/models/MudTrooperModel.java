package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.MudTrooperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class MudTrooperModel<T extends MudTrooperEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/mud_trooper.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/mud_trooper.png");
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return null;
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        final IBone ROOT = this.getAnimationProcessor().getBone("root");
        ROOT.setRotationZ(MathHelper.cos(entity.limbAngle * 0.6662F) * entity.limbDistance * 0.5F);

        float progress = (float) (customPredicate.animationTick * 2F * 0.01F);
        final IBone RIGHT_ARM = this.getAnimationProcessor().getBone("right_arm");
        final IBone LEFT_ARM = this.getAnimationProcessor().getBone("left_arm");
        RIGHT_ARM.setRotationX(MathHelper.cos(progress) * 0.2F);
        LEFT_ARM.setRotationX(MathHelper.cos(progress) * 0.2F);
        RIGHT_ARM.setRotationY(MathHelper.cos(entity.limbAngle * 0.6662F) * entity.limbDistance);
        LEFT_ARM.setRotationY(MathHelper.cos(entity.limbAngle * 0.6662F) * entity.limbDistance);
        RIGHT_ARM.setRotationZ((MathHelper.cos(progress) * 0.2F) + 0.5F);
        LEFT_ARM.setRotationZ((MathHelper.cos(progress) * -0.2F) - 0.5F);

        final IBone RIGHT_FOOT = this.getAnimationProcessor().getBone("right_foot");
        final IBone LEFT_FOOT = this.getAnimationProcessor().getBone("left_foot");
        RIGHT_FOOT.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance);
        LEFT_FOOT.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance);
        RIGHT_FOOT.setRotationY(-0.45F);
        LEFT_FOOT.setRotationY(0.45F);
    }
}