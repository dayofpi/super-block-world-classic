package com.dayofpi.super_block_world.client.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
@Environment(EnvType.CLIENT)
public class RottenMushroomModel<T extends Entity> extends SinglePartEntityModel<T> {
    private final ModelPart ROOT;

    public RottenMushroomModel(ModelPart root) {
        this.ROOT = root;
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData rootData = modelData.getRoot();
        ModelPartData body = rootData.addChild("body", ModelPartBuilder.create().uv(0, 21).cuboid(-4.0F, -1.25F, -4.0F, 8.0F, 8.0F, 8.0F), ModelTransform.pivot(0.0F, 17.25F, 0.0F));
        body.addChild("cap", ModelPartBuilder.create().uv(0, 0).cuboid(-6.0F, -7.25F, -6.0F, 12.0F, 9.0F, 12.0F), ModelTransform.NONE);
        return modelData;
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(getModelData(), 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.ROOT;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }
}