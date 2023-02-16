package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.models.PokeySegmentModel;
import com.dayofpi.super_block_world.entity.entities.hostile.PokeySegmentEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class PokeySegmentRenderer extends MobEntityRenderer<PokeySegmentEntity, PokeySegmentModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/pokey/segment.png");
    private static final Identifier SNOWY_TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/pokey/segment_snow.png");

    public PokeySegmentRenderer(EntityRendererFactory.Context context) {
        super(context, new PokeySegmentModel(context.getPart(ModModelLayers.POKEY_SEGMENT)), 0.6F);
    }

    public Identifier getTexture(PokeySegmentEntity entity) {
        return entity.isSnowy() ? SNOWY_TEXTURE : TEXTURE;
    }
}
