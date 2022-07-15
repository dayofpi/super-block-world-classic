package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.passive.SleepySheepEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class SleepySheepModel extends SinglePartEntityModel<SleepySheepEntity> {
	private float headPitchModifier;

	private final ModelPart root;
	private final ModelPart wool;
	private final ModelPart head;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart rightHindLeg;
	private final ModelPart leftHindLeg;

	public SleepySheepModel(ModelPart root) {
		this.root = root.getChild("root");
		this.wool = this.root.getChild("wool");
		this.head = this.root.getChild(EntityModelPartNames.HEAD);
		this.leftFrontLeg = this.root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
		this.rightFrontLeg = this.root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
		this.leftHindLeg = this.root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
		this.rightHindLeg = this.root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -3.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)).uv(20, 16).cuboid(2.0F, -9.0F, -4.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F)).uv(20, 16).mirrored().cuboid(-6.0F, -9.0F, -4.0F, 4.0F, 5.0F, 5.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		root.addChild("wool", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0F, -10.0F, -3.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
		root.addChild("head", ModelPartBuilder.create().uv(0, 16).cuboid(-2.0F, -2.5F, -5.0F, 4.0F, 5.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -5.5F, -3.0F));
		root.addChild("left_front_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -4.0F, -1.0F));
		root.addChild("right_front_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -4.0F, -1.0F));
		root.addChild("right_hind_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -4.0F, 3.0F));
		root.addChild("left_hind_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, -4.0F, 3.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void animateModel(SleepySheepEntity entity, float limbAngle, float limbDistance, float tickDelta) {
		super.animateModel(entity, limbAngle, limbDistance, tickDelta);
		this.headPitchModifier = entity.getHeadAngle(tickDelta);
	}

	@Override
	public void setAngles(SleepySheepEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.wool.visible = !entity.isSheared();
		this.rightHindLeg.pitch = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
		this.leftHindLeg.pitch = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.4f * limbSwingAmount;
		this.rightFrontLeg.pitch = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.4f * limbSwingAmount;
		this.leftFrontLeg.pitch = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
		this.head.yaw = netHeadYaw * ((float)Math.PI / 180);
		this.head.pitch = this.headPitchModifier;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}