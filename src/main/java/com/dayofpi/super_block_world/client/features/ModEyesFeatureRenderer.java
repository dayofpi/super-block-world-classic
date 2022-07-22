package com.dayofpi.super_block_world.client.features;

import com.dayofpi.super_block_world.common.entities.hostile.DryBonesEntity;
import com.dayofpi.super_block_world.common.entities.misc.KoopaShellEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

public class ModEyesFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends EyesFeatureRenderer<T, M> {
    private final RenderLayer SKIN;

    public ModEyesFeatureRenderer(Identifier texture, FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
        this.SKIN = RenderLayer.getEyes(texture);
    }

    @Override
    public RenderLayer getEyesTexture() {
        return SKIN;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if (entity instanceof DryBonesEntity && ((DryBonesEntity) entity).blockEntityContext)
            return;
        if (entity instanceof KoopaShellEntity && !((KoopaShellEntity) entity).isOccupied())
            return;
        super.render(matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
    }
}
