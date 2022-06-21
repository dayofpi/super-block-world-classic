package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.LilOinkModel;
import com.dayofpi.super_block_world.client.registry.EntityClient;
import com.dayofpi.super_block_world.common.entities.passive.LilOinkEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class LilOinkRenderer<T extends LilOinkEntity> extends MobEntityRenderer<T, LilOinkModel<T>> {
    public LilOinkRenderer(EntityRendererFactory.Context context) {
        super(context, new LilOinkModel<>(context.getPart(EntityClient.LIL_OINK)), 0.4f);
    }

    @Override
    public Identifier getTexture(T entity) {
        return entity.getTexturePath();
    }

    @Override
    public void render(T mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        if (mobEntity.isBaby())
            matrixStack.scale(0.5F, 0.5F, 0.5F);
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
