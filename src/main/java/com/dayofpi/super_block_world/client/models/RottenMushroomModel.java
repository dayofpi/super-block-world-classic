package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.client.registry.ModAnimations;
import com.dayofpi.super_block_world.common.entities.hostile.RottenMushroomEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

@Environment(value= EnvType.CLIENT)
public class RottenMushroomModel extends SinglePartEntityModel<RottenMushroomEntity> {
	private final ModelPart ROOT;
	public RottenMushroomModel(ModelPart modelPart) {
		this.ROOT = modelPart.getChild("root");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 21).cuboid(-4.0F, -5.0F, -4.0F, 8.0F, 5.0F, 8.0F, new Dilation(0.0F)).uv(0, 0).cuboid(-6.0F, -14.0F, -6.0F, 12.0F, 9.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(RottenMushroomEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.updateAnimation(entity.walkingAnimationState, ModAnimations.RottenMushroom.WALK, ageInTicks);
	}

	@Override
	public ModelPart getPart() {
		return this.ROOT;
	}
}