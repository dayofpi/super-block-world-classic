package com.dayofpi.super_block_world.client.features;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class ModEyesFeatureRenderer<T extends LivingEntity, M extends SinglePartEntityModel<T>> extends EyesFeatureRenderer<T, M> {
    private final RenderLayer SKIN;

    public ModEyesFeatureRenderer(Identifier texture, FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
        this.SKIN = RenderLayer.getEyes(texture);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}
