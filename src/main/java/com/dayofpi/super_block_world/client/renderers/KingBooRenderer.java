package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.layers.KingBooLayer;
import com.dayofpi.super_block_world.client.models.KingBooModel;
import com.dayofpi.super_block_world.common.entities.boss.KingBooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class KingBooRenderer<T extends KingBooEntity> extends GeoEntityRenderer<T> {
    public KingBooRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new KingBooModel<>());
        this.addLayer(new KingBooLayer<>(this));
        this.shadowRadius = 1.5F;
    }

    @Override
    public RenderLayer getRenderType(T entity, float partialTicks, MatrixStack matrices, @Nullable VertexConsumerProvider provider, @Nullable VertexConsumer consumer, int light, Identifier identifier) {
        return RenderLayer.getEntityTranslucent(this.getTextureResource(entity));
    }

    @Override
    public Color getRenderColor(T entity, float partialTicks, MatrixStack matrices, VertexConsumerProvider provider, VertexConsumer consumer, int light) {
        return Color.ofRGBA(1.0F, 1.0F, 1.0F, entity.getAlpha());
    }
}
