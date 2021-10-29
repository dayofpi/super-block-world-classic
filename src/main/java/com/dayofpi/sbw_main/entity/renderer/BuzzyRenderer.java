package com.dayofpi.sbw_main.entity.renderer;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.entity.registry.ModelLayers;
import com.dayofpi.sbw_main.entity.model.BuzzyModel;
import com.dayofpi.sbw_main.entity.feature.BuzzyEyes;
import com.dayofpi.sbw_main.entity.types.mobs.BuzzyEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3f;

public class BuzzyRenderer<T extends BuzzyEntity> extends MobEntityRenderer<T, BuzzyModel<T>> {
    private static final Identifier DEFAULT = new Identifier(Main.MOD_ID, "textures/entity/buzzy_beetle/buzzy.png");
    private static final Identifier HIDING = new Identifier(Main.MOD_ID, "textures/entity/buzzy_beetle/buzzy_shell.png");

    public BuzzyRenderer(EntityRendererFactory.Context context) {
        super(context, new BuzzyModel<>(context.getPart(ModelLayers.BUZZY_BEETLE)), 0.5F);
        this.addFeature(new SaddleFeatureRenderer<>(this, new BuzzyModel<>(context.getPart(ModelLayers.BUZZY_BEETLE_SADDLE)), new Identifier(Main.MOD_ID, "textures/entity/buzzy_beetle/buzzy_saddle.png")));
        this.addFeature(new BuzzyEyes<>(this));
        this.model.getSpikeParts().forEach((spikeParts) -> spikeParts.visible = false);
    }

    @Override
    protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        if (entity.isBaby()) {
            matrices.scale(0.5F, 0.5F, 0.5F);
        }
        if (entity.isUpsideDown()) {
            matrices.translate(0.0D, entity.getHeight() + 0.1F, 0.0D);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
        }
        if (entity.isHiding()) {
            matrices.translate(0.0D, -0.2, 0.0D);
        }
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
    }

    @Override
    public Identifier getTexture(T entity) {
        return entity.isHiding() ? HIDING : DEFAULT;
    }
}
