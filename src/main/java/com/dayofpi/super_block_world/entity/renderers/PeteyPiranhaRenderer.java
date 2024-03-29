package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.boss.PeteyPiranhaEntity;
import com.dayofpi.super_block_world.entity.models.PeteyPiranhaModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class PeteyPiranhaRenderer extends MobEntityRenderer<PeteyPiranhaEntity, PeteyPiranhaModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/petey_piranha.png");

    public PeteyPiranhaRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PeteyPiranhaModel(ctx.getPart(ModModelLayers.PETEY_PIRANHA)), 0.8F);
    }

    @Override
    protected void setupTransforms(PeteyPiranhaEntity entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
        if (entity.isSpinning())
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(animationProgress * 100));
    }

    @Override
    protected void scale(PeteyPiranhaEntity entity, MatrixStack matrices, float amount) {
        matrices.scale(1.5f, 1.5f, 1.5f);
    }

    @Override
    public Identifier getTexture(PeteyPiranhaEntity entity) {
        return TEXTURE;
    }
}
