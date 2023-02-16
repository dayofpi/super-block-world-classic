package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.hostile.MechakoopaEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class MechakoopaModel extends SinglePartEntityModel<MechakoopaEntity> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart jaw;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public MechakoopaModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.head = this.body.getChild("head");
        this.jaw = this.head.getChild("jaw");
        this.leftLeg = this.root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightLeg = this.root.getChild(EntityModelPartNames.RIGHT_LEG);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 24.0F, 2.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(15, 15).cuboid(-2.5F, -2.5F, -2.5F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, -5.5F, -0.5F));
        ModelPartData key = body.addChild("key_pivot", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -4.5F, 5.5F));
        key.addChild("key", ModelPartBuilder.create().uv(12, 25).cuboid(0.0F, -6.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 3.0F, -4.0F, 0.0F, -1.5708F, 0.0F));
        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(15, 10).cuboid(-2.5F, -2.1667F, -5.8333F, 5.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 10).cuboid(-2.5F, -5.1667F, -3.8333F, 5.0F, 5.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.3333F, -2.6667F));
        head.addChild("head_r1", ModelPartBuilder.create().uv(0, 20).cuboid(-12.0F, -7.0F, 0.0F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.1667F, 9.1667F, 0.0F, -1.5708F, 0.0F));
        head.addChild("jaw", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -1.5F, -5.5F, 6.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.6667F, -0.3333F));
        ModelPartData leftLeg = root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(1.6F, -5.0F, 0.0F));
        leftLeg.addChild("l_leg_r1", ModelPartBuilder.create().uv(19, 0).cuboid(-3.0F, -4.0F, 2.4F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(2.4F, 3.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        ModelPartData rightLeg = root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(-3.6F, -5.0F, 0.0F));
        rightLeg.addChild("r_leg_r1", ModelPartBuilder.create().uv(19, 0).cuboid(-3.0F, -4.0F, 1.6F, 6.0F, 6.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(1.6F, 3.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(MechakoopaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        if (entity.isInSittingPose()) {
            this.body.pivotY += 3.0F;
            this.head.pivotY += 3.0F;
            this.head.pivotZ -= 1.0F;
            this.jaw.pitch = 0.0F;
        } else {
            this.body.pivotY += 0.0F;
            this.head.pivotY += 0.0F;
            this.head.pivotZ += 0.0F;
            this.head.yaw = netHeadYaw * (MathHelper.PI / 180F);
            if (entity.isAttacking() && entity.getPower() > 0) {
                this.head.pivotZ -= 1.0F;
                this.head.pitch = -0.9F;
                this.jaw.pitch = 1.5F;
            } else {
                this.head.pitch = headPitch * (MathHelper.PI / 180F);
                this.jaw.pitch = MathHelper.clamp(MathHelper.cos(limbSwing * 0.6662F) * limbSwingAmount + 0.4F, 0.0F, 1.0F);
            }
            this.rightLeg.pivotY += MathHelper.cos(limbSwing * 0.7F) * 1.4F * limbSwingAmount;
            this.leftLeg.pivotY += MathHelper.cos(limbSwing * 0.7F + 3.1415927F) * 1.4F * limbSwingAmount;

        }
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}