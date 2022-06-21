package com.dayofpi.super_block_world.client.renderers.layers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.BuzzyModel;
import com.dayofpi.super_block_world.common.entities.abst.AbstractBuzzy;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class BuzzyEyes<T extends AbstractBuzzy, M extends BuzzyModel<T>> extends EyesFeatureRenderer<T, M> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(Main.MOD_ID, "textures/entity/buzzy_beetle/buzzy_eyes.png"));

    public BuzzyEyes(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}
