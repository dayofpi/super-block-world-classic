package com.dayofpi.sbw_main.entity.model;

import com.dayofpi.sbw_main.entity.types.BuzzyShellEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

public class BuzzyShellModel<T extends BuzzyShellEntity> extends SinglePartEntityModel<T> {
    private final ModelPart BODY;

    public BuzzyShellModel(ModelPart root) {
        this.BODY = root.getChild("body");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(44, 27).cuboid(-5.0F, -10.0F, -7.0F, 10.0F, 6.0F, 14.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        body.addChild("shell", ModelPartBuilder.create().uv(0, 0).cuboid(-9.0F, -19F, -9.0F, 18.0F, 9.0F, 18.0F).uv(0, 27).cuboid(-9.0F, -10F, -9.0F, 4.0F, 7.0F, 18.0F).mirrored().uv(0, 27).cuboid(5.0F, -10F, -9.0F, 4.0F, 7.0F, 18.0F), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.BODY;
    }

    @Override
    public void setAngles(BuzzyShellEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
