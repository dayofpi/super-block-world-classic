package com.dayofpi.super_block_world.client.features;

import com.dayofpi.super_block_world.client.models.PowerUpModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.PowerUp;
import com.dayofpi.super_block_world.util.FormManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PowerUpFeatureRenderer<T extends PlayerEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private final EntityModel<PlayerEntity> model;

    public PowerUpFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.model = new PowerUpModel(loader.getModelPart(ModModelLayers.POWER_UP));
    }

    @Override
    protected Identifier getTexture(T entity) {
        return PowerUp.getTexture(entity.getDataTracker().get(FormManager.POWER_UP));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity.getDataTracker().get(FormManager.POWER_UP).equals(PowerUp.NONE.asString()))
            return;
        boolean bl;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        bl = minecraftClient.hasOutline(entity) && entity.isInvisible();
        if (entity.isInvisible() && !bl) {
            return;
        }
        VertexConsumer vertexConsumer = bl ? vertexConsumerProvider.getBuffer(RenderLayer.getOutline(this.getTexture(entity))) : vertexConsumerProvider.getBuffer(RenderLayer.getEntityCutoutNoCull(this.getTexture(entity)));
        double d = -0.75;
        if (entity.isInSneakingPose())
            d = -0.5;
        matrixStack.translate(0.0, d, 0.0);
        this.model.animateModel(entity, limbAngle, limbDistance, tickDelta);
        this.model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        this.model.render(matrixStack, vertexConsumer, light, LivingEntityRenderer.getOverlay(entity, 0.0f), 1.0f, 1.0f, 1.0f, 0.5f);

    }
}
