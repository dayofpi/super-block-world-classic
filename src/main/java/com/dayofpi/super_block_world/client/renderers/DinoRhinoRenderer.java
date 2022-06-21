package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.DinoRhinoModel;
import com.dayofpi.super_block_world.common.entities.hostile.DinoRhinoEntity;
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
public class DinoRhinoRenderer<T extends DinoRhinoEntity> extends ModEntityRenderer<T> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/dino_rhino.png");

    public DinoRhinoRenderer(EntityRendererFactory.Context context) {
        super(context, new DinoRhinoModel<>());
        this.shadowRadius = 0.6F;
    }

    @Override
    public void render(GeoModel model, T entity, float partialTicks, RenderLayer type, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        if (entity.isBaby()) {
            stack.scale(0.5F, 0.5F, 0.5F);
        }
        super.render(model, entity, partialTicks, type, stack, renderTypeBuffer, vertexBuilder, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }
}
