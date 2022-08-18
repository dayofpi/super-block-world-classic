package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.boss.PeteyPiranhaEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class PeteyPiranhaModel extends SinglePartEntityModel<PeteyPiranhaEntity> {
	private final ModelPart root;
	private final ModelPart leftArm;
	private final ModelPart rightArm;
	private final ModelPart head;
	private final ModelPart bottom;
	private final ModelPart top;
	private final ModelPart rightLeg;
	private final ModelPart leftLeg;

	public PeteyPiranhaModel(ModelPart root) {
		this.root = root.getChild("root");
		this.leftArm = this.root.getChild("left_arm");
		this.rightArm = this.root.getChild("right_arm");
		this.rightLeg = this.root.getChild("right_leg");
		this.leftLeg = this.root.getChild("left_leg");
		this.head = this.root.getChild("head");
		this.bottom = this.head.getChild("bottom");
		this.top = this.head.getChild("top");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(38, 52).cuboid(-6.0F, -9.0F, -2.0F, 12.0F, 9.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 18.0F, -4.0F));

		ModelPartData left_arm = root.addChild("left_arm", ModelPartBuilder.create().uv(0, 52).mirrored().cuboid(0.0F, 0.0F, -5.0F, 20.0F, 0.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(4.0F, -10.0F, 4.0F));

		ModelPartData right_arm = root.addChild("right_arm", ModelPartBuilder.create().uv(0, 52).cuboid(-20.0F, 0.0F, -5.0F, 20.0F, 0.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -10.0F, 4.0F));

		ModelPartData neck = root.addChild("neck", ModelPartBuilder.create().uv(30, 73).cuboid(-4.0F, -10.0F, -3.0F, 8.0F, 8.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 4.0F, -0.0873F, 0.0F, 0.0F));

		ModelPartData head = root.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -17.0F, 5.0F));

		ModelPartData bottom = head.addChild("bottom", ModelPartBuilder.create().uv(0, 26).cuboid(-8.0F, 0.0F, -18.0F, 16.0F, 8.0F, 18.0F, new Dilation(0.0F))
		.uv(32, 26).cuboid(-8.0F, 3.0F, -18.0F, 16.0F, 0.0F, 18.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.0F, 3.0F));

		ModelPartData bottom_leaves = bottom.addChild("bottom_leaves", ModelPartBuilder.create().uv(68, 18).cuboid(-8.0F, -4.0F, 0.0F, 16.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 11.0F, -8.0F, -0.4363F, 0.0F, 0.0F));

		ModelPartData tongue = bottom.addChild("tongue", ModelPartBuilder.create().uv(0, 62).cuboid(-4.0F, 0.0F, -1.0F, 8.0F, 3.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, -10.0F));

		ModelPartData top = head.addChild("top", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -8.0F, -18.0F, 16.0F, 8.0F, 18.0F, new Dilation(0.0F))
		.uv(32, 0).cuboid(-8.0F, -3.0F, -18.0F, 16.0F, 0.0F, 18.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.0F, 3.0F));

		ModelPartData top_leaves = top.addChild("top_leaves", ModelPartBuilder.create().uv(68, 44).cuboid(-8.0F, -4.0F, 0.0F, 16.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -11.0F, -8.0F, 0.4363F, 0.0F, 0.0F));

		ModelPartData left_leaves = head.addChild("left_leaves", ModelPartBuilder.create().uv(0, 26).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(11.0F, -7.0F, -5.0F, 0.0F, 0.4363F, 0.0F));

		ModelPartData right_leaves = head.addChild("right_leaves", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, 0.0F, 8.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(-11.0F, -7.0F, -5.0F, 0.0F, -0.4363F, 0.0F));

		ModelPartData right_leg = root.addChild("right_leg", ModelPartBuilder.create().uv(74, 52).cuboid(-3.0F, 0.0F, -3.0F, 5.0F, 6.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, 0.0F, 4.0F));

		ModelPartData left_leg = root.addChild("left_leg", ModelPartBuilder.create().uv(74, 52).mirrored().cuboid(-2.0F, 0.0F, -3.0F, 5.0F, 6.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.5F, 0.0F, 4.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(PeteyPiranhaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

		this.rightArm.yaw = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
		this.leftArm.yaw = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;

		float progress = ageInTicks * 2F * 0.01F;
		this.rightArm.roll = ((MathHelper.cos(progress) * 0.4F) - 0.4F);
		this.leftArm.roll = ((MathHelper.cos(progress) * -0.4F) + 0.4F);

		this.top.pitch = MathHelper.cos(progress) * 0.1F;
		this.bottom.pitch = MathHelper.cos(entity.limbAngle * 0.6662F) * 0.4F * entity.limbDistance;
		this.root.pitch = MathHelper.cos(entity.limbAngle * 0.6662F) * 0.4F * entity.limbDistance;

		this.head.pitch = headPitch * ((float) Math.PI / 180F) * 0.5F;
		this.head.roll = headPitch * ((float) Math.PI / 180F) * 0.2F;
		this.head.yaw = netHeadYaw * ((float) Math.PI / 180F) * 0.5F;

		this.rightLeg.pitch = MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance;
		this.leftLeg.pitch = MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}