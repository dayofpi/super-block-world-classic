package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.hostile.MummyMeEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.util.math.MathHelper;

public class MummyMeModel extends AnimalModel<MummyMeEntity> {
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart torso;
    private final ModelPart head;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public MummyMeModel(ModelPart root) {
        super(true, 13.0f, 0.0f, 2.0f, 2.0f, 24.0f);
        this.leftLeg = root.getChild("left_leg");
        this.rightLeg = root.getChild("right_leg");
        ModelPart body = root.getChild("body");
        this.torso = body.getChild("torso");
        this.head = body.getChild("neck");
        this.leftArm = body.getChild("left_arm");
        this.rightArm = body.getChild("right_arm");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(36, 0).mirrored().cuboid(-2.0F, 0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.0F, 16.5F, 0.0F));
        modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(36, 0).cuboid(-2.0F, 0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 16.5F, 0.0F));
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 16.5F, 0.0F));
        body.addChild("torso", ModelPartBuilder.create().uv(32, 30).cuboid(-4.0F, -8.5F, -2.0F, 8.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F + 16.5F, 0.0F));
        ModelPartData neck = body.addChild("neck", ModelPartBuilder.create().uv(0, 19).cuboid(-5.0F, -13.1667F, -5.0F, 10.0F, 1.0F, 10.0F, new Dilation(0.0F)).uv(0, 30).cuboid(-4.0F, -5.1667F, -4.0F, 8.0F, 5.0F, 8.0F, new Dilation(0.0F)).uv(0, 0).cuboid(-6.0F, -12.1667F, -6.0F, 12.0F, 7.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.3333F + 16.5F, 0.0F));
        neck.addChild("rag", ModelPartBuilder.create().uv(14, 43).cuboid(-2.5F, 1.0F, 0.0F, 5.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -6.1667F, 5.0F));
        body.addChild("left_arm", ModelPartBuilder.create().uv(0, 43).mirrored().cuboid(-1.5F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.5F, -7.0F + 16.5F, 0.0F, -1.309F, 0.0F, 0.0F));
        body.addChild("right_arm", ModelPartBuilder.create().uv(0, 43).cuboid(-1.5F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-5.5F, -7.0F + 16.5F, 0.0F, -1.309F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(MummyMeEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * (MathHelper.PI / 180F);
        this.head.yaw = headYaw * (MathHelper.PI / 180F);
        this.getBodyParts().forEach(ModelPart::resetTransform);
        if (entity.isAttacking()) {
            this.leftArm.pitch -= 0.7F;
            this.rightArm.pitch -= 0.7F;
        }
        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.torso, this.leftArm, this.rightArm, this.leftLeg, this.rightLeg);
    }
}