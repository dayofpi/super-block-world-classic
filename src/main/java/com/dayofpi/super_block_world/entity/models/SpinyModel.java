package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.hostile.SpinyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class SpinyModel extends SinglePartEntityModel<SpinyEntity> {
	private final ModelPart root;
	private final ModelPart leftFrontLeg;
	private final ModelPart rightFrontLeg;
	private final ModelPart leftHindLeg;
	private final ModelPart rightHindLeg;

	public SpinyModel(ModelPart root) {
		this.root = root.getChild(EntityModelPartNames.ROOT);
		this.rightFrontLeg = this.root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
		this.rightHindLeg = this.root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
		this.leftFrontLeg = this.root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
		this.leftHindLeg = this.root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -10.0F, -7.0F, 14.0F, 6.0F, 14.0F, new Dilation(0.0F)).uv(0, 20).cuboid(-5.0F, -6.0F, -8.0F, 10.0F, 4.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		root.addChild("spikes_2", ModelPartBuilder.create().uv(36, 20).cuboid(-7.0F, -4.0F, 0.0F, 14.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -14.0F, 0.0F, 0.0F, 0.7854F, 0.0F));
		root.addChild("spikes_1", ModelPartBuilder.create().uv(36, 20).cuboid(-7.0F, -4.0F, 0.0F, 14.0F, 8.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -14.0F, 0.0F, 0.0F, -0.7854F, 0.0F));
		root.addChild("left_front_leg", ModelPartBuilder.create().uv(0, 40).cuboid(-2.0F, 0.0F, -2.5F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -4.0F, -3.5F));
		root.addChild("right_front_leg", ModelPartBuilder.create().uv(0, 40).cuboid(-2.0F, 0.0F, -2.5F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -4.0F, -3.5F));
		root.addChild("left_hind_leg", ModelPartBuilder.create().uv(0, 40).cuboid(-2.0F, 0.0F, -2.5F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -4.0F, 3.5F));
		root.addChild("right_hind_leg", ModelPartBuilder.create().uv(0, 40).cuboid(-2.0F, 0.0F, -2.5F, 4.0F, 4.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -4.0F, 3.5F));
		return TexturedModelData.of(modelData, 64, 64);
	}

	@Override
	public void setAngles(SpinyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.rightHindLeg.pitch = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
		this.leftHindLeg.pitch = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.4f * limbSwingAmount;
		this.rightFrontLeg.pitch = MathHelper.cos(limbSwing * 0.6662f + (float)Math.PI) * 1.4f * limbSwingAmount;
		this.leftFrontLeg.pitch = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}