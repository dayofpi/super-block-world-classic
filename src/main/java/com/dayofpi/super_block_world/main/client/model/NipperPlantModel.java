package com.dayofpi.super_block_world.main.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
@Environment(EnvType.CLIENT)
public class NipperPlantModel<T extends Entity> extends SinglePartEntityModel<T> {
    private final ModelPart ROOT;
    private final ModelPart HEAD;
    private final ModelPart JAW;
    private final ModelPart LEFT_LEAF;
    private final ModelPart RIGHT_LEAF;

    public NipperPlantModel(ModelPart root) {
        this.ROOT = root;
        ModelPart STEM = root.getChild("stem");
        this.HEAD = root.getChild("head");
        this.JAW = HEAD.getChild("jaw");
        this.LEFT_LEAF = STEM.getChild("left_leaf");
        this.RIGHT_LEAF = STEM.getChild("right_leaf");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData stem = modelPartData.addChild("stem", ModelPartBuilder.create().uv(0, 17).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 3.0F, 2.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0,0).cuboid(-3.0F, -4.5F, -4.0F, 6.0F, 3.0F, 6.0F), ModelTransform.pivot(0.0F, 20.5F, 0.0F));
        head.addChild("jaw", ModelPartBuilder.create().uv(0,9).cuboid(-3.0F, 0.0F, -6.0F, 6.0F, 2.0F, 6.0F), ModelTransform.pivot(0.0F, -1.5F, 2.0F));
        stem.addChild("left_leaf", ModelPartBuilder.create().mirrored(true).uv(12,0).cuboid(0.0F, 0.0F, -3.0F, 6.0F, 0.0F, 6.0F), ModelTransform.pivot(1.0F, 0.0F, 0.0F));
        stem.addChild("right_leaf", ModelPartBuilder.create().uv(12,0).cuboid(-6.0F, 0.0F, -3.0F, 6.0F, 0.0F, 6.0F), ModelTransform.pivot(-1.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public ModelPart getPart() {
        return ROOT;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.HEAD.yaw = headYaw * 0.017453292F;
        this.HEAD.pitch = headPitch * 0.017453292F;
        this.JAW.pitch = headPitch * -0.017453292F;
        this.RIGHT_LEAF.roll = 0.3927F;
        this.LEFT_LEAF.roll = -0.5672F;
    }
}
