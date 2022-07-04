package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.hostile.DinoRhinoEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;

public class DinoRhinoModel extends AnimalModel<DinoRhinoEntity> {
    private final ModelPart left_front_leg;
    private final ModelPart right_front_leg;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart r_ear;
    private final ModelPart l_ear;
    private final ModelPart left_hind_leg;
    private final ModelPart right_hind_leg;

    public DinoRhinoModel(ModelPart root) {
        super(true, 8.0f, 6.0f, 1.9f, 2.0f, 24.0f);
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.body = root.getChild(EntityModelPartNames.BODY);
        this.l_ear = this.head.getChild(EntityModelPartNames.LEFT_EAR);
        this.r_ear = this.head.getChild(EntityModelPartNames.RIGHT_EAR);
        this.right_front_leg = root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
        this.right_hind_leg = root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
        this.left_front_leg = root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
        this.left_hind_leg = root.getChild(EntityModelPartNames.LEFT_HIND_LEG);

    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 25).cuboid(-8.0F, -6.3095F, -7.1252F, 16.0F, 16.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 10.0F, -6.5F, -0.4363F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.RIGHT_EAR, ModelPartBuilder.create().uv(46, 41).mirrored().cuboid(-6.8468F, -3.3373F, -1.4226F, 6.0F, 5.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-6.5F, -2.8095F, -2.6252F, 0.0F, 0.0F, 0.3927F));
        head.addChild(EntityModelPartNames.LEFT_EAR, ModelPartBuilder.create().uv(46, 41).cuboid(-0.1532F, -3.3373F, -1.4226F, 6.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(7.5F, -2.8095F, -2.6252F, 0.0F, 0.0F, -0.3927F));
        modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(0, 48).cuboid(-4.5F, -0.5F, -3.5F, 7.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, 18.5F, -4.5F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(0, 48).cuboid(-4.5F, -0.5F, -3.5F, 7.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, 18.5F, -4.5F));
        modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(0, 48).cuboid(-4.5F, -0.5F, -1.0F, 7.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(5.5F, 18.5F, 2.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(0, 48).cuboid(-4.5F, -0.5F, -1.0F, 7.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, 18.5F, 2.0F));
        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-7.0F, 0.5F, -4.0F, 14.0F, 11.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 6.5F, -3.0F));
        body.addChild("spikes", ModelPartBuilder.create().uv(39, 25).cuboid(-2.0F, -2.5F, 0.0F, 12.0F, 4.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -1.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(DinoRhinoEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.pitch = -0.5F + headPitch * ((float) Math.PI / 180F) * 0.5F;
        this.head.yaw = netHeadYaw * ((float) Math.PI / 180F) * 0.5F;

        float progress = ageInTicks * 5F * 0.04F;
        l_ear.roll = (-0.4F - (MathHelper.cos(progress * 0.5F) * 0.05F));
        r_ear.roll =(0.4F + (MathHelper.cos(progress * 0.5F) * 0.05F));

        right_hind_leg.pitch = (MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount);
        left_hind_leg.pitch = (MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount);
        right_front_leg.pitch = (MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount);
        left_front_leg.pitch = (MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount);

    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.left_hind_leg, this.left_front_leg, this.right_hind_leg, this.right_front_leg);
    }
}