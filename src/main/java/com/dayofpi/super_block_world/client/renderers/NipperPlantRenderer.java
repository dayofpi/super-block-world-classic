package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.NipperPlantModel;
import com.dayofpi.super_block_world.client.main.ModelLayers;
import com.dayofpi.super_block_world.common.entities.mob.NipperPlantEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class NipperPlantRenderer<T extends NipperPlantEntity> extends MobEntityRenderer<T, NipperPlantModel<T>> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/nipper_plant.png");

    public NipperPlantRenderer(EntityRendererFactory.Context context) {
        super(context, new NipperPlantModel<>(context.getPart(ModelLayers.NIPPER_PLANT)), 0.2F);
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }
}
