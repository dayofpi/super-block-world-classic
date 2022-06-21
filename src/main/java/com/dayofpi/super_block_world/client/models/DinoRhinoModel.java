package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.DinoRhinoEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class DinoRhinoModel<T extends DinoRhinoEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/dino_rhino.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return null;
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone l_ear = this.getAnimationProcessor().getBone("l_ear");
        IBone r_ear = this.getAnimationProcessor().getBone("r_ear");
        IBone b_r_leg = this.getAnimationProcessor().getBone("r_b_leg");
        IBone b_l_leg = this.getAnimationProcessor().getBone("l_b_leg");
        IBone f_r_leg = this.getAnimationProcessor().getBone("r_f_leg");
        IBone f_l_leg = this.getAnimationProcessor().getBone("l_f_leg");

        EntityModelData modelData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
        if (modelData.isChild) {
            head.setScaleX(2.0F);
            head.setScaleY(2.0F);
            head.setScaleZ(2.0F);
        }
        head.setRotationX(0.5F + modelData.headPitch * ((float) Math.PI / 180F) * 0.5F);
        head.setRotationY(modelData.netHeadYaw * ((float) Math.PI / 180F) * 0.5F);

        float progress = (float) (customPredicate.animationTick * 5F * 0.04F);
        l_ear.setRotationZ(-0.4F - (MathHelper.cos(progress * 0.5F) * 0.05F));
        r_ear.setRotationZ(0.4F + (MathHelper.cos(progress * 0.5F) * 0.05F));

        b_r_leg.setRotationX(MathHelper.cos(customPredicate.getLimbSwing() * 0.6662f) * 1.4f * customPredicate.getLimbSwingAmount());
        b_l_leg.setRotationX(MathHelper.cos(customPredicate.getLimbSwing() * 0.6662f + (float) Math.PI) * 1.4f * customPredicate.getLimbSwingAmount());
        f_r_leg.setRotationX(MathHelper.cos(customPredicate.getLimbSwing() * 0.6662f + (float) Math.PI) * 1.4f * customPredicate.getLimbSwingAmount());
        f_l_leg.setRotationX(MathHelper.cos(customPredicate.getLimbSwing() * 0.6662f) * 1.4f * customPredicate.getLimbSwingAmount());
    }
}
