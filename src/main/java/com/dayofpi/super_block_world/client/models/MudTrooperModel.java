package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.hostile.MudTrooperEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

public class MudTrooperModel extends SinglePartEntityModel<MudTrooperEntity> {
    private final ModelPart ROOT;
    private final ModelPart RIGHT_FOOT;
    private final ModelPart LEFT_FOOT;
    private final ModelPart LEFT_ARM;
    private final ModelPart RIGHT_ARM;

    public MudTrooperModel(ModelPart modelPart) {
        this.ROOT = modelPart.getChild("root");
        this.RIGHT_FOOT = this.ROOT.getChild("right_foot");
        this.LEFT_FOOT = this.ROOT.getChild("left_foot");
        this.RIGHT_ARM = this.ROOT.getChild("right_arm");
        this.LEFT_ARM = this.ROOT.getChild("left_arm");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 22).cuboid(-5.0F, -3.0F, -5.0F, 10.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-5.0F, -15.0F, -5.0F, 10.0F, 12.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 27).cuboid(0.0F, -18.0F, -3.0F, 0.0F, 3.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        root.addChild("right_foot", ModelPartBuilder.create().uv(32, 14).cuboid(-2.5F, 3.5F, -4.0F, 5.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -6.5F, 0.0F));

        root.addChild("left_foot", ModelPartBuilder.create().uv(32, 14).cuboid(-2.5F, 3.5F, -4.0F, 5.0F, 3.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, -6.5F, 0.0F));

        root.addChild("left_arm", ModelPartBuilder.create().uv(33, 27).cuboid(-2.0F, -2.0F, -7.5F, 4.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(6.0F, -7.0F, 0.5F));

        root.addChild("right_arm", ModelPartBuilder.create().uv(33, 27).cuboid(-2.0F, -2.0F, -7.5F, 4.0F, 4.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.0F, -7.0F, 0.5F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.ROOT;
    }

    @Override
    public void setAngles(MudTrooperEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.ROOT.roll = MathHelper.cos(entity.limbAngle * 0.6662F) * entity.limbDistance * 0.5F;
        float progress = ageInTicks * 2F * 0.01F;
        this.RIGHT_ARM.pitch = (MathHelper.cos(progress) * 0.2F);
        this.LEFT_ARM.pitch = (MathHelper.cos(progress) * 0.2F);
        this.RIGHT_ARM.yaw = (MathHelper.cos(entity.limbAngle * 0.6662F) * entity.limbDistance);
        LEFT_ARM.yaw = (MathHelper.cos(entity.limbAngle * 0.6662F) * entity.limbDistance);
        RIGHT_ARM.roll = ((MathHelper.cos(progress) * 0.2F) + 0.5F);
        LEFT_ARM.roll = ((MathHelper.cos(progress) * -0.2F) - 0.5F);


        this.RIGHT_FOOT.pitch = MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance;
        this.LEFT_FOOT.pitch = MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance;
        this.RIGHT_FOOT.yaw = 0.45F;
        this.LEFT_FOOT.yaw = -0.45F;
    }
}