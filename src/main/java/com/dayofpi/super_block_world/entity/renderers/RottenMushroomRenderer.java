package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.ModEyesFeatureRenderer;
import com.dayofpi.super_block_world.entity.models.RottenMushroomModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.RottenMushroomEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class RottenMushroomRenderer extends MobEntityRenderer<RottenMushroomEntity, RottenMushroomModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/rotten_mushroom.png");
    private static final Identifier EYES = new Identifier(Main.MOD_ID, "textures/entity/rotten_mushroom_eyes.png");
    public RottenMushroomRenderer(EntityRendererFactory.Context context) {
        super(context, new RottenMushroomModel(context.getPart(ModModelLayers.ROTTEN_MUSHROOM)), 0.3f);
        this.addFeature(new ModEyesFeatureRenderer<>(EYES, this));
    }

    @Override
    public Identifier getTexture(RottenMushroomEntity entity) {
        return TEXTURE;
    }
}