package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.common.entities.boss.KingBobOmbEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class KingBobOmbModel extends SinglePartEntityModel<KingBobOmbEntity> {
    private final ModelPart root;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    public KingBobOmbModel(ModelPart root) {
        this.root = root.getChild("root");
        this.right_arm = this.root.getChild(EntityModelPartNames.RIGHT_ARM);
        this.left_arm = this.root.getChild(EntityModelPartNames.LEFT_ARM);
        this.right_leg = this.root.getChild(EntityModelPartNames.RIGHT_LEG);
        this.left_leg = this.root.getChild(EntityModelPartNames.LEFT_LEG);
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create().uv(0, 0).cuboid(-9.0F, -28.0F, -9.0F, 18.0F, 18.0F, 18.0F, new Dilation(0.0F))
                .uv(54, 2).cuboid(-4.0F, -36.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F))
                .uv(32, 52).cuboid(-12.0F, -20.0F, -9.1F, 12.0F, 12.0F, 0.0F, new Dilation(0.0F))
                .uv(32, 52).mirrored().cuboid(0.0F, -20.0F, -9.1F, 12.0F, 12.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 36).cuboid(-3.5F, 4.0F, -5.25F, 7.0F, 6.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-1.5F, -2.0F, -1.25F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-3.5F, -10.0F, 0.25F));

        root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -2.0F, -1.25F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 36).cuboid(-3.5F, 4.0F, -5.25F, 7.0F, 6.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(4.5F, -10.0F, 0.25F));

        root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(0, 0).cuboid(-1.25F, -1.0F, -1.25F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F))
                .uv(34, 36).mirrored().cuboid(-3.25F, 5.0F, -4.25F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-9.75F, -18.0F, 0.25F, 0.0F, 0.0F, 0.6545F));

        root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(0, 0).cuboid(-0.75F, -1.0F, -1.25F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F))
                .uv(34, 36).cuboid(-2.75F, 5.0F, -4.25F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(8.75F, -18.0F, 0.25F, 0.0F, 0.0F, -0.6545F));
        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(KingBobOmbEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float progress = ageInTicks * 2F * 0.01F;
        right_arm.roll = ((MathHelper.cos(progress) * 0.2F) + 0.5F);
        left_arm.roll = ((MathHelper.cos(progress) * -0.2F) - 0.5F);

        if (entity.getThrowCooldown() < 40 && !entity.isLaughing()) {
            right_arm.pitch = (MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance);
            if (!entity.isSummoning())
                left_arm.pitch = (MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance);
        }
        right_leg.pitch = (MathHelper.cos(entity.limbAngle * 0.6662F) * 1.4F * entity.limbDistance);
        left_leg.pitch = (MathHelper.cos(entity.limbAngle * 0.6662F + 3.1415927F) * 1.4F * entity.limbDistance);

    }
}