package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.RottenMushroomModel;
import com.dayofpi.super_block_world.common.entities.hostile.RottenMushroomEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;

@Environment(EnvType.CLIENT)
public class RottenMushroomRenderer<T extends RottenMushroomEntity> extends ModEntityRenderer<T> {
    public RottenMushroomRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new RottenMushroomModel<>());
        this.shadowRadius = 0.4F;
    }
}
