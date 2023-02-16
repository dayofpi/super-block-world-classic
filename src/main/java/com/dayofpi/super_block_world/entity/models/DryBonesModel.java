package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.client.registry.ModAnimations;
import com.dayofpi.super_block_world.entity.entities.hostile.DryBonesEntity;
import com.dayofpi.super_block_world.entity.entities.hostile.ParabonesEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Flutterer;
import net.minecraft.util.math.MathHelper;

public class DryBonesModel extends SinglePartEntityModel<DryBonesEntity> {
    private final ModelPart root;
    private final ModelPart jaw;
    private final ModelPart head;
    private final ModelPart right_wing;
    private final ModelPart left_wing;

    public DryBonesModel(ModelPart root) {
        this.root = root.getChild(EntityModelPartNames.ROOT);
        this.left_wing = this.root.getChild(EntityModelPartNames.LEFT_WING);
        this.right_wing = this.root.getChild(EntityModelPartNames.RIGHT_WING);
        this.head = this.root.getChild(EntityModelPartNames.HEAD);
        this.jaw = this.root.getChild(EntityModelPartNames.JAW);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(DryBonesEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        boolean isWinged = entity instanceof Flutterer;
        left_wing.visible = isWinged;
        right_wing.visible = isWinged;
        float progress = animationProgress * 5F * 0.017453292F;
        right_wing.pitch = (MathHelper.cos(progress) * 0.2F -0.1F);
        left_wing.pitch = (MathHelper.cos(progress) * 0.2F -0.1F);

        head.yaw = (headYaw * ((float) Math.PI / 180F));
        jaw.yaw = (headYaw * ((float) Math.PI / 180F));
        head.roll = (headPitch * ((float) Math.PI / 180F) * 0.5F);
        jaw.roll = (headPitch * ((float) Math.PI / 180F) * 0.5F);

        if (!entity.isAttacking()) {
            head.pitch = (headPitch * ((float) Math.PI / 180F));
            jaw.pitch = (headPitch * ((float) Math.PI / 180F));
        }
        this.updateAnimation(entity.idlingAnimationState, ModAnimations.DryBones.IDLE, animationProgress);
        this.updateAnimation(entity.chasingAnimationState, ModAnimations.DryBones.CHASE, animationProgress);
        this.updateAnimation(entity.walkingAnimationState, ModAnimations.DryBones.WALK, animationProgress);
        this.updateAnimation(entity.breakingAnimationState, ModAnimations.DryBones.BREAK, animationProgress);
        this.updateAnimation(entity.wakingUpAnimationState, ModAnimations.DryBones.WAKEUP, animationProgress);
        if (isWinged) {
            this.updateAnimation(((ParabonesEntity) entity).flyingAnimationState, ModAnimations.KoopaTroopa.FLY, animationProgress);
        }
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 13.9698F, 1.829F));

        root.addChild("left_arm", ModelPartBuilder.create().uv(0, 13).cuboid(-1.5F, 2.5F, -1.0F, 3.0F, 5.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 0.5302F, -3.329F));
        root.addChild("right_arm", ModelPartBuilder.create().uv(0, 13).mirrored().cuboid(-1.5F, 2.5F, -1.0F, 3.0F, 5.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.5F, 0.5302F, -3.329F));
        root.addChild("right_leg", ModelPartBuilder.create().uv(24, 0).cuboid(-2.0F, -0.5F, -1.5F, 4.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, 3.5302F, 1.671F));
        root.addChild("left_leg", ModelPartBuilder.create().uv(24, 0).cuboid(-2.0F, -0.5F, -1.5F, 4.0F, 7.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 3.5302F, 1.671F));
        root.addChild("jaw", ModelPartBuilder.create().uv(32, 32).cuboid(-4.0F, -2.0F, -9.0F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.9698F, -2.829F));
        root.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.5F, -5.5F, 8.0F, 6.0F, 4.0F, new Dilation(0.0F)).uv(30, 3).cuboid(-4.0F, -2.5F, -8.5F, 8.0F, 4.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.4698F, -3.329F));
        root.addChild("body", ModelPartBuilder.create().uv(0, 10).cuboid(-5.0F, -4.0F, -5.0F, 10.0F, 8.0F, 10.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
        root.addChild("right_wing", ModelPartBuilder.create().uv(40, 15).cuboid(-1.0F, -9.0F, -1.5F, 2.0F, 10.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(5.0F, -4.0F, -0.5F));
        root.addChild("left_wing", ModelPartBuilder.create().uv(40, 15).cuboid(-1.0F, -9.0F, -1.5F, 2.0F, 10.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -4.0F, -0.5F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}
