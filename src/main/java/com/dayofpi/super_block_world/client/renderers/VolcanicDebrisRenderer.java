package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.VolcanicDebrisModel;
import com.dayofpi.super_block_world.common.entities.VolcanicDebrisEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class VolcanicDebrisRenderer<T extends VolcanicDebrisEntity> extends GeoEntityRenderer<T> {
    public VolcanicDebrisRenderer(EntityRendererFactory.Context context) {
        super(context, new VolcanicDebrisModel<>());
        this.shadowRadius = 0.5F;
    }
}
