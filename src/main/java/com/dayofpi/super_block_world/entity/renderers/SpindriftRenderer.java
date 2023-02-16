package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.SpindriftModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.passive.SpindriftEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class SpindriftRenderer<T extends SpindriftEntity> extends MobEntityRenderer<T, SpindriftModel<T>> {
    public SpindriftRenderer(EntityRendererFactory.Context context) {
        super(context, new SpindriftModel<>(context.getPart(ModModelLayers.SPINDRIFT)), 0.4f);
    }

    @Override
    public Identifier getTexture(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/spindrift.png");
    }
}
