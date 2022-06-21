package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.PiranhaPlantEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

@Environment(EnvType.CLIENT)
public class PiranhaPlantModel<T extends PiranhaPlantEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T object) {
        return new Identifier(Main.MOD_ID, "geo/piranha_plant.geo.json");
    }

    @Override
    public Identifier getTextureResource(T object) {
        return new Identifier(Main.MOD_ID, "textures/entity/piranha_plant.png");
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return new Identifier(Main.MOD_ID, "animations/piranha_plant.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone l_leaf = this.getAnimationProcessor().getBone("l_leaf");
        IBone r_leaf = this.getAnimationProcessor().getBone("r_leaf");
        IBone head = this.getAnimationProcessor().getBone("head");
        IBone neck = this.getAnimationProcessor().getBone("neck");
        float progress = (float) (customPredicate.animationTick * 5F * 0.04F);
        EntityModelData modelData = (EntityModelData) customPredicate.getExtraData().get(0);

        l_leaf.setRotationZ(-0.2F - (MathHelper.cos(progress * 0.5F) * 0.05F));
        r_leaf.setRotationZ(0.2F + (MathHelper.cos(progress * 0.5F) * 0.05F));
        neck.setRotationZ(MathHelper.cos(progress * 0.07F) * 0.1F);
        neck.setRotationZ(MathHelper.cos(progress * 0.07F) * 0.1F);
        float headPitch = 0.0F;
        if (entity.isAttacking()) {
            headPitch = -0.8F;
            neck.setRotationX((headPitch * 0.8F));
            neck.setRotationZ(0);
        }
        head.setRotationX(headPitch);
        head.setRotationY(modelData.netHeadYaw * ((float) Math.PI / 180F));
    }
}
