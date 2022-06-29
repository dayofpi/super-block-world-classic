package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.MudTrooperModel;
import com.dayofpi.super_block_world.common.entities.hostile.MudTrooperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class MudTrooperRenderer<T extends MudTrooperEntity> extends GeoEntityRenderer<T> {
    public MudTrooperRenderer(EntityRendererFactory.Context context) {
        super(context, new MudTrooperModel<>());
        this.shadowRadius = 0.5F;
    }
}
