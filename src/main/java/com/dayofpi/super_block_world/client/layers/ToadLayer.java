package com.dayofpi.super_block_world.client.layers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.passive.ToadEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

public class ToadLayer<T extends ToadEntity> extends GeoLayerRenderer<T> {
    private static final Identifier MODEL = new Identifier(Main.MOD_ID, "geo/toad.geo.json");

    public ToadLayer(IGeoRenderer<T> geoRenderer) {
        super(geoRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider provider, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isInvisible() && entity.getColor() != 0) {
            String string = Formatting.strip(entity.getName().getString());
            RenderLayer texture = RenderLayer.getEntityTranslucent(entity.getTexture());
            if ("Lucy".equals(string))
                texture = RenderLayer.getEntityTranslucent(new Identifier(Main.MOD_ID, "textures/entity/toad/lucy.png"));
            VertexConsumer consumer = provider.getBuffer(texture);

            GeoBone all = getEntityModel().getModel(MODEL).topLevelBones.get(0);
            RenderUtils.translate(all, matrixStack);
            RenderUtils.moveToPivot(all, matrixStack);
            RenderUtils.rotate(all, matrixStack);
            RenderUtils.scale(all, matrixStack);
            RenderUtils.moveBackFromPivot(all, matrixStack);
            for (GeoBone childBone : all.childBones) {
                this.getRenderer().renderRecursively(childBone, matrixStack, consumer, light,
                        LivingEntityRenderer.getOverlay(entity, 0.0F), 1f, 1f, 1f, 1f);
            }
        }
    }
}
