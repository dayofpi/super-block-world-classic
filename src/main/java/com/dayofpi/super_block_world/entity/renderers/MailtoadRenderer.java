package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.ToadSkinFeatureRenderer;
import com.dayofpi.super_block_world.entity.models.ToadModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.passive.MailtoadEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class MailtoadRenderer extends MobEntityRenderer<MailtoadEntity, ToadModel<MailtoadEntity>> {
    public MailtoadRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ToadModel<>(ctx.getPart(ModModelLayers.TOAD)), 0.5f);
        this.addFeature(new ToadSkinFeatureRenderer<>(this));
    }

    @Override
    protected boolean isShaking(MailtoadEntity entity) {
        return super.isShaking(entity) || entity.isScared();
    }

    @Override
    public Identifier getTexture(MailtoadEntity entity) {
        if (entity.isScared())
            return new Identifier(Main.MOD_ID, "textures/entity/toad/toad_scared.png");
        else if (entity.isHappy())
            return new Identifier(Main.MOD_ID, "textures/entity/toad/toad_happy.png");
        if (entity.isAnnoyed())
            return new Identifier(Main.MOD_ID, "textures/entity/toad/toad_annoyed.png");
        else
            return new Identifier(Main.MOD_ID, "textures/entity/toad/toad.png");
    }
}
