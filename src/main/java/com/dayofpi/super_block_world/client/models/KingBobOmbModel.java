package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.boss.KingBobOmbEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class KingBobOmbModel<T extends KingBobOmbEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T entity) {
        return new Identifier(Main.MOD_ID, "geo/king_bob_omb.geo.json");
    }

    @Override
    public Identifier getTextureResource(T entity) {
        return null;
    }

    @Override
    public Identifier getAnimationResource(T entity) {
        return new Identifier(Main.MOD_ID, "animations/king_bob_omb.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone r_arm = this.getAnimationProcessor().getBone("r_arm");
        IBone l_arm = this.getAnimationProcessor().getBone("l_arm");
        IBone r_leg = this.getAnimationProcessor().getBone("r_leg");
        IBone l_leg = this.getAnimationProcessor().getBone("l_leg");

        float progress = (float) (customPredicate.animationTick * 2F * 0.01F);
        r_arm.setRotationZ((MathHelper.cos(progress) * 0.2F) + 0.5F);
        l_arm.setRotationZ((MathHelper.cos(progress) * -0.2F) - 0.5F);

        if (entity.getThrowCooldown() < 40 && !entity.isLaughing()) {
            r_arm.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance);
            if (!entity.isSummoning())
                l_arm.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance);
        }
        r_leg.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance);
        l_leg.setRotationX(MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance);
    }
}
