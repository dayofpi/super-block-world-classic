package com.dayofpi.super_block_world.client.features;

import com.dayofpi.super_block_world.client.models.LavaBubbleModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.hostile.LavaBubbleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class LavaBubbleOverlayFeatureRenderer extends FeatureRenderer<LavaBubbleEntity, LavaBubbleModel> {
    private final EntityModel<LavaBubbleEntity> model;

    public LavaBubbleOverlayFeatureRenderer(FeatureRendererContext<LavaBubbleEntity, LavaBubbleModel> context, EntityModelLoader loader) {
        super(context);
        this.model = new LavaBubbleModel(loader.getModelPart(ModModelLayers.LAVA_BUBBLE_OUTER));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, LavaBubbleEntity livingEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        boolean bl;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        bl = minecraftClient.hasOutline(livingEntity) && livingEntity.isInvisible();
        if (livingEntity.isInvisible() && !bl) {
            return;
        }
        VertexConsumer vertexConsumer = bl ? vertexConsumerProvider.getBuffer(RenderLayer.getOutline(this.getTexture(livingEntity))) : vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(livingEntity)));
        (this.getContextModel()).copyStateTo(this.model);
        this.model.animateModel(livingEntity, limbAngle, limbDistance, tickDelta);
        this.model.setAngles(livingEntity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        this.model.render(matrixStack, vertexConsumer, light, LivingEntityRenderer.getOverlay(livingEntity, 0.0f), 1.0f, 1.0f, 1.0f, 0.5f);
    }
}
