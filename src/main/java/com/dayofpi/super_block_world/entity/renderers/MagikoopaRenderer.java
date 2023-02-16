package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.entity.models.MagikoopaModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.MagikoopaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MagikoopaRenderer extends MobEntityRenderer<MagikoopaEntity, MagikoopaModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/magikoopa.png");

    public MagikoopaRenderer(EntityRendererFactory.Context context) {
        super(context, new MagikoopaModel(context.getPart(ModModelLayers.MAGIKOOPA)), 0.5f);
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(MagikoopaEntity entity) {
        return TEXTURE;
    }
}
