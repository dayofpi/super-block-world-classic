package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.hostile.MagikoopaEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

public class MagikoopaModel extends SinglePartEntityModel<MagikoopaEntity> implements ModelWithArms {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightFoot;
    private final ModelPart leftFoot;

    public MagikoopaModel(ModelPart modelPart) {
        this.root = modelPart.getChild("root");
        this.head = this.root.getChild("head");
        this.rightArm = this.root.getChild("right_arm");
        this.leftArm = this.root.getChild("left_arm");
        this.rightFoot = this.root.getChild("right_foot");
        this.leftFoot = this.root.getChild("left_foot");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 30).cuboid(-4.0F, -8.25F, -4.25F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(40, 28).cuboid(-3.0F, -3.25F, -6.25F, 6.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -12.75F, -0.75F));

        ModelPartData hat = head.addChild("hat", ModelPartBuilder.create().uv(27, 0).cuboid(-3.0F, -1.5F, -3.0F, 6.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -9.75F, 0.75F));

        ModelPartData hat_tip = hat.addChild("hat_tip", ModelPartBuilder.create().uv(32, 38).cuboid(-2.0F, -5.0F, -2.0F, 4.0F, 5.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.5F, 0.0F, 0.3491F, 0.0F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.5F, -13.0F, -4.0F, 9.0F, 9.0F, 9.0F, new Dilation(0.0F))
                .uv(0, 18).cuboid(-5.0F, -4.0F, -4.0F, 10.0F, 2.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_arm = root.addChild("right_arm", ModelPartBuilder.create().uv(31, 13).cuboid(-2.5F, -1.0F, -2.5F, 3.0F, 10.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -12.0F, 0.5F));

        ModelPartData left_arm = root.addChild("left_arm", ModelPartBuilder.create().uv(31, 13).cuboid(-0.5F, -1.0F, -2.5F, 3.0F, 10.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -12.0F, 0.5F));

        ModelPartData right_foot = root.addChild("right_foot", ModelPartBuilder.create().uv(24, 30).cuboid(-0.5F, 0.0F, -3.0F, 4.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, -2.0F, 0.0F));

        ModelPartData left_foot = root.addChild("left_foot", ModelPartBuilder.create().uv(24, 30).cuboid(-1.5F, 0.0F, -3.0F, 4.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, -2.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(MagikoopaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.pitch = headPitch * ((float) Math.PI / 180F) * 0.5F;
        this.head.yaw = netHeadYaw * ((float) Math.PI / 180F) * 0.5F;

        this.rightArm.roll = MathHelper.cos(ageInTicks * 0.05F) * 0.1F + 0.25F;
        this.leftArm.roll = MathHelper.cos(ageInTicks * 0.05F) * -0.1F - 0.25F;
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        this.root.rotate(matrices);
        this.rightArm.rotate(matrices);
        matrices.translate(0.0, 0.09375, -0.09375);
        matrices.multiply(RotationAxis.POSITIVE_X.rotation(this.rightArm.pitch - 0.2f));
        matrices.scale(0.7f, 0.7f, 0.7f);
        matrices.translate(0.0625, 0.0, 0.0);
    }
}