package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.passive.MooMooEntity;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class MooMooModel extends AnimalModel<MooMooEntity> {
    private final ModelPart head;
    private final ModelPart rightEar;
    private final ModelPart leftEar;
    private final ModelPart bell;
    private final ModelPart rightHindLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart body;
    public MooMooModel(ModelPart root) {
        super(true, 8.0f, 6.0f, 1.9f, 2.0f, 24.0f);
        this.head = root.getChild(EntityModelPartNames.HEAD);
        this.leftEar = head.getChild(EntityModelPartNames.LEFT_EAR);
        this.rightEar = head.getChild(EntityModelPartNames.RIGHT_EAR);
        this.bell = root.getChild("bell");
        this.rightHindLeg = root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
        this.rightFrontLeg = root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
        this.body = root.getChild(EntityModelPartNames.BODY);
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 26).cuboid(-5.0F, -6.625F, -12.0F, 10.0F, 11.0F, 8.0F, new Dilation(0.0F))
                .uv(28, 26).cuboid(-5.0F, -1.625F, -14.0F, 10.0F, 6.0F, 2.0F, new Dilation(0.0F))
                .uv(0, 7).cuboid(-4.0F, -9.625F, -9.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 7).cuboid(2.0F, -9.625F, -9.0F, 2.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 11.625F, -2.0F));

        head.addChild(EntityModelPartNames.RIGHT_EAR, ModelPartBuilder.create().uv(38, 0).cuboid(-5.0F, -1.0F, -2.0F, 6.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.0F, -4.625F, -8.0F));

        head.addChild(EntityModelPartNames.LEFT_EAR, ModelPartBuilder.create().uv(38, 6).cuboid(-1.0F, -1.0F, -2.0F, 6.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(6.0F, -4.625F, -8.0F));

        modelPartData.addChild("bell", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.0F, -1.5F, 4.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 17.0F, -8.5F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_HIND_LEG, ModelPartBuilder.create().uv(36, 34).cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, 19.0F, 5.5F));

        modelPartData.addChild(EntityModelPartNames.LEFT_HIND_LEG, ModelPartBuilder.create().uv(36, 34).cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(3.5F, 19.0F, 5.5F));

        modelPartData.addChild(EntityModelPartNames.RIGHT_FRONT_LEG, ModelPartBuilder.create().uv(36, 34).cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, 19.0F, -2.5F));

        modelPartData.addChild(EntityModelPartNames.LEFT_FRONT_LEG, ModelPartBuilder.create().uv(36, 34).cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 6.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(3.5F, 19.0F, -2.5F));

        modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -19.0F, -7.0F, 12.0F, 12.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 25.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void setAngles(MooMooEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.pitch = headPitch * ((float) Math.PI / 180F) * 0.5F;
        head.yaw = netHeadYaw * ((float) Math.PI / 180F) * 0.5F;

        float progress = ageInTicks * 5F * 0.04F;
        this.leftEar.roll = 0.2F + (MathHelper.cos(progress * 0.5F) * 0.05F);
        this.rightEar.roll = -0.2F - (MathHelper.cos(progress * 0.5F) * 0.05F);

        this.bell.roll = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
        this.rightHindLeg.pitch = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
        this.leftHindLeg.pitch = MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount;
        this.rightFrontLeg.pitch = MathHelper.cos(limbSwing * 0.6662f + (float) Math.PI) * 1.4f * limbSwingAmount;
        this.leftFrontLeg.pitch = MathHelper.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.leftHindLeg, this.rightHindLeg, this.bell, this.body, this.rightFrontLeg, this.leftFrontLeg);
    }
}
