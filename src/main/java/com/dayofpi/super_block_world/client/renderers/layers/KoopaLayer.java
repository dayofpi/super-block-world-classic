package com.dayofpi.super_block_world.client.renderers.layers;

import com.dayofpi.super_block_world.Main;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

public class KoopaLayer<T extends MobEntity & IAnimatable> extends GeoLayerRenderer<T> {
    private static final Identifier MODEL = new Identifier(Main.MOD_ID, "geo/koopa.geo.json");
    public KoopaLayer(IGeoRenderer<T> geoRenderer) {
        super(geoRenderer);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider provider, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!((Saddleable)entity).isSaddled()) {
            return;
        }
        RenderLayer texture =  RenderLayer.getEntityCutoutNoCull(new Identifier(Main.MOD_ID, "textures/entity/koopa/saddle.png"));

        VertexConsumer consumer = provider.getBuffer(texture);
        GeoBone all = getEntityModel().getModel(MODEL).topLevelBones.get(0);
        RenderUtils.translate(all, matrixStack);
        RenderUtils.moveToPivot(all, matrixStack);
        RenderUtils.rotate(all, matrixStack);
        RenderUtils.scale(all, matrixStack);
        RenderUtils.moveBackFromPivot(all, matrixStack);
        for (GeoBone childBone : all.childBones) {
            //Makes sure that only the head main group is being rendered since that the only part that lights in the
            //eye layer.
            this.getRenderer().renderRecursively(childBone, matrixStack, consumer, light,
                    LivingEntityRenderer.getOverlay(entity, 0.0F), 1f, 1f, 1f, 1f);
        }
    }
}
