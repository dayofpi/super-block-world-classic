package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.MechakoopaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class MechakoopaModel<T extends MechakoopaEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T object) {
        return new Identifier(Main.MOD_ID, "geo/mechakoopa.geo.json");
    }

    @Override
    public Identifier getTextureResource(T object) {
        return null;
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return null;
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone body = this.getAnimationProcessor().getBone("body");
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone jaw = this.getAnimationProcessor().getBone("jaw");

        if (entity.isInSittingPose()) {
            body.setPositionY(-3.0F);
            head.setPositionY(-3.0F);
            head.setPositionZ(-1.0F);
            jaw.setRotationX(0.0F);
        } else {
            EntityModelData modelData = (EntityModelData) customPredicate.getExtraData().get(0);
            body.setPositionY(0.0F);
            head.setPositionY(0.0F);
            head.setPositionZ(0.0F);
            head.setRotationY(modelData.netHeadYaw * ((float) Math.PI / 180F));
            if (entity.isAttacking() && entity.getPower() > 0) {
                head.setPositionZ(-2.0F);
                head.setRotationX(0.9F);
                jaw.setRotationX(-1.5F);
            } else {
                head.setRotationX(modelData.headPitch * ((float) Math.PI / 180F));
                jaw.setRotationX(MathHelper.clamp(MathHelper.cos(entity.limbAngle * 0.6662F) * entity.limbDistance - 0.4F, -1.0F, 0.0F));
            }
        }

        IBone r_leg = this.getAnimationProcessor().getBone("r_leg");
        IBone l_leg = this.getAnimationProcessor().getBone("l_leg");

        r_leg.setPositionY(MathHelper.cos(entity.limbAngle * 0.7F) * 1.4F * entity.limbDistance);
        l_leg.setPositionY(MathHelper.cos(entity.limbAngle * 0.7F + 3.1415927F) * 1.4F * entity.limbDistance);
    }
}
