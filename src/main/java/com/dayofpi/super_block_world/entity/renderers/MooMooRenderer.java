package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.entity.models.MooMooModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.passive.MooMooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MooMooRenderer extends MobEntityRenderer<MooMooEntity, MooMooModel> {
    public MooMooRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MooMooModel(ctx.getPart(ModModelLayers.MOO_MOO)), 0.5f);
    }

    @Override
    public Identifier getTexture(MooMooEntity entity) {
        return entity.getTexture();
    }
}
