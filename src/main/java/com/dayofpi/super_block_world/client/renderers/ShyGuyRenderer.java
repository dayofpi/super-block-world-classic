package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.ShyGuyItemRenderer;
import com.dayofpi.super_block_world.client.models.ShyGuyModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.hostile.ShyGuyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ShyGuyRenderer extends MobEntityRenderer<ShyGuyEntity, ShyGuyModel> {
    public ShyGuyRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ShyGuyModel(ctx.getPart(ModModelLayers.SHY_GUY)), 0.4f);
        this.addFeature(new ShyGuyItemRenderer<>(this, ctx.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(ShyGuyEntity entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/shy_guy/" + entity.getShyGuyType().asString() + ".png");
    }
}
