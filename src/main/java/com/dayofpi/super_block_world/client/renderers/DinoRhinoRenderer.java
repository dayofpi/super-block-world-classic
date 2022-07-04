package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.DinoRhinoModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.hostile.DinoRhinoEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DinoRhinoRenderer extends MobEntityRenderer<DinoRhinoEntity, DinoRhinoModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/dino_rhino.png");

    public DinoRhinoRenderer(EntityRendererFactory.Context context) {
        super(context, new DinoRhinoModel(context.getPart(ModModelLayers.DINO_RHINO)), 0.6f);

    }

    @Override
    public Identifier getTexture(DinoRhinoEntity entity) {
        return TEXTURE;
    }
}
