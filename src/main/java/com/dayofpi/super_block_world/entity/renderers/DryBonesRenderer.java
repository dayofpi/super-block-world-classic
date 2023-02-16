package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.ModEyesFeatureRenderer;
import com.dayofpi.super_block_world.entity.models.DryBonesModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.DryBonesEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class DryBonesRenderer extends MobEntityRenderer<DryBonesEntity, DryBonesModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/dry_bones.png");
    private static final Identifier EYES = new Identifier(Main.MOD_ID, "textures/entity/dry_bones_eyes.png");

    public DryBonesRenderer(EntityRendererFactory.Context context) {
        super(context, new DryBonesModel(context.getPart(ModModelLayers.DRY_BONES)), 0.4f);
        this.addFeature(new ModEyesFeatureRenderer<>(EYES, this));
    }

    @Override
    protected float getLyingAngle(DryBonesEntity entity) {
        return 0.0f;
    }

    @Override
    public Identifier getTexture(DryBonesEntity entity) {
        return TEXTURE;
    }
}
