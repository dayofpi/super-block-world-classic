package com.dayofpi.super_block_world.client.model;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.entity.mob.FakeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;

@Environment(EnvType.CLIENT)
public class FakeBlockModel<T extends FakeBlockEntity> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelLocation(T object) {
        return new Identifier(Main.MOD_ID, "geo/fake_block.geo.json");
    }

    @Override
    public Identifier getTextureLocation(T object) {
        return new Identifier(Main.MOD_ID, "textures/entity/fake_block.png");
    }

    @Override
    public Identifier getAnimationFileLocation(T animatable) {
        return new Identifier(Main.MOD_ID, "animations/fake_block.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID, @Nullable AnimationEvent customPredicate) {
        super.setLivingAnimations(entity, uniqueID, customPredicate);
        IBone block = this.getAnimationProcessor().getBone("block");
        IBone tail = this.getAnimationProcessor().getBone("tail");
        EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);


        float l = entity.age * 5F * 0.017453292F;
        //tail.setRotationY(MathHelper.cos(l) * 0.2F -0.1F);
    }
}
