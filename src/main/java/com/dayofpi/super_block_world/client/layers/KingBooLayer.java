package com.dayofpi.super_block_world.client.layers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.boss.KingBooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

@Environment(EnvType.CLIENT)
public class KingBooLayer<T extends KingBooEntity> extends GeoLayerRenderer<T> {
    private static final Identifier MODEL = new Identifier(Main.MOD_ID, "geo/king_boo.geo.json");
    private static final Identifier GLOW = new Identifier(Main.MOD_ID, "textures/entity/king_boo/glow.png");

    public KingBooLayer(IGeoRenderer<T> geoRenderer) {
        super(geoRenderer);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider provider, int light, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        VertexConsumer consumer = provider.getBuffer(RenderLayer.getEyes(GLOW));
        GeoBone root = getEntityModel().getModel(MODEL).topLevelBones.get(0);
        this.getRenderer().renderRecursively(root, matrices, consumer, light, LivingEntityRenderer.getOverlay(entity, 0.0F), 1f, 1f, 1f, 1f);
    }
}
