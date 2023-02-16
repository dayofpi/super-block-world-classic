package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.hostile.MummyMeEntity;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.util.math.MathHelper;

public class MummyMeModel extends AnimalModel<MummyMeEntity> {
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;
    private final ModelPart torso;
    private final ModelPart head;
    private final ModelPart rag;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private static final String RAG = "rag";

    public MummyMeModel(ModelPart root) {
        super(true, 13.0f, 0.0f, 2.0f, 2.0f, 24.0f);
        this.leftLeg = root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightLeg = root.getChild(EntityModelPartNames.RIGHT_LEG);
        ModelPart body = root.getChild(EntityModelPartNames.BODY);
        this.torso = body.getChild(ToadModel.TORSO);
        this.head = body.getChild(EntityModelPartNames.HEAD);
        this.rag = this.head.getChild(MummyMeModel.RAG);
        this.leftArm = body.getChild(EntityModelPartNames.LEFT_ARM);
        this.rightArm = body.getChild(EntityModelPartNames.RIGHT_ARM);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(36, 0).mirrored().cuboid(-2.0F, 0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(2.0F, 16.5F, 0.0F));
        modelPartData.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(36, 0).cuboid(-2.0F, 0.5F, -2.0F, 4.0F, 7.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, 16.5F, 0.0F));
        ModelPartData body = modelPartData.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 16.5F, 0.0F));
        body.addChild(ToadModel.TORSO, ModelPartBuilder.create().uv(32, 30).cuboid(-4.0F, -8.5F, -2.0F, 8.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F + 16.5F, 0.0F));

        ModelPartData head = body.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(0, 19).cuboid(-5.0F, -13.1667F, -5.0F, 10.0F, 1.0F, 10.0F, new Dilation(0.0F)).uv(0, 30).cuboid(-4.0F, -5.1667F, -4.0F, 8.0F, 5.0F, 8.0F, new Dilation(0.0F)).uv(0, 0).cuboid(-6.0F, -12.1667F, -6.0F, 12.0F, 7.0F, 12.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.3333F + 16.5F, 0.0F));
        head.addChild(MummyMeModel.RAG, ModelPartBuilder.create().uv(14, 43).cuboid(-2.5F, 1.0F, 0.0F, 5.0F, 12.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -6.1667F, 5.0F));

        body.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(0, 43).mirrored().cuboid(-1.5F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(5.5F, -7.0F + 16.5F, 0.0F, -1.309F, 0.0F, 0.0F));
        body.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(0, 43).cuboid(-1.5F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(-5.5F, -7.0F + 16.5F, 0.0F, -1.309F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(MummyMeEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * (MathHelper.PI / 180F);
        this.head.yaw = headYaw * (MathHelper.PI / 180F);
        this.rag.pitch = MathHelper.cos(limbAngle * 0.6662F) * 0.2F * limbDistance;
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