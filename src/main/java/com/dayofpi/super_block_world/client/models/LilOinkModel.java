package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.passive.LilOinkEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class LilOinkModel<T extends LilOinkEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;

    public LilOinkModel(ModelPart modelPart) {
        this.root = modelPart.getChild("root");
        this.rightHindLeg = root.getChild(EntityModelPartNames.RIGHT_HIND_LEG);
        this.leftHindLeg = root.getChild(EntityModelPartNames.LEFT_HIND_LEG);
        this.rightFrontLeg = root.getChild(EntityModelPartNames.RIGHT_FRONT_LEG);
        this.leftFrontLeg = root.getChild(EntityModelPartNames.LEFT_FRONT_LEG);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -13.0F, -5.0F, 10.0F, 10.0F, 10.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        root.addChild("snoot", ModelPartBuilder.create().uv(0, 20).cuboid(-3.0F, -3.0F, -2.0F, 6.0F, 6.0F, 2.0F), ModelTransform.pivot(0.0F, -7.0F, -5.0F));
        root.addChild("left_hind_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F), ModelTransform.pivot(3.0F, -4.0F, 3.0F));
        root.addChild("left_front_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F), ModelTransform.pivot(3.0F, -4.0F, -3.0F));
        root.addChild("right_hind_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F), ModelTransform.pivot(-3.0F, -4.0F, 3.0F));
        root.addChild("right_front_leg", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 4.0F, 2.0F), ModelTransform.pivot(-3.0F, -4.0F, -3.0F));

        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.leftHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance;
        this.rightHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662f + (float) Math.PI) * 1.4f * limbDistance;
        this.leftFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662f + (float) Math.PI) * 1.4f * limbDistance;
        this.rightFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662f) * 1.4f * limbDistance;
    }
}