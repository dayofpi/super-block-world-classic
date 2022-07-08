package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.misc.DryBonesShellEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

public class DryBonesShellModel extends SinglePartEntityModel<DryBonesShellEntity> {
	private final ModelPart root;
	public DryBonesShellModel(ModelPart root) {
		this.root = root.getChild("root");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, -8.0F, -7.0F, 14.0F, 6.0F, 14.0F, new Dilation(0.0F))
				.uv(0, 20).cuboid(-5.0F, -2.0F, -5.0F, 10.0F, 2.0F, 10.0F, new Dilation(0.0F))
				.uv(0, 32).cuboid(-8.0F, -10.0F, -8.0F, 16.0F, 3.0F, 3.0F, new Dilation(0.0F))
				.uv(30, 20).cuboid(-8.0F, -10.0F, 5.0F, 16.0F, 3.0F, 3.0F, new Dilation(0.0F))
				.uv(30, 28).cuboid(5.0F, -10.0F, -5.0F, 3.0F, 3.0F, 10.0F, new Dilation(0.0F))
				.uv(30, 28).mirrored().cuboid(-8.0F, -10.0F, -5.0F, 3.0F, 3.0F, 10.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 64);
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}

	@Override
	public void setAngles(DryBonesShellEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
		this.root.yaw = limbAngle * 0.2F;
	}
}