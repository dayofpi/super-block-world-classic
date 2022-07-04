package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.BooFaceFeatureRenderer;
import com.dayofpi.super_block_world.client.models.BooModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.hostile.BooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

@Environment(EnvType.CLIENT)
public class BooRenderer extends MobEntityRenderer<BooEntity, BooModel> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/boo/boo.png");
    private static final Identifier SHY_TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/boo/boo_shy.png");

    public BooRenderer(EntityRendererFactory.Context context) {
        super(context, new BooModel(context.getPart(ModModelLayers.BOO)), 0.5f);
        this.addFeature(new BooFaceFeatureRenderer<>(this));
        this.addFeature(new HeldItemFeatureRenderer<BooEntity, BooModel>(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(BooEntity entity) {
        return TEXTURE;
    }

    @Nullable
    @Override
    protected RenderLayer getRenderLayer(BooEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier identifier = this.getTexture(entity);
        return RenderLayer.getEntityTranslucent(identifier);
    }
}
