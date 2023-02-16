package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.SpinyModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.SpinyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SpinyRenderer extends MobEntityRenderer<SpinyEntity, SpinyModel> {
    public SpinyRenderer(EntityRendererFactory.Context context) {
        super(context, new SpinyModel(context.getPart(ModModelLayers.SPINY)), 0.5f);
    }

    @Override
    protected float getLyingAngle(SpinyEntity entity) {
        return 180.0f;
    }

    @Override
    public Identifier getTexture(SpinyEntity entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/spiny.png");
    }
}
