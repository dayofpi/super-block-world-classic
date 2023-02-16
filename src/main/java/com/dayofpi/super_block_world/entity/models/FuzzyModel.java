package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.client.registry.ModAnimations;
import com.dayofpi.super_block_world.entity.entities.hostile.FuzzyEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

@Environment(EnvType.CLIENT)
public class FuzzyModel<T extends FuzzyEntity> extends SinglePartEntityModel<T> {
    private final ModelPart ROOT;

    public FuzzyModel(ModelPart modelPart) {
        this.ROOT = modelPart.getChild(EntityModelPartNames.ROOT);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(), ModelTransform.NONE);
        root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 12).cuboid(-5.0F, -11.0F, -6.0F, 10.0F, 10.0F, 10.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        root.addChild(EntityModelPartNames.RIGHT_EYE, ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F), ModelTransform.pivot(-2.0F, 13.0F, -4.0F));
        root.addChild(EntityModelPartNames.LEFT_EYE, ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-2.0F, -4.0F, -2.0F, 4.0F, 4.0F, 4.0F).mirrored(false), ModelTransform.pivot(2.0F, 13.0F, -4.0F));
        root.addChild("spikes", ModelPartBuilder.create().uv(32, 0).cuboid(-8.0F, -8.0F, 0.0F, 16.0F, 16.0F, 0.0F), ModelTransform.pivot(0.0F, 18.0F, -1.0F));

        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateAnimation(entity.idlingAnimationState, ModAnimations.Fuzzy.IDLE, animationProgress);
    }

    @Override
    public ModelPart getPart() {
        return this.ROOT;
    }
}