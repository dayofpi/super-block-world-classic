package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.hostile.PokeySegmentEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

@Environment(EnvType.CLIENT)
public class PokeySegmentModel extends SinglePartEntityModel<PokeySegmentEntity> {
	private final ModelPart root;

	public PokeySegmentModel(ModelPart root) {
		this.root = root.getChild("root");
	}

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 22).cuboid(-6.0F, -10.0F, -5.0F, 12.0F, 10.0F, 10.0F, new Dilation(0.0F))
				.uv(34, 12).cuboid(-5.0F, -10.0F, -5.0F, 10.0F, 10.0F, 10.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-5.0F, -10.0F, -6.0F, 10.0F, 10.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 128, 64);
	}

	@Override
	public void setAngles(PokeySegmentEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public ModelPart getPart() {
		return this.root;
	}
}