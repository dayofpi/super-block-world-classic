package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.UnagiModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.hostile.UnagiEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class UnagiRenderer extends MobEntityRenderer<UnagiEntity, UnagiModel> {
    public UnagiRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new UnagiModel(ctx.getPart(ModModelLayers.UNAGI)), 0.4F);
    }

    @Override
    public Identifier getTexture(UnagiEntity entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/unagi.png");
    }

    @Override
    protected void setupTransforms(UnagiEntity unagiEntity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(unagiEntity, matrices, animationProgress, bodyYaw, tickDelta);
        float i = 4.3f * MathHelper.sin(0.6f * animationProgress);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(i));
        if (!unagiEntity.isTouchingWater()) {
            matrices.translate(0.2f, 0.1f, 0.0);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(90.0f));
        }
    }
}
