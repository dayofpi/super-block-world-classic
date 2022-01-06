package com.dayofpi.super_block_world.client.renderer.feature;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.entity.mob.MummyToadEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;
import software.bernie.geckolib3.util.RenderUtils;

public class MummyToadLayer<T extends MummyToadEntity> extends GeoLayerRenderer<T> {
    private static final Identifier MODEL = new Identifier(Main.MOD_ID, "geo/mummy_toad.geo.json");
    private static final Identifier EYES = new Identifier(Main.MOD_ID, "textures/entity/toad/mummy_eyes.png");
    public MummyToadLayer(IGeoRenderer<T> geoRenderer) {
        super(geoRenderer);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider provider, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (!entity.isHidden()) {
            matrices.push();
            //Copied from GeoLayerRenderer.
            VertexConsumer consumer = provider.getBuffer(RenderLayer.getEyes(EYES));
            GeoBone root = getEntityModel().getModel(MODEL).topLevelBones.get(0);
            RenderUtils.translate(root, matrices);
            RenderUtils.moveToPivot(root, matrices);
            RenderUtils.rotate(root, matrices);
            RenderUtils.scale(root, matrices);
            RenderUtils.moveBackFromPivot(root, matrices);
            for (GeoBone childBone : root.childBones) {
                if (childBone.getName().equals("neck")) continue;
                this.getRenderer().renderRecursively(childBone, matrices, consumer, light,
                        LivingEntityRenderer.getOverlay(entity, 0.0F), 1f, 1f, 1f, 1f);
            }
            matrices.pop();
        }
    }
}
