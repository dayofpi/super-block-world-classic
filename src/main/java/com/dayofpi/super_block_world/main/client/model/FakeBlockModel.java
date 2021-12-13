package com.dayofpi.super_block_world.main.client.model;

import com.dayofpi.super_block_world.main.common.entity.type.mobs.FakeBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
@Environment(EnvType.CLIENT)
public class FakeBlockModel<T extends FakeBlockEntity> extends SinglePartEntityModel<T> {
    private final ModelPart ROOT;
    private final ModelPart BLOCK;
    private final ModelPart TAIL;

    public FakeBlockModel(ModelPart root) {
        this.ROOT = root;
        this.BLOCK = root.getChild("block");
        this.TAIL = root.getChild("tail");
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData rootData = modelData.getRoot();
        rootData.addChild("block", ModelPartBuilder.create().uv(0, 0).cuboid(-8.0F, -16.0F, -8.0F, 16.0F, 16.0F, 16.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        rootData.addChild("tail", ModelPartBuilder.create().uv(0, 32).cuboid(-3.0F, -3.0F, -0.5F, 6.0F, 6.0F, 11.0F), ModelTransform.pivot(0.0F, 19.0F, 7.5F));
        return modelData;
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(getModelData(), 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return ROOT;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.BLOCK.yaw = headYaw * 0.005F;
        float l = animationProgress * 5F * 0.017453292F;
        this.TAIL.yaw = MathHelper.cos(l) + 0.2F * limbDistance;
        if (!entity.isOnGround())
            this.TAIL.yaw = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * limbDistance;
    }
}
