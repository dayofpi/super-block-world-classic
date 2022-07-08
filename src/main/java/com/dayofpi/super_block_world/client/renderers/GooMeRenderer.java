package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.misc.GooMeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class GooMeRenderer extends LivingEntityRenderer<GooMeEntity, PlayerEntityModel<GooMeEntity>> {
    public static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/goo_me.png");

    public GooMeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PlayerEntityModel<>(ctx.getPart(EntityModelLayers.PLAYER), false), 0.5f);
    }

    @Override
    protected void scale(GooMeEntity entity, MatrixStack matrixStack, float f) {
        matrixStack.scale(0.9375f, 0.9375f, 0.9375f);
    }

    @Override
    public Identifier getTexture(GooMeEntity entity) {
        return entity.getSkinTexture() == null ? TEXTURE : entity.getSkinTexture();
    }
}
