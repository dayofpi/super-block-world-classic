package com.dayofpi.super_block_world.client.features;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.passive.GladGoombaEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class FlushedFeatureRenderer<T extends MobEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/goomba/flushed.png");
    private static final Identifier TEXTURE_GLAD_GOOMBA = new Identifier(Main.MOD_ID, "textures/entity/goomba/flushed_glad.png");

    public FlushedFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        String string = Formatting.strip(entity.getName().getString());
        if ("flushed".equals(string) || "Flushed".equals(string)) {
            VertexConsumer vertexConsumer;
            if (entity instanceof GladGoombaEntity)
                vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(TEXTURE_GLAD_GOOMBA));
            else vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(TEXTURE));
            this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);

        }
    }
}
