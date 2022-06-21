package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.ChinchoTorchModel;
import com.dayofpi.super_block_world.common.block_entities.ChinchoTorchBE;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class ChinchoTorchRenderer extends GeoBlockRenderer<ChinchoTorchBE> {
    public ChinchoTorchRenderer() {
        super(new ChinchoTorchModel<>());
    }

    @Override
    public RenderLayer getRenderType(ChinchoTorchBE entity, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityTranslucent(getTextureResource(entity));
    }

    @Override
    public void render(ChinchoTorchBE tile, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int packedLightIn) {
        stack.translate(0, -0.01f, 0);
        super.render(tile, partialTicks, stack, bufferIn, packedLightIn);
    }
}
