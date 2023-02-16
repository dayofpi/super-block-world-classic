package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.client.registry.ModAnimations;
import com.dayofpi.super_block_world.entity.entities.passive.AbstractKoopa;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Flutterer;
import net.minecraft.util.math.MathHelper;

public class KoopaTroopaModel extends SinglePartEntityModel<AbstractKoopa> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart rightWing;
    private final ModelPart leftWing;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public KoopaTroopaModel(ModelPart modelPart) {
        this.root = modelPart.getChild("root");
        this.head = this.root.getChild("head");
        this.leftArm = this.root.getChild("left_arm");
        this.rightArm = this.root.getChild("right_arm");
        this.leftWing = this.root.getChild("left_wing");
        this.rightWing = this.root.getChild("right_wing");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 13.9698F, 1.829F));
        root.addChild("left_arm", ModelPartBuilder.create().uv(0, 13).cuboid(-1.5F, 1.5F, -1.0F, 3.0F, 5.0F, 2.0F), ModelTransform.pivot(2.5F, 0.5302F, -3.329F));
        root.addChild("right_arm", ModelPartBuilder.create().uv(0, 13).mirrored().cuboid(-1.5F, 1.5F, -1.0F, 3.0F, 5.0F, 2.0F).mirrored(false), ModelTransform.pivot(-2.5F, 0.5302F, -3.329F));
        root.addChild("right_leg", ModelPartBuilder.create().uv(24, 0).cuboid(-2.0F, -0.5F, -1.5F, 4.0F, 7.0F, 3.0F), ModelTransform.pivot(-2.5F, 3.5302F, 1.671F));
        root.addChild("left_leg", ModelPartBuilder.create().uv(24, 0).cuboid(-2.0F, -0.5F, -1.5F, 4.0F, 7.0F, 3.0F), ModelTransform.pivot(2.5F, 3.5302F, 1.671F));
        root.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.5F, -4.5F, 8.0F, 6.0F, 4.0F).uv(30, 3).cuboid(-4.0F, -2.5F, -7.5F, 8.0F, 4.0F, 8.0F), ModelTransform.pivot(0.0F, -4.4698F, -3.329F));
        root.addChild("body", ModelPartBuilder.create().uv(0, 10).cuboid(-5.0F, -4.0F, -5.0F, 10.0F, 8.0F, 10.0F), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
        root.addChild("right_wing", ModelPartBuilder.create().uv(40, 15).cuboid(-1.0F, -9.0F, -1.5F, 2.0F, 10.0F, 7.0F), ModelTransform.pivot(5.0F, -4.0F, -0.5F));
        root.addChild("left_wing", ModelPartBuilder.create().uv(40, 15).cuboid(-1.0F, -9.0F, -1.5F, 2.0F, 10.0F, 7.0F), ModelTransform.pivot(-5.0F, -4.0F, -0.5F));

        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(AbstractKoopa entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        boolean isWinged = entity instanceof Flutterer;
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        float progress = ageInTicks * 5F * 0.017453292F;
        this.leftWing.visible = isWinged;
        this.rightWing.visible = isWinged;

        this.rightWing.pitch = MathHelper.cos(progress) * 0.2F - 0.1F;
        this.leftWing.pitch = MathHelper.cos(progress) * 0.2F - 0.1F;

        if (entity.isAttacking()) {
            this.leftArm.pitch -= 0.7F;
            this.rightArm.pitch -= 0.7F;
        }

        this.head.pitch = headPitch * ((float) Math.PI / 180F);
        this.head.yaw = netHeadYaw * ((float) Math.PI / 180F);
        this.updateAnimation(entity.idlingAnimationState, ModAnimations.KoopaTroopa.IDLE, ageInTicks);
        this.updateAnimation(entity.dancingAnimationState, ModAnimations.KoopaTroopa.DANCE, ageInTicks);
        this.updateAnimation(entity.walkingAnimationState, isWinged ? ModAnimations.KoopaTroopa.FLY : ModAnimations.KoopaTroopa.WALK, ageInTicks);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}