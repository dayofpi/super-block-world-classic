package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.MummyMeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class MummyMeModel<T extends MummyMeEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/mummy_me.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/toad/mummy.png");
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return new Identifier(Main.MOD_ID, "animations/mummy_me.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        final IBone ROOT = this.getAnimationProcessor().getBone("root");
        final IBone NECK = this.getAnimationProcessor().getBone("neck");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraData().get(0);

        ROOT.setHidden(entity.isHidden());

        if (entity.isConverting() || entity.isFrozen()) {
            entity.bodyYaw += (float) (Math.cos((double) entity.age * 3.25) * Math.PI * (double) 0.4f);
        }

        NECK.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
        NECK.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
    }
}
