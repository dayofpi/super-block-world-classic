package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.MooMooModel;
import com.dayofpi.super_block_world.common.entities.passive.MooMooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;

@Environment(EnvType.CLIENT)
public class MooMooRenderer<T extends MooMooEntity> extends ModEntityRenderer<T> {
    public MooMooRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MooMooModel<>());
        this.shadowRadius = 0.5F;
    }

    @Override
    public void render(GeoModel model, T entity, float partialTicks, RenderLayer type, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (entity.isBaby()) {
            stack.scale(0.5F, 0.5F, 0.5F);
        }
        super.render(model, entity, partialTicks, type, stack, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public Identifier getTexture(T entity) {
        return entity.getTexture();
    }
}
