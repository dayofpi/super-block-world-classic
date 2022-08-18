package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.hostile.AbstractBro;
import com.dayofpi.super_block_world.common.entities.hostile.FireBroEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.BipedEntityModel;
@Environment(EnvType.CLIENT)
public class HammerBroModel<T extends AbstractBro> extends BipedEntityModel<T> {
    public HammerBroModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = BipedEntityModel.getModelData(Dilation.NONE, 0.0F);
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(28, 30).cuboid(-2.0F, 3.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(1.9f, 9.0f, 0.0f));
        modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(28, 30).mirrored().cuboid(-2.0F, 3.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-1.9f, 9.0f, 0.0f));

        modelPartData.addChild("head", ModelPartBuilder.create().uv(24, 10).cuboid(-4.0F, -3.0F, -5.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(24, 0).cuboid(-2.0F, 2.0F, -7.0F, 4.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0f, 0.0f, 0.0f));

        modelPartData.addChild("hat", ModelPartBuilder.create().uv(0, 18).cuboid(-4.0F, -3F, -5F, 8.0F, 8.0F, 8.0F, new Dilation(0.5F)), ModelTransform.NONE);

        modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(0, 34).mirrored().cuboid(-2.5F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5f, -5.0f, -2.0f));
        modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(0, 34).cuboid(-0.5F, -1.5F, -2.0F, 3.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5f, -5.0f, -2.0f));

        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 5.0F, -3.0F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0f, 0.0f, 0.0f));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float f, float g, float h, float i, float j) {
        super.setAngles(entity, f, g, h, i, j);
        this.rightArm.setPivot(-4.5F, 7.5F, -1.0F);
        this.leftArm.setPivot(4.5F, 7.5F, -1.0F);
        if (entity.isAttacking()) {
            getArm(entity.getMainArm()).pitch = -1.8F;
            if (entity instanceof FireBroEntity)
                getArm(entity.getMainArm()).roll = 0.5F;
        }
    }


}
