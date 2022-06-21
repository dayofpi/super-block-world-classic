package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.MooMooModel;
import com.dayofpi.super_block_world.client.main.ModelLayers;
import com.dayofpi.super_block_world.common.entities.mob.MooMooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class MooMooRenderer<T extends MooMooEntity> extends MobEntityRenderer<T, MooMooModel<T>> {
    public MooMooRenderer(EntityRendererFactory.Context context) {
        super(context, new MooMooModel<>(context.getPart(ModelLayers.MOO_MOO)), 0.7F);
    }

    @Override
    protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        if (entity.isLying()) {
            matrices.translate(0.0D, -0.3F, 0.0D);
        }
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
    }

    public Identifier getTexture(MooMooEntity mooMooEntity) {
        return mooMooEntity.getTexture();
    }
}
