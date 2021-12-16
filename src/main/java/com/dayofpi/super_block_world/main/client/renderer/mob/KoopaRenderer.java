package com.dayofpi.super_block_world.main.client.renderer.mob;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.client.model.KoopaModel;
import com.dayofpi.super_block_world.main.client.ModModelLayers;
import com.dayofpi.super_block_world.main.common.entity.mob.koopa.KoopaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class KoopaRenderer<T extends KoopaEntity> extends MobEntityRenderer<T, KoopaModel<T>> {
    private static final Identifier GREEN = new Identifier(Main.MOD_ID, "textures/entity/koopa_troopa/green.png");
    private static final Identifier GREEN_ANGRY = new Identifier(Main.MOD_ID, "textures/entity/koopa_troopa/green_angry.png");
    private static final Identifier RED = new Identifier(Main.MOD_ID, "textures/entity/koopa_troopa/red.png");
    private static final Identifier RED_ANGRY = new Identifier(Main.MOD_ID, "textures/entity/koopa_troopa/red_angry.png");

    public KoopaRenderer(EntityRendererFactory.Context context) {
        super(context, new KoopaModel<>(context.getPart(ModModelLayers.KOOPA)), 0.4F);
        this.addFeature(new SaddleFeatureRenderer<>(this, new KoopaModel<>(context.getPart(ModModelLayers.KOOPA_SADDLE)), new Identifier(Main.MOD_ID, "textures/entity/koopa_troopa/saddle.png")));
    }

    @Override
    public Identifier getTexture(T entity) {
        if (entity.getKoopaType() == 0)
            return entity.hasAngerTime() ? GREEN_ANGRY : GREEN;
        else
            return entity.hasAngerTime() ? RED_ANGRY : RED;
    }
}
