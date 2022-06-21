package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.PiranhaPlantModel;
import com.dayofpi.super_block_world.common.entities.hostile.PiranhaPlantEntity;
import com.dayofpi.super_block_world.common.entities.hostile.PutridPiranhaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class PiranhaPlantRenderer<T extends PiranhaPlantEntity> extends GeoEntityRenderer<T> {
    private static final Identifier PUTRID_PIRANHA_TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/putrid_piranha.png");

    public PiranhaPlantRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new PiranhaPlantModel<>());
        this.shadowRadius = 0.5F;
    }

    @Override
    public Identifier getTexture(T entity) {
        if (entity instanceof PutridPiranhaEntity)
            return PUTRID_PIRANHA_TEXTURE;
        return super.getTexture(entity);
    }

    @Override
    public RenderLayer getRenderType(T animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityCutoutNoCull(textureLocation);
    }
}
