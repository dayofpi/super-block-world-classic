package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.passive.SpindriftEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

@Environment(EnvType.CLIENT)
public class SpindriftModel<T extends SpindriftEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart blossom;

    public SpindriftModel(ModelPart modelPart) {
        this.root = modelPart.getChild("root");
        this.head = root.getChild("head");
        this.blossom = head.getChild("blossom");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData head = root.addChild("head", ModelPartBuilder.create().uv(0, 16).cuboid(-3.0F, -5.5F, -3.0F, 6.0F, 5.0F, 6.0F), ModelTransform.pivot(0.0F, -5.5F, 0.0F));
        head.addChild("blossom", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, 0.0F, -8.0F, 16.0F, 0.0F, 16.0F), ModelTransform.pivot(0.0F, -5.5F, 0.0F));
        root.addChild("body", ModelPartBuilder.create().uv(19, 22).cuboid(-2.5F, 0.5F, -2.5F, 5.0F, 5.0F, 5.0F)
                .uv(0, 0).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 6.0F, 4.0F), ModelTransform.pivot(0.0F, -6.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.yaw = headYaw * ((float) Math.PI / 180F);
        this.blossom.yaw = animationProgress * 0.2F;
    }
}