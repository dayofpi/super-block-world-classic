package com.dayofpi.super_block_world.main.client.renderer.mob;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.client.model.GoombaModel;
import com.dayofpi.super_block_world.main.client.ModelLayers;
import com.dayofpi.super_block_world.main.common.entity.type.mobs.GoombaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class GoombaRenderer<T extends GoombaEntity> extends MobEntityRenderer<T, GoombaModel<T>> {
    private static final Identifier COMMON = new Identifier(Main.MOD_ID, "textures/entity/goomba/goomba.png");
    private static final Identifier GOLD = new Identifier(Main.MOD_ID, "textures/entity/goomba/gold_goomba.png");

    public GoombaRenderer(EntityRendererFactory.Context context) {
        super(context, new GoombaModel<>(context.getPart(ModelLayers.GOOMBA)), 0.4F);
    }

    @Override
    protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        if (entity.getSize() == 0) {
            matrices.scale(0.5F, 0.5F, 0.5F);
        } else if (entity.getSize() == 2) {
            matrices.scale(2.5F, 2.5F, 2.5F);
        } else {
            matrices.scale(entity.getSize(), entity.getSize(), entity.getSize());
        }
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
    }

    @Override
    public Identifier getTexture(T entity) {
        return entity.isGold() ? GOLD : COMMON;
    }
}
