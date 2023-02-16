package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.ModEyesFeatureRenderer;
import com.dayofpi.super_block_world.entity.models.MummyMeModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.MummyMeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MummyMeRenderer extends MobEntityRenderer<MummyMeEntity, MummyMeModel> {
    private static final Identifier EYES = new Identifier(Main.MOD_ID, "textures/entity/toad/mummy_eyes.png");
    public MummyMeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MummyMeModel(ctx.getPart(ModModelLayers.MUMMY_ME)), 0.5f);
        this.addFeature(new ModEyesFeatureRenderer<>(EYES, this));
    }

    @Override
    protected boolean isShaking(MummyMeEntity entity) {
        return super.isShaking(entity) || entity.isConverting();
    }

    @Override
    public Identifier getTexture(MummyMeEntity entity) {
        return new Identifier(Main.MOD_ID, "textures/entity/toad/mummy.png");
    }
}
