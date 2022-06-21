package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.block_entities.ChinchoTorchBE;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class ChinchoTorchModel<T extends ChinchoTorchBE> extends AnimatedGeoModel<T> {
    @Override
    public Identifier getModelResource(T object) {
        return new Identifier(Main.MOD_ID, "geo/chincho_torch.geo.json");
    }

    @Override
    public Identifier getTextureResource(T object) {
        return new Identifier(Main.MOD_ID, "textures/entity/chincho_torch.png");
    }

    @Override
    public Identifier getAnimationResource(T animatable) {
        return new Identifier(Main.MOD_ID, "animations/chincho_torch.animation.json");
    }

    @Override
    public void setLivingAnimations(T entity, Integer uniqueID) {
        super.setLivingAnimations(entity, uniqueID);
        IBone stars = this.getAnimationProcessor().getBone("stars");
        stars.setHidden(!entity.isLit());
    }
}
