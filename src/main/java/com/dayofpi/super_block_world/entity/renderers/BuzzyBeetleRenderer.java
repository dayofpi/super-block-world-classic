package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.ModEyesFeatureRenderer;
import com.dayofpi.super_block_world.entity.models.BuzzyBeetleModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.BuzzyBeetleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;
@Environment(EnvType.CLIENT)
public class BuzzyBeetleRenderer<T extends BuzzyBeetleEntity> extends MobEntityRenderer<T, BuzzyBeetleModel<T>> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/buzzy_beetle/buzzy.png");
    private static final Identifier EYES = new Identifier(Main.MOD_ID, "textures/entity/buzzy_beetle/buzzy_eyes.png");

    public BuzzyBeetleRenderer(EntityRendererFactory.Context context) {
        super(context, new BuzzyBeetleModel<>(context.getPart(ModModelLayers.BUZZY_BEETLE)), 0.5F);
        this.addFeature(new SaddleFeatureRenderer<>(this, new BuzzyBeetleModel<>(context.getPart(ModModelLayers.BUZZY_BEETLE_SADDLE)), new Identifier(Main.MOD_ID, "textures/entity/buzzy_beetle/buzzy_saddle.png")));
        this.addFeature(new ModEyesFeatureRenderer<>(EYES, this));
        this.model.getSpikeParts().forEach((spikeParts) -> spikeParts.visible = false);
    }

    @Override
    protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        if (entity.isBaby()) {
            matrices.scale(0.6F, 0.6F, 0.6F);
        }
        if (entity.isUpsideDown()) {
            matrices.translate(0.0D, entity.getHeight() + 0.1F, 0.0D);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
        }
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }
}
