package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.ModEyesFeatureRenderer;
import com.dayofpi.super_block_world.client.models.KoopaShellModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.KoopaVariant;
import com.dayofpi.super_block_world.common.entities.misc.KoopaShellEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

public class KoopaShellRenderer extends MobEntityRenderer<KoopaShellEntity, KoopaShellModel> {
    private static final Identifier BUZZY = new Identifier(Main.MOD_ID, "textures/entity/shell/buzzy.png");
    private static final Identifier EYES = new Identifier(Main.MOD_ID, "textures/entity/shell/eyes.png");

    public KoopaShellRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new KoopaShellModel(ctx.getPart(ModModelLayers.KOOPA_SHELL)), 0.5F);
        this.addFeature(new ModEyesFeatureRenderer<>(EYES, this));
    }

    @Override
    protected boolean isShaking(KoopaShellEntity entity) {
        return super.isShaking(entity) || entity.getExitTime() > (KoopaShellEntity.MAX_EXIT_TIME - 30);
    }

    @Override
    public Identifier getTexture(KoopaShellEntity entity) {
        int variant = entity.getVariant();
        if (variant < 0)
            return BUZZY;
        return new Identifier(Main.MOD_ID, "textures/entity/shell/" + KoopaVariant.getName(variant) + ".png");
    }
}
