package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.YoshiModel;
import com.dayofpi.super_block_world.common.entities.passive.YoshiEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class YoshiRenderer<T extends YoshiEntity> extends GeoEntityRenderer<T> {
    public YoshiRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new YoshiModel<>());
        this.shadowRadius = 0.6F;
    }
}
