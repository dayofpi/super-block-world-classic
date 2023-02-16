package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.models.PokeyModel;
import com.dayofpi.super_block_world.entity.entities.hostile.PokeyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PokeyRenderer extends MobEntityRenderer<PokeyEntity, PokeyModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/pokey/pokey.png");

    public PokeyRenderer(EntityRendererFactory.Context context) {
        super(context, new PokeyModel(context.getPart(ModModelLayers.POKEY)), 0.6F);
    }

    public Identifier getTexture(PokeyEntity entity) {
        return TEXTURE;
    }
}
