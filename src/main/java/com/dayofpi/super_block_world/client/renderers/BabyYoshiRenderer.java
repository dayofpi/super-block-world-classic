package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.BabyYoshiModel;
import com.dayofpi.super_block_world.common.entities.passive.BabyYoshiEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class BabyYoshiRenderer<T extends BabyYoshiEntity> extends GeoEntityRenderer<T> {
    public BabyYoshiRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BabyYoshiModel<>());
        this.shadowRadius = 0.6F;
    }

    @Override
    public void render(GeoModel model, T entity, float partialTicks, RenderLayer type, MatrixStack matrices, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrices.scale(0.6F, 0.6F, 0.6F);
        super.render(model, entity, partialTicks, type, matrices, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
