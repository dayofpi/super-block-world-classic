package com.dayofpi.super_block_world.client.renderers.layers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.RottenMushroomModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class RottenMushroomEyes<T extends Entity, M extends RottenMushroomModel<T>> extends EyesFeatureRenderer<T, M> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(Main.MOD_ID, "textures/entity/rotten_mushroom_eyes.png"));

    public RottenMushroomEyes(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}