package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.CheepCheepModel;
import com.dayofpi.super_block_world.common.entities.hostile.CheepCheepEntity;
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
public class CheepCheepRenderer<T extends CheepCheepEntity> extends GeoEntityRenderer<T> {
    public CheepCheepRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new CheepCheepModel<>());
        this.shadowRadius = 0.4F;
    }

    @Override
    public void render(GeoModel model, T animatable, float partialTicks, RenderLayer type, MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
