package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.BobOmbModel;
import com.dayofpi.super_block_world.common.entities.hostile.BobOmbEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class BobOmbRenderer<T extends BobOmbEntity> extends GeoEntityRenderer<T> {
    public BobOmbRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BobOmbModel<>());
        this.shadowRadius = 0.4F;
    }

    @Override
    public void render(GeoModel model, T animatable, float partialTicks, RenderLayer type, MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        scale(animatable, matrixStackIn, partialTicks);
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    protected void scale(BobOmbEntity bobOmbEntity, MatrixStack matrixStack, float f) {
        float time = bobOmbEntity.getClientFuseTime(f);
        float h = 1.0F + MathHelper.sin(time * 100.0F) * time * 0.01F;
        time = MathHelper.clamp(time, 0.0F, 1.0F);
        time *= time;
        time *= time;
        float i = (1.0F + time * 0.4F) * h;
        float j = (1.0F + time * 0.1F) / h;
        matrixStack.scale(i, j, i);
    }
}
