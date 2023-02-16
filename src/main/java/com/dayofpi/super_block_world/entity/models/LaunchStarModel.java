package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.client.registry.ModAnimations;
import com.dayofpi.super_block_world.entity.entities.misc.LaunchStarEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

public class LaunchStarModel<T extends LaunchStarEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    public LaunchStarModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.of(0.0F, 24.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

        root.addChild("tiny", ModelPartBuilder.create().uv(0, 22).cuboid(-4.0F, -6.5F, -1.5F, 8.0F, 0.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, -1.0F));
        root.addChild("big", ModelPartBuilder.create().uv(0, 0).cuboid(-11.0F, -7.0F, -8.5F, 22.0F, 0.0F, 22.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, -1.0F));
        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateAnimation(entity.idlingAnimationState, ModAnimations.LaunchStar.IDLE, ageInTicks);
        this.updateAnimation(entity.launchingAnimationState, ModAnimations.LaunchStar.LAUNCH, ageInTicks);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}