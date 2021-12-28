package com.dayofpi.super_block_world.client.entity.renderer.other;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.entity.projectile.IceballEntity;
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
public class EnemyIceballRenderer extends EntityRenderer<IceballEntity> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/item/enemy_iceball.png");
    private static final RenderLayer LAYER;

    public EnemyIceballRenderer(EntityRendererFactory.Context context) {
        super(context);
    }

    protected int getBlockLight(IceballEntity fireballEntity, BlockPos blockPos) {
        return 7;
    }

    public void render(IceballEntity entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumers, int light) {
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

    private static void produceVertex(VertexConsumer vertexConsumer, Matrix4f modelMatrix, Matrix3f normalMatrix, int light, float x, int y, int textureU, int textureV) {
        vertexConsumer.vertex(modelMatrix, x - 0.5F, (float)y - 0.25F, 0.0F).color(255, 255, 255, 255).texture((float)textureU, (float)textureV).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normalMatrix, 0.0F, 1.0F, 0.0F).next();
    }

    public Identifier getTexture(IceballEntity fireballEntity) {
        return TEXTURE;
    }

    static {
        LAYER = RenderLayer.getEntityCutoutNoCull(TEXTURE);
    }
}