package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.GladGoombaModel;
import com.dayofpi.super_block_world.client.renderers.layers.GoombaLayer;
import com.dayofpi.super_block_world.common.entities.mob.GladGoombaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class GladGoombaRenderer<T extends GladGoombaEntity> extends LeadableEntityRenderer<T> {
    public GladGoombaRenderer(EntityRendererFactory.Context context) {
        super(context, new GladGoombaModel<>());
        this.shadowRadius = 0.5f;
        addLayer(new GoombaLayer<>(this));
    }

    @Override
    public void render(T entity, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int packedLightIn) {
        super.render(entity, entityYaw, tickDelta, matrices, provider, packedLightIn);
    }

    protected float getDeathMaxRotation(T entityLivingBaseIn) {
        return 0.0F;
    }

}
