package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.layers.GoombaLayer;
import com.dayofpi.super_block_world.client.models.GladGoombaModel;
import com.dayofpi.super_block_world.common.entities.passive.GladGoombaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class GladGoombaRenderer<T extends GladGoombaEntity> extends ModEntityRenderer<T> {
    public GladGoombaRenderer(EntityRendererFactory.Context context) {
        super(context, new GladGoombaModel<>());
        this.shadowRadius = 0.5f;
        addLayer(new GoombaLayer<>(this));
    }

    @Override
    protected void applyRotations(T entity, MatrixStack matrices, float ageInTicks, float rotationYaw, float partialTicks) {
        if (entity.isBig()) {
            matrices.scale(2.5F, 2.5F, 2.5F);
        }
        super.applyRotations(entity, matrices, ageInTicks, rotationYaw, partialTicks);
    }

    protected float getDeathMaxRotation(T entity) {
        return 0.0F;
    }

}
