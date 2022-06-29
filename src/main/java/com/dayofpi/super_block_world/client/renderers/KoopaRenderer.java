package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.layers.KoopaLayer;
import com.dayofpi.super_block_world.client.models.KoopaTroopaModel;
import com.dayofpi.super_block_world.common.entities.passive.KoopaTroopaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class KoopaRenderer<T extends KoopaTroopaEntity> extends GeoEntityRenderer<T> {
    private static final Identifier GREEN = new Identifier(Main.MOD_ID, "textures/entity/koopa/green.png");
    private static final Identifier GREEN_ANGRY = new Identifier(Main.MOD_ID, "textures/entity/koopa/green_angry.png");
    private static final Identifier RED = new Identifier(Main.MOD_ID, "textures/entity/koopa/red.png");
    private static final Identifier RED_ANGRY = new Identifier(Main.MOD_ID, "textures/entity/koopa/red_angry.png");

    public KoopaRenderer(EntityRendererFactory.Context context) {
        super(context, new KoopaTroopaModel<>());
        this.shadowRadius = 0.4F;
        addLayer(new KoopaLayer<>(this));
    }

    @Override
    public Identifier getTexture(T entity) {
        if (entity.getKoopaColor() == 0)
            return entity.hasAngerTime() ? GREEN_ANGRY : GREEN;
        else
            return entity.hasAngerTime() ? RED_ANGRY : RED;
    }
}
