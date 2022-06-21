package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.mob.ToadEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

@SuppressWarnings("unchecked")
@Environment(EnvType.CLIENT)
public class ToadModel<T extends ToadEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelLocation(T object) {
        return new Identifier(Main.MOD_ID, "geo/toad.geo.json");
    }

    @Override
    public Identifier getTextureLocation(T object) {
        return new Identifier(Main.MOD_ID, "textures/entity/toad/white.png");
    }

    @Override
    public Identifier getAnimationFileLocation(T animatable) {
        return new Identifier(Main.MOD_ID, "animations/toad.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone root = this.getAnimationProcessor().getBone("root");
        IBone neck = this.getAnimationProcessor().getBone("neck");
        IBone left_twintail = this.getAnimationProcessor().getBone("left_twintail");
        IBone right_twintail = this.getAnimationProcessor().getBone("right_twintail");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        if (entity.isScared() && !customPredicate.isMoving() || entity.isFreezing()) {
            entity.bodyYaw += (float)(Math.cos((double) entity.age * 3.25) * Math.PI * (double)0.4f);
        }

        neck.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        neck.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
        String name = Formatting.strip(entity.getName().getString());

        left_twintail.setHidden(!entity.isToadette() && !"Lucy".equals(name));
        right_twintail.setHidden(!entity.isToadette() && !"Lucy".equals(name));

        if (entity.isBaby()) {
            root.setScaleX(0.5F);
            root.setScaleY(0.5F);
            root.setScaleZ(0.5F);
            neck.setScaleX(2.0F);
            neck.setScaleY(2.0F);
            neck.setScaleZ(2.0F);
        }
    }
}
