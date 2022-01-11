package com.dayofpi.super_block_world.client.model;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entity.mob.goomba.GladGoombaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

@Environment(EnvType.CLIENT)
public class GladGoombaModel<T extends GladGoombaEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelLocation(T object) {
        return new Identifier(Main.MOD_ID, "geo/goomba.geo.json");
    }

    @Override
    public Identifier getTextureLocation(T object) {
        return new Identifier(Main.MOD_ID, "textures/entity/goomba/glad_goomba.png");
    }

    @Override
    public Identifier getAnimationFileLocation(T animatable) {
        return new Identifier(Main.MOD_ID, "animations/goomba.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone root = this.getAnimationProcessor().getBone("root");
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone left_wing = this.getAnimationProcessor().getBone("left_wing");
        IBone right_wing = this.getAnimationProcessor().getBone("right_wing");
        left_wing.setHidden(true);
        right_wing.setHidden(true);
        EntityModelData extraData;
        
        if (customPredicate != null) {
            extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
            if (entity.getHealth() > 0) {
                head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
                head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
            } else {
                head.setRotationX(0);
                head.setRotationY(0);
            }
        }

        float l = entity.age * 5F * 0.017453292F;
        right_wing.setRotationX(MathHelper.cos(l) * 0.2F -0.1F);
        left_wing.setRotationX(MathHelper.cos(l) * 0.2F -0.1F);

        if (entity.isBaby()) {
            root.setScaleX(0.5F);
            root.setScaleY(0.5F);
            root.setScaleZ(0.5F);
            head.setScaleX(1.3F);
            head.setScaleY(1.3F);
            head.setScaleZ(1.3F);
        }
    }
}
