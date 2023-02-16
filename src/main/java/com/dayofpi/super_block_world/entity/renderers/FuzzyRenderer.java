package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.FuzzyModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.FuzzyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class FuzzyRenderer extends MobEntityRenderer<FuzzyEntity, FuzzyModel<FuzzyEntity>> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/fuzzy.png");
    public FuzzyRenderer(EntityRendererFactory.Context context) {
        super(context, new FuzzyModel<>(context.getPart(ModModelLayers.FUZZY)), 0.3f);
    }

    @Override
    public Identifier getTexture(FuzzyEntity entity) {
        return TEXTURE;
    }
}
