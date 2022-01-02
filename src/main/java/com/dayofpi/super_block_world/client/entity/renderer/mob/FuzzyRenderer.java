package com.dayofpi.super_block_world.client.entity.renderer.mob;

import com.dayofpi.super_block_world.client.entity.model.FuzzyModel;
import com.dayofpi.super_block_world.main.common.entity.mob.FuzzyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class FuzzyRenderer<T extends FuzzyEntity> extends GeoEntityRenderer<T> {
    public FuzzyRenderer(EntityRendererFactory.Context context) {
        super(context, new FuzzyModel<>());
        this.shadowRadius = 0.3F;
    }
}
