package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.projectile.MechakoopaMissileEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class MechakoopaMissileModel<T extends MechakoopaMissileEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T object) {
        return new Identifier(Main.MOD_ID, "geo/mechakoopa_missile.geo.json");
    }

    @Override
    public Identifier getTextureResource(T object) {
        return new Identifier(Main.MOD_ID, "textures/entity/mechakoopa/missile.png");
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return null;
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone main = this.getAnimationProcessor().getBone("bb_main");
        main.setRotationX(1F);
        main.setRotationZ(-1.5F);
        main.setRotationY(-2.5F);

    }
}
