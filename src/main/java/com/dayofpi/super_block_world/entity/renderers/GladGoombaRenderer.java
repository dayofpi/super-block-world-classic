package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.FlushedFeatureRenderer;
import com.dayofpi.super_block_world.entity.models.GoombaModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.passive.GladGoombaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class GladGoombaRenderer<T extends GladGoombaEntity> extends MobEntityRenderer<T, GoombaModel<T>> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/goomba/glad_goomba.png");

    public GladGoombaRenderer(EntityRendererFactory.Context context) {
        super(context, new GoombaModel<>(context.getPart(ModModelLayers.GOOMBA)), 0.5f);
        this.addFeature(new FlushedFeatureRenderer<>(this));
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }

    @Override
    protected void scale(T entity, MatrixStack matrices, float amount) {
        if (entity.isDead()) {
            float h = MathHelper.lerp(entity.deathTime * 0.15F, 1.0F, 1.2F);
            float v = MathHelper.lerp(entity.deathTime * 0.15F, 1.0F, 0.25F);
            matrices.scale(MathHelper.clamp(h, 1.0F, 1.2F), MathHelper.clamp(v, 0.25F, 1.0F), MathHelper.clamp(h, 1.0F, 1.2F));
        }
        if (entity.isBaby()) {
            matrices.scale(0.5F, 0.5F, 0.5F);
        }
        if (entity.isBig()) {
            matrices.scale(2.5F, 2.5F, 2.5F);
        }
    }

    @Override
    protected float getLyingAngle(T entity) {
        return 0.0F;
    }
}
