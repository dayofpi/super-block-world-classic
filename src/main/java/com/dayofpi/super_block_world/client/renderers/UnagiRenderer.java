package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.UnagiModel;
import com.dayofpi.super_block_world.common.entities.hostile.UnagiEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class UnagiRenderer<T extends UnagiEntity> extends GeoEntityRenderer<T> {
    public UnagiRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new UnagiModel<>());
        this.shadowRadius = 0.4F;
    }
}
