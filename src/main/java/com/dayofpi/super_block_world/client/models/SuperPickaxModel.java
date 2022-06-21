package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.misc.SuperPickaxEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

@Environment(EnvType.CLIENT)
public class SuperPickaxModel extends SinglePartEntityModel<SuperPickaxEntity> {
    private final ModelPart root;

    public SuperPickaxModel(ModelPart modelPart) {
        this.root = modelPart.getChild("root");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 8).cuboid(-1.0F, -10.0F, -1.0F, 2.0F, 10.0F, 2.0F)
                .uv(0, 0).cuboid(-2.0F, -9.0F, -2.0F, 4.0F, 4.0F, 4.0F)
                .uv(8, 13).mirrored().cuboid(2.0F, -8.0F, -1.0F, 3.0F, 3.0F, 2.0F).mirrored(false)
                .uv(8, 8).cuboid(-5.0F, -8.0F, -1.0F, 3.0F, 3.0F, 2.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(SuperPickaxEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
