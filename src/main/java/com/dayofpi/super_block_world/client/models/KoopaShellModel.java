package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.misc.KoopaShellEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

public class KoopaShellModel extends SinglePartEntityModel<KoopaShellEntity> {
    private final ModelPart root;

    public KoopaShellModel(ModelPart root) {
        this.root = root.getChild(EntityModelPartNames.ROOT);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create().uv(0, 14).cuboid(-5.0F, -8.0F, -5.0F, 10.0F, 8.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(KoopaShellEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
