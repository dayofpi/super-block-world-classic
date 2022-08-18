package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.util.PowerUp;
import com.dayofpi.super_block_world.util.FormManager;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;

public class PowerUpModel extends EntityModel<PlayerEntity> {
	private final ModelPart ears;
	private final ModelPart bee_ears;
	private final ModelPart cat_tail;
	private final ModelPart tanooki_tail;
	private final ModelPart beehind;

	public PowerUpModel(ModelPart root) {
		this.ears = root.getChild("ears");
		this.bee_ears = root.getChild("bee_ears");
		this.cat_tail = root.getChild("cat_tail");
		this.tanooki_tail = root.getChild("tanooki_tail");
		this.beehind = root.getChild("beehind");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();

		modelPartData.addChild("cat_tail", ModelPartBuilder.create().uv(0, 12).cuboid(-1.0F, -0.5F, 1.25F, 2.0F, 2.0F, 7.0F, new Dilation(0.0F))
				.uv(0, 0).cuboid(-1.0F, -4.5F, 6.25F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 22.5F, 0.75F));

		modelPartData.addChild("tanooki_tail", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -2.0F, 1.0F, 4.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 23.0F, 0.0F));

		modelPartData.addChild("beehind", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -2.0F, 1.0F, 6.0F, 4.0F, 6.0F, new Dilation(0.0F))
				.uv(28, -2).cuboid(0.0F, -1.0F, 7.0F, 0.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 23.0F, 0.0F));

		modelPartData.addChild("ears", ModelPartBuilder.create().uv(11, 12).mirrored().cuboid(1.0F, -11.0F, -1.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
				.uv(11, 12).cuboid(-4.0F, -11.0F, -1.0F, 3.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		modelPartData.addChild("bee_ears", ModelPartBuilder.create().uv(0, 26).cuboid(-2.0F, -11.0F, -0.5F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F))
				.uv(0, 26).cuboid(1.0F, -11, -0.5F, 1.0F, 5.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, -0.5F));
		return TexturedModelData.of(modelData, 32, 32);}

	@Override
	public void setAngles(PlayerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		float progress = ageInTicks * 2F * 0.01F;
		this.tanooki_tail.pitch = MathHelper.cos(limbSwing + progress) * 0.2F * limbSwingAmount;
		this.tanooki_tail.yaw = MathHelper.cos(progress * 2) * 0.5F;
		this.cat_tail.pitch = MathHelper.cos(progress * 2) * 0.5F;
		this.cat_tail.yaw = MathHelper.cos(limbSwing + progress) * 0.5F * limbSwingAmount;
		if (!entity.isInSwimmingPose()) {
			ears.pitch = headPitch * ((float) Math.PI / 180F);
			bee_ears.pitch = 0.3f + headPitch * ((float) Math.PI / 180F);
		}
		else {
			ears.pitch = -0.5F;
			bee_ears.pitch = -0.4F;
		}
		ears.yaw = netHeadYaw * ((float) Math.PI / 180F);
		bee_ears.yaw = netHeadYaw * ((float) Math.PI / 180F);
		boolean isBee = entity.getDataTracker().get(FormManager.POWER_UP).equals(PowerUp.BEE.asString());
		this.beehind.hidden = !isBee;
		this.bee_ears.hidden = !isBee;
		this.ears.hidden = isBee;
		this.cat_tail.hidden = isBee;
		this.tanooki_tail.hidden = isBee;

	}

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		matrices.push();
		matrices.translate(0.0, -0.75, 0.0);
		ears.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		matrices.pop();

		matrices.push();
		matrices.translate(0.0, -0.85, 0.0);
		bee_ears.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		matrices.pop();

		matrices.push();
		matrices.translate(0.0, 0.0, 0.1);
		cat_tail.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		tanooki_tail.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		beehind.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
		matrices.pop();
	}
}