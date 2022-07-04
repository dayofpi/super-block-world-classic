package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.client.registry.ModAnimations;
import com.dayofpi.super_block_world.common.entities.hostile.FakeBlockEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class FakeBlockModel extends SinglePartEntityModel<FakeBlockEntity> {
    private final ModelPart ROOT;
    private final ModelPart TAIL;

    public FakeBlockModel(ModelPart modelPart) {
        this.ROOT = modelPart.getChild("root");
        this.TAIL = this.ROOT.getChild("tail");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        root.addChild("block", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -8.0F, -8.0F, 16.0F, 16.0F, 16.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 0.0F));

        root.addChild("tail", ModelPartBuilder.create().uv(0, 32).cuboid(-3.0F, 2.0F, 8.0F, 6.0F, 6.0F, 11.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -8.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.ROOT;
    }

    @Override
    public void setAngles(FakeBlockEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float progress = ageInTicks * 2F * 0.01F;
        this.TAIL.pitch = MathHelper.cos(limbSwing + progress) * 0.2F * limbSwingAmount;
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateAnimation(entity.attackingAnimationState, ModAnimations.FakeBlock.ATTACK, ageInTicks);
    }
}