package com.dayofpi.super_block_world.client.features;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.entities.hostile.BooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BooFaceFeatureRenderer<T extends BooEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier FACE_TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/boo/face.png");
    private static final Identifier OVERLAY_TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/boo/boo_overlay.png");

    public BooFaceFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!entity.isClientShy()) {
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(this.getEyesTexture(entity));
            this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, entity.getAlpha());
        }
        if (entity.isTamed()) {
            float[] color = entity.getBooColor().getColorComponents();
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(OVERLAY_TEXTURE));
            this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, color[0], color[1], color[2], 0.5F);
        }
    }

    public RenderLayer getEyesTexture(T entity) {
        if (entity.isInSittingPose())
            return RenderLayer.getEntityTranslucent(entity.getBooFace().texture());
        return RenderLayer.getEntityTranslucent(FACE_TEXTURE);
    }
}
