package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.passive.MooMooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class MooMooModel<T extends MooMooEntity> extends AnimatedTickingGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/moo_moo.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return null;
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return null;
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone l_ear = this.getAnimationProcessor().getBone("l_ear");
        IBone r_ear = this.getAnimationProcessor().getBone("r_ear");
        IBone bell = this.getAnimationProcessor().getBone("bell");
        IBone b_r_leg = this.getAnimationProcessor().getBone("b_r_leg");
        IBone b_l_leg = this.getAnimationProcessor().getBone("b_l_leg");
        IBone f_r_leg = this.getAnimationProcessor().getBone("f_r_leg");
        IBone f_l_leg = this.getAnimationProcessor().getBone("f_l_leg");

        EntityModelData modelData = (EntityModelData) customPredicate.getExtraData().get(0);
        if (modelData.isChild) {
            head.setScaleX(1.5f);
            head.setScaleY(1.5f);
            head.setScaleZ(1.5f);
            head.setPivotZ(-4.0f);
        }

        head.setRotationX(modelData.headPitch * ((float) Math.PI / 180F) * 0.5F);
        head.setRotationY(modelData.netHeadYaw * ((float) Math.PI / 180F) * 0.5F);

        float progress = (float) (customPredicate.animationTick * 5F * 0.04F);
        l_ear.setRotationZ(0.2F + (MathHelper.cos(progress * 0.5F) * 0.05F));
        r_ear.setRotationZ(-0.2F - (MathHelper.cos(progress * 0.5F) * 0.05F));

        bell.setRotationZ(MathHelper.cos(customPredicate.getLimbSwing() * 0.6662f) * 1.4f * customPredicate.getLimbSwingAmount());
        b_r_leg.setRotationX(MathHelper.cos(customPredicate.getLimbSwing() * 0.6662f) * 1.4f * customPredicate.getLimbSwingAmount());
        b_l_leg.setRotationX(MathHelper.cos(customPredicate.getLimbSwing() * 0.6662f + (float) Math.PI) * 1.4f * customPredicate.getLimbSwingAmount());
        f_r_leg.setRotationX(MathHelper.cos(customPredicate.getLimbSwing() * 0.6662f + (float) Math.PI) * 1.4f * customPredicate.getLimbSwingAmount());
        f_l_leg.setRotationX(MathHelper.cos(customPredicate.getLimbSwing() * 0.6662f) * 1.4f * customPredicate.getLimbSwingAmount());
    }
}
