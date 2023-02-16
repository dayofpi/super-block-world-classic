package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.client.registry.ModAnimations;
import com.dayofpi.super_block_world.entity.entities.hostile.UnagiEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

@Environment(EnvType.CLIENT)
public class UnagiModel extends SinglePartEntityModel<UnagiEntity> {
    private final ModelPart head;

    public UnagiModel(ModelPart root) {
        this.head = root.getChild(EntityModelPartNames.HEAD);
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -4.0F, -8.0F, 4.0F, 4.0F, 8.0F, new Dilation(0.0F)).uv(27, 2).cuboid(-2.0F, 0.0F, -8.0F, 4.0F, 1.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 20.0F, -10.0F));
        ModelPartData neck = head.addChild("neck", ModelPartBuilder.create().uv(0, 24).cuboid(-2.0F, -3.5F, 0.0F, 4.0F, 5.0F, 8.0F, new Dilation(0.0F)).uv(30, 22).cuboid(0.0F, -4.5F, 0.0F, 0.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData body = neck.addChild("body", ModelPartBuilder.create().uv(0, 38).cuboid(-1.5F, -1.5F, 0.0F, 3.0F, 5.0F, 8.0F, new Dilation(0.0F)).uv(30, 22).cuboid(0.0F, -2.5F, 0.0F, 0.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 8.0F));
        ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(0, 38).cuboid(-1.5F, -1.5F, 0.0F, 3.0F, 5.0F, 8.0F, new Dilation(0.0F)).uv(30, 22).cuboid(0.0F, -2.5F, 0.0F, 0.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 8.0F));
        tail.addChild("tail_end", ModelPartBuilder.create().uv(0, 54).cuboid(-1.0F, -1.0F, 0.0F, 2.0F, 4.0F, 6.0F, new Dilation(0.0F)).uv(40, 23).cuboid(0.0F, -2.0F, 0.0F, 0.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 8.0F));
        head.addChild("jaw", ModelPartBuilder.create().uv(0, 13).cuboid(-2.0F, 0.0F, -8.0F, 4.0F, 2.0F, 8.0F, new Dilation(0.0F)).uv(25, 9).cuboid(-2.0F, -1.0F, -8.0F, 4.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.5236F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.head;
    }

    @Override
    public void setAngles(UnagiEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateAnimation(entity.attackingAnimationState, ModAnimations.Unagi.BITE, animationProgress);
        this.updateAnimation(entity.swimmingAnimationState, ModAnimations.Unagi.SWIM, animationProgress);
        this.updateAnimation(entity.sufferingAnimationState, ModAnimations.Unagi.SUFFER, animationProgress);
    }
}
