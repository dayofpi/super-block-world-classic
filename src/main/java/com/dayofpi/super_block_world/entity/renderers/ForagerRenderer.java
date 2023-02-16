package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.ForagerEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.IllagerEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.util.Identifier;

@Environment(value= EnvType.CLIENT)
public class ForagerRenderer extends IllagerEntityRenderer<ForagerEntity> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/forager.png");

    public ForagerRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new IllagerEntityModel<>(ctx.getPart(ModModelLayers.FORAGER)), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<>(this, ctx.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(ForagerEntity entity) {
        return TEXTURE;
    }
}
