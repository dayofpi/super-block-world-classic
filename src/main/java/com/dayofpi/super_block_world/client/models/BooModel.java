package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.hostile.BooEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class BooModel extends SinglePartEntityModel<BooEntity> {
	private final ModelPart root;
	private final ModelPart tongue;
	private final ModelPart right_arm;
	private final ModelPart left_arm;
	private float alpha;


	public BooModel(ModelPart root) {
		this.root = root.getChild(EntityModelPartNames.ROOT);
		this.tongue = this.root.getChild(EntityModelPartNames.TONGUE);
		this.right_arm = this.root.getChild(EntityModelPartNames.RIGHT_ARM);
		this.left_arm = this.root.getChild(EntityModelPartNames.LEFT_ARM);
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
		this.getPart().traverse().forEach(ModelPart::resetTransform);
	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
		alpha = this.alpha;
		super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
	}
}