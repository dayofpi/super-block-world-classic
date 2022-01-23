package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.KoopaModel;
import com.dayofpi.super_block_world.client.renderers.layers.KoopaLayer;
import com.dayofpi.super_block_world.common.entities.mob.KoopaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class KoopaRenderer<T extends KoopaEntity> extends LeadableEntityRenderer<T> {
    private static final Identifier GREEN = new Identifier(Main.MOD_ID, "textures/entity/koopa/green.png");
    private static final Identifier GREEN_ANGRY = new Identifier(Main.MOD_ID, "textures/entity/koopa/green_angry.png");
    private static final Identifier RED = new Identifier(Main.MOD_ID, "textures/entity/koopa/red.png");
    private static final Identifier RED_ANGRY = new Identifier(Main.MOD_ID, "textures/entity/koopa/red_angry.png");

    public KoopaRenderer(EntityRendererFactory.Context context) {
        super(context, new KoopaModel<>());
        this.shadowRadius = 0.4F;
        addLayer(new KoopaLayer<>(this));
    }

    @Override
    public Identifier getTexture(T entity) {
        if (entity.getKoopaType() == 0)
            return entity.hasAngerTime() ? GREEN_ANGRY : GREEN;
        else
            return entity.hasAngerTime() ? RED_ANGRY : RED;
    }
}
