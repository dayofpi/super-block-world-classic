package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.FlushedFeatureRenderer;
import com.dayofpi.super_block_world.client.features.GoombaItemRenderer;
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
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class GoombaRenderer<T extends GoombaEntity> extends MobEntityRenderer<T, GoombaModel<T>> {
    public GoombaRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new GoombaModel<>(ctx.getPart(ModModelLayers.GOOMBA)), 0.5f);
        this.addFeature(new FlushedFeatureRenderer<>(this));
        this.addFeature(new GoombaItemRenderer<>(this, ctx.getHeldItemRenderer()));
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.shadowRadius = 0.25F * entity.getSize() + 0.25F;
        super.render(entity, yaw, tickDelta, matrices, vertexConsumerProvider, i);
    }

    @Override
    protected void scale(T entity, MatrixStack matrices, float amount) {
        if (entity.isDead()) {
            float h = MathHelper.lerp(entity.deathTime * 0.15F, 1.0F, 1.2F);
            float v = MathHelper.lerp(entity.deathTime * 0.15F, 1.0F, 0.25F);
            matrices.scale(MathHelper.clamp(h, 1.0F, 1.2F), MathHelper.clamp(v, 0.25F, 1.0F), MathHelper.clamp(h, 1.0F, 1.2F));
        }
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
        return new Identifier(Main.MOD_ID, "textures/entity/goomba/" + entity.getVariant().asString() + ".png");
    }
}
