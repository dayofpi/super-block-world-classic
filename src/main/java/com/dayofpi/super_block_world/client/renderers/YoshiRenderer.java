package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.YoshiModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.passive.YoshiEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class YoshiRenderer extends MobEntityRenderer<YoshiEntity, YoshiModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/yoshi/green.png");

    public YoshiRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new YoshiModel(ctx.getPart(ModModelLayers.YOSHI)), 0.6F);
    }

    @Override
    public Identifier getTexture(YoshiEntity entity) {
        return TEXTURE;
    }
}
