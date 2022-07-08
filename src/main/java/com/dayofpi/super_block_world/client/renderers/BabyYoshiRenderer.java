package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.BabyYoshiModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.passive.BabyYoshiEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BabyYoshiRenderer extends MobEntityRenderer<BabyYoshiEntity, BabyYoshiModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/baby_yoshi/green.png");
    public BabyYoshiRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BabyYoshiModel(ctx.getPart(ModModelLayers.BABY_YOSHI)), 0.5F);
    }

    @Override
    public Identifier getTexture(BabyYoshiEntity entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(BabyYoshiEntity entity, MatrixStack matrices, float amount) {
        matrices.scale(0.6F, 0.6F, 0.6F);
    }
}
