package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.layers.MummyMeLayer;
import com.dayofpi.super_block_world.client.models.MummyMeModel;
import com.dayofpi.super_block_world.common.entities.hostile.MummyMeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class MummyMeRenderer<T extends MummyMeEntity> extends GeoEntityRenderer<T> {
    public MummyMeRenderer(EntityRendererFactory.Context context) {
        super(context, new MummyMeModel<>());
        this.addLayer(new MummyMeLayer<>(this));
        this.shadowRadius = 0.5f;
    }

    @Override
    public Identifier getTexture(T entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/toad/mummy.png");
    }
}
