package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.MudTrooperModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.hostile.MudTrooperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MudTrooperRenderer extends MobEntityRenderer<MudTrooperEntity, MudTrooperModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/mud_trooper.png");
    public MudTrooperRenderer(EntityRendererFactory.Context context) {
        super(context, new MudTrooperModel(context.getPart(ModModelLayers.MUD_TROOPER)), 0.5f);

    }

    @Override
    public Identifier getTexture(MudTrooperEntity entity) {
        return TEXTURE;
    }
}
