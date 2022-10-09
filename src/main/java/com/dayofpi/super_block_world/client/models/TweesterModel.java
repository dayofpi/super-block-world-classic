package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.hostile.TweesterEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class TweesterModel<T extends TweesterEntity> extends EntityModel<T> {
	private final ModelPart root;
	private final ModelPart face;
	private final ModelPart bottom;
	private final ModelPart middle;
	private final ModelPart top;

	public TweesterModel(ModelPart root) {
		this.root = root.getChild("root");
		this.face = this.root.getChild("face");
		this.bottom = this.root.getChild("bottom");
		this.middle = this.root.getChild("middle");
		this.top = this.root.getChild("top");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		root.addChild("face", ModelPartBuilder.create().uv(100, 0).cuboid(-7.0F, -6.0F, 0.0F, 14.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -20.0F, -9.0F));
		root.addChild("bottom", ModelPartBuilder.create().uv(40, 38).cuboid(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.0F, 0.0F));
		root.addChild("middle", ModelPartBuilder.create().uv(0, 24).cuboid(-6.0F, -5.0F, -6.0F, 12.0F, 10.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.0F, 0.0F));
		root.addChild("top", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -4.0F, -8.0F, 16.0F, 8.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -19.0F, 0.0F));

		return TexturedModelData.of(modelData, 128, 64);
	}

	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float progress = ageInTicks * 5F * 0.05F;
		this.top.pivotY = -19.0F + MathHelper.cos(progress) * 2F;
		this.face.pivotY = -20.0F + MathHelper.cos(progress * 0.5F) * 5F;
		this.face.roll = 0.0F;
		this.top.yaw += 0.1F;
		this.middle.yaw += 0.2F;
		this.bottom.yaw += 0.4F;
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		this.root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}
}