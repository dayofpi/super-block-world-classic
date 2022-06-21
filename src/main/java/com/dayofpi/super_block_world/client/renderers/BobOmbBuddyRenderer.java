package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.BobOmbBuddyModel;
import com.dayofpi.super_block_world.common.entities.passive.BobOmbBuddyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class BobOmbBuddyRenderer<T extends BobOmbBuddyEntity> extends GeoEntityRenderer<T> {
    public BobOmbBuddyRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BobOmbBuddyModel<>());
        this.shadowRadius = 0.4F;
    }
}
