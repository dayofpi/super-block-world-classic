package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.Toad;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.MathHelper;

public class ToadModel<T extends PassiveEntity> extends AnimalModel<T> {
    protected static final String TORSO = "torso";
    private static final String LEFT_TWINTAIL = "left_twintail";
    private static final String RIGHT_TWINTAIL = "right_twintail";
    private final ModelPart body;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart torso;
    private final ModelPart head;
    private final ModelPart leftTwintail;
    private final ModelPart rightTwintail;
    private final ModelPart leftArm;
    private final ModelPart rightArm;

    public ToadModel(ModelPart root) {
        super(true, 13.0f, 0.0f, 2.0f, 2.0f, 24.0f);
        this.leftLeg = root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightLeg = root.getChild(EntityModelPartNames.RIGHT_LEG);
        this.body = root.getChild(EntityModelPartNames.BODY);
        this.leftArm = body.getChild(EntityModelPartNames.LEFT_ARM);
        this.rightArm = body.getChild(EntityModelPartNames.RIGHT_ARM);
        this.torso = body.getChild(ToadModel.TORSO);
        this.head = body.getChild(EntityModelPartNames.HEAD);
        this.leftTwintail = this.head.getChild(ToadModel.LEFT_TWINTAIL);
        this.rightTwintail = this.head.getChild(ToadModel.RIGHT_TWINTAIL);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(36, 0).mirrored().cuboid(-2.0F, 0.5F, -2.0F, 4.0F, 7.0F, 4.0F).mirrored(false), ModelTransform.pivot(2.0F, 16.5F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(36, 0).cuboid(-2.0F, 0.5F, -2.0F, 4.0F, 7.0F, 4.0F), ModelTransform.pivot(-2.0F, 16.5F, 0.0F));
        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.5F, 0.0F));
        body.addChild(ToadModel.TORSO, ModelPartBuilder.create().uv(32, 30).cuboid(-4.0F, -16.0F, -2.0F, 8.0F, 9.0F, 4.0F), ModelTransform.pivot(0.0F, 7.5F + 16.5F, 0.0F));
        ModelPartData head = body.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 19).cuboid(-5.0F, -13.1667F, -5.0F, 10.0F, 1.0F, 10.0F).uv(0, 30).cuboid(-4.0F, -5.1667F, -4.0F, 8.0F, 5.0F, 8.0F).uv(0, 0).cuboid(-6.0F, -12.1667F, -6.0F, 12.0F, 7.0F, 12.0F), ModelTransform.pivot(0.0F, -8.3333F + 16.5F, 0.0F));

        head.addChild(ToadModel.LEFT_TWINTAIL, ModelPartBuilder.create().uv(30, 19).mirrored().cuboid(-1.25F, 2.75F, -4.25F, 4.0F, 4.0F, 4.0F).mirrored(false).uv(0, 0).mirrored().cuboid(-1.25F, -0.25F, -3.75F, 3.0F, 3.0F, 3.0F).mirrored(false), ModelTransform.pivot(5.25F, -4.9167F, 2.75F));
        head.addChild(ToadModel.RIGHT_TWINTAIL, ModelPartBuilder.create().uv(30, 19).cuboid(-2.25F, 2.75F, -4.25F, 4.0F, 4.0F, 4.0F).uv(0, 0).cuboid(-1.25F, -0.25F, -3.75F, 3.0F, 3.0F, 3.0F), ModelTransform.pivot(-5.75F, -4.9167F, 2.75F));

        body.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(0, 43).mirrored().cuboid(-1.5F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F).mirrored(false), ModelTransform.pivot(5.5F, -7.0F + 16.5F, 0.0F));
        body.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(0, 43).cuboid(-1.5F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F), ModelTransform.pivot(-5.5F, -7.0F + 16.5F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity instanceof Toad) {
            String string = Formatting.strip(entity.getName().getString());
            this.leftTwintail.visible = ((Toad) entity).isToadette() || "Lucy".equals(string);
            this.rightTwintail.visible = ((Toad) entity).isToadette() || "Lucy".equals(string);
        }
        if (entity instanceof Toad) {
            if (((Toad) entity).isScared()) {
                this.body.pitch = 0.3F;
                this.rightArm.pitch = -1.5F;
                this.leftArm.pitch = -1.5F;
                this.rightArm.yaw = 0.5F;
                this.leftArm.yaw = -0.5F;
            } else {
                this.body.pitch = 0.0F;
                if (((Toad) entity).isCheering()) {
                    this.rightArm.pitch = -1.4F;
                    this.leftArm.pitch = -1.4F;
                    float progress = ageInTicks * 5F * 0.04F;
                    this.rightArm.yaw = MathHelper.cos(progress * 4) * 0.5F;
                    this.leftArm.yaw = MathHelper.cos(progress * 4) * -0.5F;
                } else {
                    this.rightArm.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
                    this.leftArm.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
                    this.rightArm.yaw = 0.0F;
                    this.leftArm.yaw = 0.0F;
                }
            }
        }

        this.head.pitch = headPitch * (MathHelper.PI / 180F);
        this.head.yaw = netHeadYaw * (MathHelper.PI / 180F);

        this.rightLeg.pitch = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.leftLeg.pitch = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
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
