package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.hostile.ShyGuyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class ShyGuyModel extends SinglePartEntityModel<ShyGuyEntity> {
    private final ModelPart root;
    private final ModelPart left_foot;
    private final ModelPart right_foot;
    private final ModelPart body;
    private final ModelPart right_arm;
    private final ModelPart left_arm;

    public ShyGuyModel(ModelPart modelPart) {
        this.root = modelPart.getChild("root");
        this.left_foot = this.root.getChild("left_foot");
        this.right_foot = this.root.getChild("right_foot");
        this.body = this.root.getChild("body");
        this.left_arm = this.body.getChild("left_arm");
        this.right_arm = this.body.getChild("right_arm");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        root.addChild("left_foot", ModelPartBuilder.create().uv(0, 19).cuboid(-2.0F, 4.0F, -4.0F, 4.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, -6.0F, 1.0F, 0.0F, -0.0349F, 0.0F));

        root.addChild("right_foot", ModelPartBuilder.create().uv(0, 19).cuboid(-2.0F, 4.0F, -4.0F, 4.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-2.0F, -6.0F, 1.0F, 0.0F, 0.0349F, 0.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -11.5F, -4.1667F, 8.0F, 11.0F, 8.0F, new Dilation(0.0F))
                .uv(24, 0).cuboid(-3.0F, -10.5F, 3.8333F, 6.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(20, 19).cuboid(-4.0F, -10.5F, -5.1667F, 8.0F, 8.0F, 1.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -1.5F, 0.1667F));

        body.addChild("right_arm", ModelPartBuilder.create().uv(0, 0).cuboid(-2.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, -4.5F, -0.1667F));

        body.addChild("left_arm", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(0.0F, -1.0F, -1.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(4.0F, -4.5F, -0.1667F));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(ShyGuyEntity entity, float limbAngle, float limbDistance, float ageInTicks, float netHeadYaw, float headPitch) {
        this.body.roll = MathHelper.cos(entity.limbAngle * 0.6662F) * 0.5F * entity.limbDistance;


        this.right_foot.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.left_foot.pitch = MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance;

        if (!entity.isAttacking()) {
            this.right_arm.roll = 0F;
            this.left_arm.roll = 0F;
        }
        else {
            this.right_arm.roll = 1.5F;
            this.left_arm.roll = -1.5F;
            return;
        }

        this.right_arm.pitch = MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance;
        this.left_arm.pitch = MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance;

    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}