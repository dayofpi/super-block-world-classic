package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.hostile.PokeyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

@Environment(EnvType.CLIENT)
public class PokeyModel extends SinglePartEntityModel<PokeyEntity> {
	private final ModelPart root;

	public PokeyModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 20).cuboid(-7.0F, -17.0F, 0.0F, 14.0F, 12.0F, 0.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 32);
	}

	@Override
	public void setAngles(PokeyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.root.pitch = headPitch * ((float) Math.PI / 180F) * 0.5F;
		this.root.yaw = netHeadYaw * ((float) Math.PI / 180F) * 0.5F;
	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}