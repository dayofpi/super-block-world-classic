package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.hostile.BooEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class BooModel extends SinglePartEntityModel<BooEntity> implements ModelWithArms {
	private final ModelPart root;
	private final ModelPart tongue;
	private final ModelPart rightArm;
	private final ModelPart leftArm;
	private float alpha;

	public BooModel(ModelPart root) {
		this.root = root.getChild(EntityModelPartNames.ROOT);
		this.tongue = this.root.getChild(EntityModelPartNames.TONGUE);
		this.rightArm = this.root.getChild(EntityModelPartNames.RIGHT_ARM);
		this.leftArm = this.root.getChild(EntityModelPartNames.LEFT_ARM);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		root.addChild(EntityModelPartNames.TONGUE, ModelPartBuilder.create().uv(19, 25).cuboid(-1.5F, -0.5F, -7.0F, 3.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.5F, -4.0F));
		root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(0, 24).cuboid(-7.5F, -2.0F, -2.0F, 5.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, -5.0F, 0.0F));
		root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(0, 24).mirrored().cuboid(2.5F, -2.0F, -2.0F, 5.0F, 4.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(3.5F, -5.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}

	@Override
	public void setAngles(BooEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.alpha = entity.getAlpha();
		this.tongue.visible = !entity.isInSittingPose() && !entity.isClientShy();
		float progress = ageInTicks * 5F * 0.04F;
		this.tongue.yaw = MathHelper.cos(progress * 0.5F) * 0.05F;
		this.tongue.pitch = 0.2F + MathHelper.cos(progress * 0.5F) * 0.05F;
		if (!entity.getMainHandStack().isEmpty() || entity.isClientShy()) {
			this.leftArm.yaw = 1.3F;
			this.rightArm.yaw = -1.3F;
			this.leftArm.pitch = -1.2F;
			this.rightArm.pitch = -1.2F;
		} else {
			this.leftArm.yaw = 0F;
			this.rightArm.yaw = 0F;
			this.leftArm.pitch = 0F;
			this.rightArm.pitch = 0F;
		}
		if (entity.getLastAttackTime() > (entity.age - 100)) {
			this.root.roll = MathHelper.cos(progress) * 0.1F;
			this.leftArm.roll = -0.5F;
			this.rightArm.roll = 0.5F;
		} else {
			this.root.roll = netHeadYaw * ((float) Math.PI / 180F) * 0.1F;
		}
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		super.render(matrices, vertices, light, overlay, red, green, blue, this.alpha - 0.1f);
	}

	@Override
	public void setArmAngle(Arm arm, MatrixStack matrices) {
		this.root.rotate(matrices);
		matrices.translate(0.0, -0.4, -0.09375);
		matrices.multiply(RotationAxis.POSITIVE_X.rotation(this.rightArm.pitch));
		matrices.scale(0.7f, 0.7f, 0.7f);
	}
}