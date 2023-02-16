package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.hostile.BulletBillEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class BulletBillModel extends EntityModel<BulletBillEntity> {
	private final ModelPart root;
	public BulletBillModel(ModelPart root) {
		this.root = root.getChild(EntityModelPartNames.ROOT);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create().uv(0, 20).cuboid(-3.0F, -7.0F, -7.0F, 6.0F, 6.0F, 1.0F, new Dilation(0.0F))
		.uv(0, 0).cuboid(-4.0F, -8.0F, -6.0F, 8.0F, 8.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void setAngles(BulletBillEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}