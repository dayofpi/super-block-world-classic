package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.KoopaTroopaModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.passive.AbstractKoopa;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.SaddleFeatureRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class KoopaTroopaRenderer extends MobEntityRenderer<AbstractKoopa, KoopaTroopaModel> {
    private static final Identifier GREEN = new Identifier(Main.MOD_ID, "textures/entity/koopa/green.png");
    private static final Identifier GREEN_ANGRY = new Identifier(Main.MOD_ID, "textures/entity/koopa/green_angry.png");
    private static final Identifier RED = new Identifier(Main.MOD_ID, "textures/entity/koopa/red.png");
    private static final Identifier RED_ANGRY = new Identifier(Main.MOD_ID, "textures/entity/koopa/red_angry.png");
    private static final Identifier SADDLE = new Identifier(Main.MOD_ID, "textures/entity/koopa/saddle.png");

    public KoopaTroopaRenderer(EntityRendererFactory.Context context) {
        super(context, new KoopaTroopaModel(context.getPart(ModModelLayers.KOOPA_TROOPA)), 0.4f);
        this.addFeature(new SaddleFeatureRenderer<>(this, new KoopaTroopaModel(context.getPart(ModModelLayers.KOOPA_TROOPA_SADDLE)), SADDLE));
    }

    @Override
    public Identifier getTexture(AbstractKoopa entity) {
        if (entity.getKoopaColor() == 0)
            return entity.hasAngerTime() ? GREEN_ANGRY : GREEN;
        else
            return entity.hasAngerTime() ? RED_ANGRY : RED;
    }
}
