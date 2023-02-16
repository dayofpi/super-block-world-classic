package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.entity.entities.projectile.HomingFlameEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Matrix3f;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class HomingFlameRenderer extends EntityRenderer<HomingFlameEntity> {
    private static final Identifier TEXTURE = new Identifier("textures/particle/soul_fire_flame.png");
    private static final RenderLayer LAYER;

    static {
        LAYER = RenderLayer.getEntityCutoutNoCull(TEXTURE);
    }

    public HomingFlameRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    private static void produceVertex(VertexConsumer vertexConsumer, Matrix4f modelMatrix, Matrix3f normalMatrix, int light, float x, int y, int textureU, int textureV) {
        vertexConsumer.vertex(modelMatrix, x - 0.5F, (float) y - 0.25F, 0.0F).color(255, 255, 255, 255).texture((float) textureU, (float) textureV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).next();
    }

    @Override
    protected int getBlockLight(HomingFlameEntity entity, BlockPos pos) {
        return 15;
    }

    public void render(HomingFlameEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
        matrixStack.push();
        matrixStack.scale(1.0F, 1.0F, 1.0F);
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
        MatrixStack.Entry entry = matrixStack.peek();
        Matrix4f matrix4f = entry.getPositionMatrix();
        Matrix3f matrix3f = entry.getNormalMatrix();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(LAYER);
        produceVertex(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 0, 0, 1);
        produceVertex(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 0, 1, 1);
        produceVertex(vertexConsumer, matrix4f, matrix3f, light, 1.0F, 1, 1, 0);
        produceVertex(vertexConsumer, matrix4f, matrix3f, light, 0.0F, 1, 0, 0);
        matrixStack.pop();
        super.render(entity, yaw, tickDelta, matrixStack, vertexConsumers, light);
    }

    public Identifier getTexture(HomingFlameEntity entity) {
        return TEXTURE;
    }
}