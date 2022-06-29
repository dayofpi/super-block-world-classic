package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.ShyGuyModel;
import com.dayofpi.super_block_world.common.entities.hostile.ShyGuyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class ShyGuyRenderer<T extends ShyGuyEntity> extends GeoEntityRenderer<T> {
    public ShyGuyRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ShyGuyModel<>());
        this.shadowRadius = 0.5F;
    }
}
