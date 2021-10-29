package com.dayofpi.sbw_main.entity.model;

import com.dayofpi.sbw_main.entity.types.mobs.HammerBroEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;

public class HammerBroModel<T extends HammerBroEntity> extends SinglePartEntityModel<T> implements ModelWithArms {
    // This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
    private final ModelPart ROOT;
    private final ModelPart left_leg;
    private final ModelPart right_leg;
    private final ModelPart left_arm;
    private final ModelPart right_arm;
    private final ModelPart head;

    public HammerBroModel(ModelPart root) {
        this.ROOT = root;
        this.left_leg = root.getChild("left_leg");
        this.right_leg = root.getChild("right_leg");
        ModelPart body = root.getChild("body");
        this.left_arm = body.getChild("left_arm");
        this.right_arm = body.getChild("right_arm");
        this.head = body.getChild("head");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(28, 30).cuboid(-2.0F, 0.5F, -2.0F, 4.0F, 9.0F, 4.0F), ModelTransform.pivot(1.0F, 14.5F, -1.0F));

        modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(28, 30).mirrored().cuboid(-2.0F, -0.5F, -2.0F, 4.0F, 9.0F, 4.0F), ModelTransform.pivot(-3.0F, 15.5F, -1.0F));

        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 10.0F, 8.0F), ModelTransform.pivot(-1.0F, 15.0F, 0.0F));

        body.addChild("head", ModelPartBuilder.create().uv(24, 10).cuboid(-4.0F, -7.8333F, -4.3333F, 8.0F, 8.0F, 8.0F)
                .uv(0, 18).cuboid(-4.0F, -7.8333F, -4.3333F, 8.0F, 8.0F, 8.0F, new Dilation(.5F))
                .uv(24, 0).cuboid(-2.0F, -2.8333F, -6.3333F, 4.0F, 3.0F, 2.0F), ModelTransform.pivot(0.0F, -10.1667F, -1.6667F));

        body.addChild("right_arm", ModelPartBuilder.create().uv(0, 34).mirrored().cuboid(-2.5F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F), ModelTransform.pivot(-4.5F, -7.5F, -1.0F));

        body.addChild("left_arm", ModelPartBuilder.create().uv(0, 34).cuboid(-0.5F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F), ModelTransform.pivot(4.5F, -7.5F, -1.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return ROOT;
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float headYaw, float headPitch) {
        Arm arm = this.getPreferredArm(entity);
        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
        this.right_leg.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.left_leg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.right_arm.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.left_arm.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        if (entity.isAttacking()) {
            this.getArm(arm).pitch = -1.6F;
        }
    }

    protected ModelPart getArm(Arm arm) {
        return arm == Arm.LEFT ? this.left_arm : this.right_arm;
    }

    private Arm getPreferredArm(T entity) {
        Arm arm = entity.getMainArm();
        return entity.preferredHand == Hand.MAIN_HAND ? arm : arm.getOpposite();
    }

    @Override
    public void setArmAngle(Arm arm, MatrixStack matrices) {
        this.getArm(arm).rotate(matrices);
    }
}
