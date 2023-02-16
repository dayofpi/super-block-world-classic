package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.models.SnowPokeyModel;
import com.dayofpi.super_block_world.entity.entities.hostile.SnowPokeyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SnowPokeyRenderer extends MobEntityRenderer<SnowPokeyEntity, SnowPokeyModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/pokey/snow_pokey.png");

    public SnowPokeyRenderer(EntityRendererFactory.Context context) {
        super(context, new SnowPokeyModel(context.getPart(ModModelLayers.SNOW_POKEY)), 0.6F);
    }

    public Identifier getTexture(SnowPokeyEntity entity) {
        return TEXTURE;
    }
}
