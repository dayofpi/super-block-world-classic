package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.layers.GoombaLayer;
import com.dayofpi.super_block_world.client.models.GoombaModel;
import com.dayofpi.super_block_world.common.entities.hostile.GoombaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class GoombaRenderer<T extends GoombaEntity> extends GeoEntityRenderer<T> {
    private static final Identifier COMMON = new Identifier(Main.MOD_ID, "textures/entity/goomba/goomba.png");
    private static final Identifier GOLD = new Identifier(Main.MOD_ID, "textures/entity/goomba/gold_goomba.png");

    public GoombaRenderer(EntityRendererFactory.Context context) {
        super(context, new GoombaModel<>());
        addLayer(new GoombaLayer<>(this));
    }

    @Override
    public void render(T entity, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int packedLightIn) {
        this.shadowRadius = 0.25F * entity.getSize() + 0.25F;
        super.render(entity, entityYaw, tickDelta, matrices, provider, packedLightIn);
    }

    @Override
    protected void applyRotations(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        super.applyRotations(entity, matrices, animationProgress, bodyYaw, tickDelta);
        if (entity.getSize() == 0) {
            matrices.scale(0.5F, 0.5F, 0.5F);
        } else if (entity.getSize() == 2) {
            matrices.scale(2.5F, 2.5F, 2.5F);
        } else {
            matrices.scale(entity.getSize(), entity.getSize(), entity.getSize());
        }
    }

    protected float getDeathMaxRotation(T entity) {
        return 0.0F;
    }

    @Override
    public Identifier getTexture(T entity) {
        return entity.isGold() ? GOLD : COMMON;
    }
}
