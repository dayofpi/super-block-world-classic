package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.BuzzyShellModel;
import com.dayofpi.super_block_world.common.entities.BuzzyShellEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoModel;

@Environment(EnvType.CLIENT)
public class BuzzyShellRenderer<T extends BuzzyShellEntity> extends LeadableEntityRenderer<T> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/shell/buzzy.png");
    private static final Identifier TEXTURE_BUSY = new Identifier(Main.MOD_ID, "textures/entity/shell/buzzy_busy.png");

    public BuzzyShellRenderer(EntityRendererFactory.Context context) {
        super(context, new BuzzyShellModel<>());
        this.shadowRadius = 0.5F;
    }

    @Override
    public void render(GeoModel model, T animatable, float partialTicks, RenderLayer type, MatrixStack matrixStackIn, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        matrixStackIn.scale(0.9f, 0.9f, 0.9f);
        super.render(model, animatable, partialTicks, type, matrixStackIn, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public Identifier getTexture(T entity) {
        return entity.hasMob() ? TEXTURE_BUSY : TEXTURE;
    }
}
