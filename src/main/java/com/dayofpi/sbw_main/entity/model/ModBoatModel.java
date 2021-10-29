package com.dayofpi.sbw_main.entity.model;


import com.dayofpi.sbw_main.entity.types.ModBoatEntity;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class ModBoatModel extends CompositeEntityModel<ModBoatEntity> {
    private final ModelPart leftPaddle;
    private final ModelPart rightPaddle;
    private final ModelPart waterPatch;
    private final ImmutableList<ModelPart> parts;

    public ModBoatModel(ModelPart root) {
        this.leftPaddle = root.getChild("left_paddle");
        this.rightPaddle = root.getChild("right_paddle");
        this.waterPatch = root.getChild("water_patch");
        this.parts = ImmutableList.of(root.getChild("bottom"), root.getChild("back"), root.getChild("front"), root.getChild("right"), root.getChild("left"), this.leftPaddle, this.rightPaddle);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("bottom", ModelPartBuilder.create().uv(0, 0).cuboid(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F), ModelTransform.of(0.0F, 3.0F, 1.0F, 1.5707964F, 0.0F, 0.0F));
        modelPartData.addChild("back", ModelPartBuilder.create().uv(0, 19).cuboid(-13.0F, -7.0F, -1.0F, 18.0F, 6.0F, 2.0F), ModelTransform.of(-15.0F, 4.0F, 4.0F, 0.0F, 4.712389F, 0.0F));
        modelPartData.addChild("front", ModelPartBuilder.create().uv(0, 27).cuboid(-8.0F, -7.0F, -1.0F, 16.0F, 6.0F, 2.0F), ModelTransform.of(15.0F, 4.0F, 0.0F, 0.0F, 1.5707964F, 0.0F));
        modelPartData.addChild("right", ModelPartBuilder.create().uv(0, 35).cuboid(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F), ModelTransform.of(0.0F, 4.0F, -9.0F, 0.0F, 3.1415927F, 0.0F));
        modelPartData.addChild("left", ModelPartBuilder.create().uv(0, 43).cuboid(-14.0F, -7.0F, -1.0F, 28.0F, 6.0F, 2.0F), ModelTransform.pivot(0.0F, 4.0F, 9.0F));
        modelPartData.addChild("left_paddle", ModelPartBuilder.create().uv(62, 0).cuboid(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).cuboid(-1.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), ModelTransform.of(3.0F, -5.0F, 9.0F, 0.0F, 0.0F, 0.19634955F));
        modelPartData.addChild("right_paddle", ModelPartBuilder.create().uv(62, 20).cuboid(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).cuboid(0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), ModelTransform.of(3.0F, -5.0F, -9.0F, 0.0F, 3.1415927F, 0.19634955F));
        modelPartData.addChild("water_patch", ModelPartBuilder.create().uv(0, 0).cuboid(-14.0F, -9.0F, -3.0F, 28.0F, 16.0F, 3.0F), ModelTransform.of(0.0F, -3.0F, 1.0F, 1.5707964F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 64);
    }

    public void setAngles(ModBoatEntity boatEntity, float f, float g, float h, float i, float j) {
        setPaddleAngle(boatEntity, 0, this.leftPaddle, f);
        setPaddleAngle(boatEntity, 1, this.rightPaddle, f);
    }

    private static void setPaddleAngle(ModBoatEntity entity, int sigma, ModelPart part, float angle) {
        float f = entity.interpolatePaddlePhase(sigma, angle);
        part.pitch = MathHelper.clampedLerp(-1.0471976F, -0.2617994F, (MathHelper.sin(-f) + 1.0F) / 2.0F);
        part.yaw = MathHelper.clampedLerp(-0.7853982F, 0.7853982F, (MathHelper.sin(-f + 1.0F) + 1.0F) / 2.0F);
        if (sigma == 1) {
            part.yaw = 3.1415927F - part.yaw;
        }
    }

    public ImmutableList<ModelPart> getParts() {
        return this.parts;
    }

    public ModelPart getWaterPatch() {
        return this.waterPatch;
    }
}