package com.dayofpi.super_block_world.client.renderer.mob;

import com.dayofpi.super_block_world.client.model.MummyToadModel;
import com.dayofpi.super_block_world.client.renderer.feature.MummyToadLayer;
import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.entity.mob.MummyToadEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class MummyToadRenderer<T extends MummyToadEntity> extends GeoEntityRenderer<T> {
    public MummyToadRenderer(EntityRendererFactory.Context context) {
        super(context, new MummyToadModel<>());
        this.addLayer(new MummyToadLayer<>(this));
        this.shadowRadius = 0.5f;
    }

    @Override
    public Identifier getTexture(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/toad/mummy.png");
    }
}
