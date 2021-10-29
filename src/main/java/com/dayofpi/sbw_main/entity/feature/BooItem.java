package com.dayofpi.sbw_main.entity.feature;

import com.dayofpi.sbw_main.entity.model.BooModel;
import com.dayofpi.sbw_main.entity.types.mobs.BooEntity;
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

	public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, BooEntity booEntity, float f, float g, float h, float j, float k, float l) {
		matrixStack.push();
		matrixStack.translate(0.05999999865889549D, 1.8D, -0.4D);
		matrixStack.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(booEntity.getRoll()));
		matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180));

		ItemStack itemStack = booEntity.getEquippedStack(EquipmentSlot.MAINHAND);
		MinecraftClient.getInstance().getHeldItemRenderer().renderItem(booEntity, itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, i);
		matrixStack.pop();
	}
}
