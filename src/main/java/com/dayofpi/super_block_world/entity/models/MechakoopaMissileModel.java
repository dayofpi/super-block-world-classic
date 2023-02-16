package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.projectile.MechakoopaMissileEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

@Environment(EnvType.CLIENT)
public class MechakoopaMissileModel extends SinglePartEntityModel<MechakoopaMissileEntity> {
    private final ModelPart root;
    public MechakoopaMissileModel(ModelPart root) {
        this.root = root.getChild("root");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 0).cuboid(-3.0F, -6.0F, -4.0F, 6.0F, 6.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 24.0F, 0.0F, -3.1416F, 0.0F, 3.1416F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(MechakoopaMissileEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
