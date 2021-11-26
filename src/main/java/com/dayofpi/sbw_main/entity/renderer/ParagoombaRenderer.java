package com.dayofpi.sbw_main.entity.renderer;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.entity.model.ParagoombaModel;
import com.dayofpi.sbw_main.entity.registry.ModelLayers;
import com.dayofpi.sbw_main.entity.types.mobs.ParagoombaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
@Environment(EnvType.CLIENT)
public class ParagoombaRenderer<T extends ParagoombaEntity> extends MobEntityRenderer<T, ParagoombaModel<T>> {
    private static final Identifier COMMON = new Identifier(Main.MOD_ID, "textures/entity/goomba/goomba.png");
    private static final Identifier GOLD = new Identifier(Main.MOD_ID, "textures/entity/goomba/gold_goomba.png");

    public ParagoombaRenderer(EntityRendererFactory.Context context) {
        super(context, new ParagoombaModel<>(context.getPart(ModelLayers.PARAGOOMBA)), 0.4F);
    }

    @Override
    protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        if (entity.getSize() == 0) {
            matrices.scale(0.5F, 0.5F, 0.5F);
        } else if (entity.getSize() == 2) {
            matrices.scale(2.5F, 2.5F, 2.5F);
        }
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
    }

    protected int getBlockLight(T goombaEntity, BlockPos blockPos) {
        return goombaEntity.isGold() ? 10 : super.getBlockLight(goombaEntity, blockPos);
    }

    @Override
    public Identifier getTexture(T entity) {
        return entity.isGold() ? GOLD : COMMON;
    }
}
