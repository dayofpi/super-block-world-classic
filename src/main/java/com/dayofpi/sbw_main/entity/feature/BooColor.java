package com.dayofpi.sbw_main.entity.feature;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.entity.model.BooModel;
import com.dayofpi.sbw_main.entity.types.mobs.BooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BooColor extends FeatureRenderer<BooEntity, BooModel<BooEntity>> {
	private static final Identifier COLOR = new Identifier(Main.MOD_ID, "textures/entity/boo/color.png");
	private static final Identifier COLOR_SITTING = new Identifier(Main.MOD_ID, "textures/entity/boo/color_sitting.png");

	public BooColor(FeatureRendererContext<BooEntity, BooModel<BooEntity>> featureRendererContext) {
		super(featureRendererContext);
	}

	public void render(MatrixStack matrixStack, VertexConsumerProvider provider, int light, BooEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		if (entity.isTamed() && !entity.isInvisible()) {
			float[] fs = entity.getBooColor().getColorComponents();
			VertexConsumer consumer = provider.getBuffer(RenderLayer.getEntityTranslucent(COLOR));

			if (entity.isInSittingPose() && entity.getBooFace() != 3) {
				consumer = provider.getBuffer(RenderLayer.getEntityTranslucent(COLOR_SITTING));
			}

			this.getContextModel().render(matrixStack, consumer, light,
					LivingEntityRenderer.getOverlay(entity, 0.0F),
					fs[0], fs[1], fs[2], 0.75F);
		}
	}
}
