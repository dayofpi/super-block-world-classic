package com.dayofpi.sbw_main.client.feature;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.client.model.BuzzyModel;
import com.dayofpi.sbw_main.common.entity.type.bases.AbstractBuzzy;
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
