package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.FlushedFeatureRenderer;
import com.dayofpi.super_block_world.client.models.GoombaModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.hostile.GoombaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GoombaRenderer<T extends GoombaEntity> extends MobEntityRenderer<T, GoombaModel<T>> {
    private static final Identifier COMMON = new Identifier(Main.MOD_ID, "textures/entity/goomba/goomba.png");
    private static final Identifier GOLD = new Identifier(Main.MOD_ID, "textures/entity/goomba/gold_goomba.png");

    public GoombaRenderer(EntityRendererFactory.Context context) {
        super(context, new GoombaModel<>(context.getPart(ModModelLayers.GOOMBA)), 0.5f);
        this.addFeature(new FlushedFeatureRenderer<>(this));
    }

    @Override
    public void render(T entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.shadowRadius = 0.25F * entity.getSize() + 0.25F;
        super.render(entity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    protected void scale(T entity, MatrixStack matrices, float amount) {
        if (entity.getSize() == 0) {
            matrices.scale(0.5F, 0.5F, 0.5F);
        } else if (entity.getSize() == 2) {
            matrices.scale(2.5F, 2.5F, 2.5F);
        } else {
            matrices.scale(entity.getSize(), entity.getSize(), entity.getSize());
        }
    }

    @Override
    protected float getLyingAngle(GoombaEntity entity) {
        return 0.0f;
    }

    @Override
    public Identifier getTexture(GoombaEntity entity) {
        return entity.isGold() ? GOLD : COMMON;
    }
}
