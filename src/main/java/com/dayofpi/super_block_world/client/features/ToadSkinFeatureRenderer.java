package com.dayofpi.super_block_world.client.features;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.entities.passive.ToadEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

public class ToadSkinFeatureRenderer<T extends PassiveEntity, M extends AnimalModel<T>> extends FeatureRenderer<T, M> {
    public ToadSkinFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (!entity.isInvisible()) {
            String string = Formatting.strip(entity.getName().getString());
            RenderLayer texture = RenderLayer.getEntityTranslucent(new Identifier(Main.MOD_ID, "textures/entity/toad/mail.png"));
            if ("Lucy".equals(string))
                texture = RenderLayer.getEntityTranslucent(new Identifier(Main.MOD_ID, "textures/entity/toad/lucy.png"));
            else if (entity instanceof ToadEntity)
                texture = RenderLayer.getEntityTranslucent(((ToadEntity) entity).getTexture());
            VertexConsumer consumer = vertexConsumers.getBuffer(texture);
            this.getContextModel().render(matrices, consumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
