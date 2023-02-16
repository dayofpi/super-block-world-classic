package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.passive.BabyYoshiEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class BabyYoshiModel extends SinglePartEntityModel<BabyYoshiEntity> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart body;
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;

    public BabyYoshiModel(ModelPart root) {
        this.root = root.getChild("root");
        this.head = this.root.getChild(EntityModelPartNames.HEAD);
        this.tail = this.root.getChild(EntityModelPartNames.TAIL);
        this.body = this.root.getChild(EntityModelPartNames.BODY);
        this.rightArm = this.root.getChild(EntityModelPartNames.RIGHT_ARM);
        this.leftArm = this.root.getChild(EntityModelPartNames.LEFT_ARM);
        this.rightLeg = this.root.getChild(EntityModelPartNames.RIGHT_LEG);
        this.leftLeg = this.root.getChild(EntityModelPartNames.LEFT_LEG);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        root.addChild("head", ModelPartBuilder.create().uv(0, 21).cuboid(-5.0F, -6.0F, -9.75F, 10.0F, 8.0F, 12.0F, new Dilation(0.0F)).uv(32, 21).cuboid(-5.0F, -10.0F, -5.75F, 10.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -11.0F, 0.75F));
        root.addChild("right_leg", ModelPartBuilder.create().uv(36, 33).cuboid(-2.0F, 0.0F, -4.0F, 4.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, -6.0F, 1.0F));
        root.addChild("left_leg", ModelPartBuilder.create().uv(36, 33).mirrored().cuboid(-2.0F, 0.0F, -4.0F, 4.0F, 6.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(8.0F, -6.0F, 1.0F));
        root.addChild("tail", ModelPartBuilder.create().uv(35, 0).cuboid(-3.0F, -2.0F, 0.0F, 6.0F, 4.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.0F, 6.0F));
        root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -5.0F, -5.5F, 12.0F, 10.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -6.0F, 0.5F));
        root.addChild("right_arm", ModelPartBuilder.create().uv(0, 21).cuboid(-1.5F, -1.5F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.5F, -6.5F, -4.5F));
        root.addChild("left_arm", ModelPartBuilder.create().uv(0, 21).mirrored().cuboid(-1.5F, -1.5F, -1.5F, 3.0F, 5.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(5.5F, -6.5F, -4.5F));
        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public void setAngles(BabyYoshiEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * ((float) Math.PI / 180F) * 0.5F;
        this.head.yaw = headYaw * ((float) Math.PI / 180F) * 0.5F;
        this.head.roll = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 0.5F * limbDistance;
        this.body.yaw = MathHelper.cos(entity.limbAngle * 0.6662F) * 0.5F * entity.limbDistance;
        this.tail.yaw = MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * entity.limbDistance;
        this.rightArm.pitch = MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance;
        this.leftArm.pitch = MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance;
        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}