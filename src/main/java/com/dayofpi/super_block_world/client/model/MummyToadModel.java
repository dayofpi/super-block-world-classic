package com.dayofpi.super_block_world.client.model;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.entity.mob.MummyToadEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

@Environment(EnvType.CLIENT)
public class MummyToadModel<T extends MummyToadEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelLocation(T object) {
        return new Identifier(Main.MOD_ID, "geo/mummy_toad.geo.json");
    }

    @Override
    public Identifier getTextureLocation(T object) {
        return new Identifier(Main.MOD_ID, "textures/entity/toad/mummy.png");
    }

    @Override
    public Identifier getAnimationFileLocation(T animatable) {
        return new Identifier(Main.MOD_ID, "animations/mummy_toad.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone root = this.getAnimationProcessor().getBone("root");
        IBone neck = this.getAnimationProcessor().getBone("neck");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);

        root.setHidden(entity.isHidden());

        if (entity.isConverting() || entity.isFreezing()) {
            entity.bodyYaw += (float)(Math.cos((double) entity.age * 3.25) * Math.PI * (double)0.4f);
        }

        neck.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        neck.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }
}
