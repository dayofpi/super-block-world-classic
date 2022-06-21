package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.passive.KoopaTroopaEntity;
import com.dayofpi.super_block_world.common.entities.passive.ParatroopaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class KoopaTroopaModel<T extends KoopaTroopaEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/koopa.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/koopa/green.png");
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return new Identifier(Main.MOD_ID, "animations/koopa.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone left_wing = this.getAnimationProcessor().getBone("left_wing");
        IBone right_wing = this.getAnimationProcessor().getBone("right_wing");
        left_wing.setHidden(!(entity instanceof ParatroopaEntity));
        right_wing.setHidden(!(entity instanceof ParatroopaEntity));
        EntityModelData modelData = (EntityModelData) customPredicate.getExtraData().get(0);

        float l = entity.age * 5F * 0.017453292F;
        right_wing.setRotationX(MathHelper.cos(l) * 0.2F - 0.1F);
        left_wing.setRotationX(MathHelper.cos(l) * 0.2F - 0.1F);

        head.setRotationX(modelData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(modelData.netHeadYaw * ((float) Math.PI / 180F));
    }

}
