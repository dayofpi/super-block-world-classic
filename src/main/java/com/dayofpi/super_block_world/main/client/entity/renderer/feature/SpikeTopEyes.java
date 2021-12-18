package com.dayofpi.super_block_world.main.client.entity.renderer.feature;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.client.entity.model.BuzzyModel;
import com.dayofpi.super_block_world.main.common.entity.mob.buzzy.AbstractBuzzy;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class SpikeTopEyes<T extends AbstractBuzzy, M extends BuzzyModel<T>> extends EyesFeatureRenderer<T, M> {
    private static final RenderLayer SKIN = RenderLayer.getEyes(new Identifier(Main.MOD_ID, "textures/entity/buzzy_beetle/spike_top_eyes.png"));

    public SpikeTopEyes(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);
    }

    public RenderLayer getEyesTexture() {
        return SKIN;
    }
}
