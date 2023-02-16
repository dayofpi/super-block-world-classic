package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.hostile.LavaBubbleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

@Environment(EnvType.CLIENT)
public class LavaBubbleModel extends SinglePartEntityModel<LavaBubbleEntity> {
	private final ModelPart root;
	public LavaBubbleModel(ModelPart root) {
		this.root = root;
	}

	public static TexturedModelData getOuterTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.CUBE, ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create().uv(0, 0).cuboid(1.0F, -5.0F, -3.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)).uv(0, 16).cuboid(-3.0F, -7.0F, -3.0F, 6.0F, 6.0F, 6.0F, new Dilation(0.0F)).uv(0, 0).cuboid(-2.0F, -5.0F, -3.5F, 1.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(LavaBubbleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}