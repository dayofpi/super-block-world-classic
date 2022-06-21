package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.KingBobOmbModel;
import com.dayofpi.super_block_world.common.entities.boss.KingBobOmbEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class KingBobOmbRenderer<T extends KingBobOmbEntity> extends GeoEntityRenderer<T> {
    public KingBobOmbRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new KingBobOmbModel<>());
        this.shadowRadius = 0.9F;
    }

    @Override
    public Identifier getTexture(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/king_bob_omb.png");
    }

    @Override
    public void render(GeoModel model, T animatable, float partialTicks, RenderLayer type, MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.scale(2.0F, 2.0F, 2.0F);
        type = RenderLayer.getEntityCutoutNoCull(this.getTexture(animatable));
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }
}
