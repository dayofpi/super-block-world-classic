package com.dayofpi.sbw_main.client.feature;

import com.dayofpi.sbw_main.client.model.BooModel;
import com.dayofpi.sbw_main.common.entity.type.mobs.BooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;
@Environment(EnvType.CLIENT)
public class BooItem extends FeatureRenderer<BooEntity, BooModel<BooEntity>> {
	public BooItem(FeatureRendererContext<BooEntity, BooModel<BooEntity>> featureRendererContext) {
		super(featureRendererContext);
	}

	public void render(MatrixStack matrixStack, VertexConsumerProvider provider, int light, BooEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		matrixStack.push();
		matrixStack.translate(0.06D, 1.8D, -0.4D);
		matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(entity.getRoll()));
		matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));

		ItemStack itemStack = entity.getEquippedStack(EquipmentSlot.MAINHAND);
		MinecraftClient.getInstance().getHeldItemRenderer().renderItem(entity, itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, provider, light);
		matrixStack.pop();
	}
}
