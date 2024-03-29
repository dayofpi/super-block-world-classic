package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.BlooperModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.BlooperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class BlooperRenderer extends MobEntityRenderer<BlooperEntity, BlooperModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/blooper.png");

    public BlooperRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BlooperModel(ctx.getPart(ModModelLayers.BLOOPER)), 0.4F);
    }

    @Override
    public Identifier getTexture(BlooperEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void setupTransforms(BlooperEntity entity, MatrixStack matrixStack, float f, float g, float h) {
        float i = MathHelper.lerp(h, entity.prevTiltAngle, entity.tiltAngle);
        float j = MathHelper.lerp(h, entity.prevRollAngle, entity.rollAngle);
        matrixStack.translate(0.0, 0.5, 0.0);
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0f - g));
        matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(i));
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(j));
        matrixStack.translate(0.0, -1.2f, 0.0);
    }

    @Override
    protected float getAnimationProgress(BlooperEntity entity, float f) {
        return MathHelper.lerp(f, entity.prevTentacleAngle, entity.tentacleAngle);
    }
}
