package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.ModEyesFeatureRenderer;
import com.dayofpi.super_block_world.entity.models.KingBooModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.boss.KingBooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class KingBooRenderer extends MobEntityRenderer<KingBooEntity, KingBooModel> {
    public static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/king_boo/king_boo.png");
    private static final Identifier WEAKENED_TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/king_boo/weakened.png");
    private static final Identifier GLOW = new Identifier(Main.MOD_ID, "textures/entity/king_boo/glow.png");

    public KingBooRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new KingBooModel(ctx.getPart(ModModelLayers.KING_BOO)), 1.5f);
        this.addFeature(new ModEyesFeatureRenderer<>(GLOW, this));
    }

    @Override
    public Identifier getTexture(KingBooEntity entity) {
        if (entity.isWeakened())
            return WEAKENED_TEXTURE;
        return TEXTURE;
    }

    @Nullable
    @Override
    protected RenderLayer getRenderLayer(KingBooEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = this.getTexture(entity);
        return RenderLayer.getEntityTranslucent(identifier);
    }
}
