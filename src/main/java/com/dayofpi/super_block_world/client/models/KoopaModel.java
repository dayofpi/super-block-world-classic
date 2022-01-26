package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.mob.KoopaEntity;
import com.dayofpi.super_block_world.common.entities.mob.ParatroopaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

@SuppressWarnings("unchecked")
@Environment(EnvType.CLIENT)
public class KoopaModel<T extends KoopaEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelLocation(T object) {
        return new Identifier(Main.MOD_ID, "geo/koopa.geo.json");
    }

    @Override
    public Identifier getTextureLocation(T object) {
        return new Identifier(Main.MOD_ID, "textures/entity/koopa/green.png");
    }

    @Override
    public Identifier getAnimationFileLocation(T animatable) {
        return new Identifier(Main.MOD_ID, "animations/koopa.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone left_wing = this.getAnimationProcessor().getBone("left_wing");
        IBone right_wing = this.getAnimationProcessor().getBone("right_wing");
        left_wing.setHidden(!(entity instanceof ParatroopaEntity));
        right_wing.setHidden(!(entity instanceof ParatroopaEntity));
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        float l = entity.age * 5F * 0.017453292F;
        right_wing.setRotationX(MathHelper.cos(l) * 0.2F -0.1F);
        left_wing.setRotationX(MathHelper.cos(l) * 0.2F -0.1F);

        head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }

}
