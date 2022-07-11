package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.LavaBubbleOverlayFeatureRenderer;
import com.dayofpi.super_block_world.client.models.LavaBubbleModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.hostile.LavaBubbleEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

public class LavaBubbleRenderer extends MobEntityRenderer<LavaBubbleEntity, LavaBubbleModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/lava_bubble.png");

    public LavaBubbleRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new LavaBubbleModel(ctx.getPart(ModModelLayers.LAVA_BUBBLE)), 0.25f);
        this.addFeature(new LavaBubbleOverlayFeatureRenderer(this, ctx.getModelLoader()));
    }

    @Override
    public void render(LavaBubbleEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.shadowRadius = 0.25f * (float)entity.getSize();
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    protected int getBlockLight(LavaBubbleEntity lavaBubbleEntity, BlockPos blockPos) {
        return 15;
    }

    @Override
    protected void setupTransforms(LavaBubbleEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
        matrices.multiply(Vec3f.NEGATIVE_X.getRadialQuaternion(entity.angle * bodyYaw));
    }

    @Override
    protected void scale(LavaBubbleEntity lavaBubbleEntity, MatrixStack matrixStack, float f) {
        int i = lavaBubbleEntity.getSize();
        float g = MathHelper.lerp(f, lavaBubbleEntity.lastStretch, lavaBubbleEntity.stretch) / ((float)i * 0.5f + 1.0f);
        float h = 1.0f / (g + 1.0f);
        matrixStack.scale(h * (float)i, 1.0f / h * (float)i, h * (float)i);
    }

    @Override
    public Identifier getTexture(LavaBubbleEntity entity) {
        return TEXTURE;
    }
}
