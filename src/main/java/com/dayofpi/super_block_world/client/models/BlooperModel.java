package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.hostile.BlooperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class BlooperModel<T extends BlooperEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/blooper.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/blooper.png");
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return null;
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        GeoBone root = this.getModel(this.getModelResource(entity)).topLevelBones.get(0);
        IBone r_tentacle = this.getAnimationProcessor().getBone("r_tentacle");
        IBone l_tentacle = this.getAnimationProcessor().getBone("l_tentacle");
        r_tentacle.setRotationZ(MathHelper.lerp(customPredicate.getPartialTick(), entity.prevTentacleAngle, entity.tentacleAngle));
        l_tentacle.setRotationZ(MathHelper.lerp(customPredicate.getPartialTick(), entity.prevTentacleAngle, entity.tentacleAngle) * -1);

        for (GeoBone geoBone : root.childBones) {
            if (geoBone.parent.getName().equals("limbs")) continue;
            geoBone.setRotationX(MathHelper.lerp(customPredicate.getPartialTick(), entity.prevTentacleAngle, entity.tentacleAngle) * 0.8F);
        }
    }
}