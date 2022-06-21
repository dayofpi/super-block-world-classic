package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.passive.YoshiEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class YoshiModel<T extends YoshiEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/yoshi.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/yoshi/green.png");
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return new Identifier(Main.MOD_ID, "animations/yoshi.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone tongue = this.getAnimationProcessor().getBone("tongue");
        tongue.setHidden(!entity.isTongueOut());

        IBone cheeks = this.getAnimationProcessor().getBone("cheeks");
        if (!entity.getStoredEntity().isEmpty()) {
            cheeks.setScaleX(1.3F);
            cheeks.setScaleY(1.3F);
            cheeks.setScaleZ(1.3F);
        }

        IBone head = this.getAnimationProcessor().getBone("head");
        EntityModelData modelData = (EntityModelData) customPredicate.getExtraData().get(0);
        head.setRotationX(modelData.headPitch * ((float) Math.PI / 180F) * 0.5F);
        head.setRotationY(modelData.netHeadYaw * ((float) Math.PI / 180F) * 0.5F);

        IBone r_arm = this.getAnimationProcessor().getBone("r_arm");
        IBone l_arm = this.getAnimationProcessor().getBone("l_arm");
        r_arm.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance);
        l_arm.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance);

        IBone r_leg = this.getAnimationProcessor().getBone("r_leg");
        IBone l_leg = this.getAnimationProcessor().getBone("l_leg");
        r_leg.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance);
        l_leg.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance);
    }
}