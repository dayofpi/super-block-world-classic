package com.dayofpi.super_block_world.client.features;

import com.dayofpi.super_block_world.common.entities.hostile.GoombaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class GoombaItemRenderer<T extends GoombaEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private final HeldItemRenderer heldItemRenderer;

    public GoombaItemRenderer(FeatureRendererContext<T, M> context, HeldItemRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, T livingEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemStack itemStack = livingEntity.getMainHandStack();
        if (itemStack.isEmpty()) {
            return;
        }
        matrixStack.push();
        this.renderItem(livingEntity, itemStack, matrixStack, vertexConsumerProvider, light);
        matrixStack.pop();
    }

    protected void renderItem(LivingEntity entity, ItemStack stack, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (stack.isEmpty()) {
            return;
        }
        matrices.push();
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0f));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.age * 5));
        matrices.scale(0.7F, 0.7F, 0.7F);
        matrices.translate((float) 1 / 16.0f, -0.85f, -0.5);
        this.heldItemRenderer.renderItem(entity, stack, ModelTransformation.Mode.HEAD, false, matrices, vertexConsumers, light);
        matrices.pop();
    }
}
