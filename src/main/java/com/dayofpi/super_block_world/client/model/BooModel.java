package com.dayofpi.super_block_world.client.model;

import com.dayofpi.super_block_world.common.entity.mob.ghost.BooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.MathHelper;
@Environment(EnvType.CLIENT)
public class BooModel<T extends BooEntity> extends SinglePartEntityModel<T> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart tongue;
    private final ModelPart right_arm;
    private final ModelPart left_arm;
    private final ModelPart right_cover;
    private final ModelPart left_cover;

    public BooModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.tongue = body.getChild("tongue");
        this.right_arm = body.getChild("right_arm");
        this.left_arm = body.getChild("left_arm");
        this.right_cover = body.getChild("right_cover");
        this.left_cover = body.getChild("left_cover");
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(0,0)
        .cuboid(-6.0F, -12.0F, -6.0F, 12.0F, 12.0F, 12.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        body.addChild("tongue", ModelPartBuilder.create().uv(19, 25).cuboid(-1.5F, -0.5F, -5.0F, 3.0F, 1.0F, 6.0F), ModelTransform.pivot(0.0F, -2.5F, -5.0F));
        body.addChild("right_arm", ModelPartBuilder.create().uv(0,24).cuboid(5.0F, -7.0F, -2.0F, 5.0F, 4.0F, 4.0F), ModelTransform.NONE);
        body.addChild("left_arm", ModelPartBuilder.create().uv(0,24).cuboid(-10.0F, -7.0F, -2.0F, 5.0F, 4.0F, 4.0F), ModelTransform.NONE);
        body.addChild("right_cover", ModelPartBuilder.create().uv(0,24).cuboid(-2.5F, -2.0F, -2.0F, 5.0F, 4.0F, 4.0F), ModelTransform.pivot(5.5F, -6.0F, -6.0F));
        body.addChild("left_cover", ModelPartBuilder.create().uv(0,24).cuboid(-2.5F, -2.0F, -2.0F, 5.0F, 4.0F, 4.0F), ModelTransform.pivot(-5.5F, -6.0F, -6.0F));
        return modelData;
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(getModelData(), 64, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.right_arm.visible = !entity.isHiding() || entity.isDabbing();
        this.left_arm.visible = !entity.isHiding() || entity.isDabbing();
        this.right_cover.visible = entity.isHiding() && !entity.isDabbing();
        this.left_cover.visible = entity.isHiding() && !entity.isDabbing();
        this.right_cover.yaw = -0.8727F;
        this.left_cover.yaw = 0.8727F;
        this.body.yaw = headYaw * 0.01F;
        this.body.pitch = headPitch * 0.01F;
        this.body.roll = headPitch * 0.01F;

        float cosF = animationProgress * 5F * 0.017453292F;

        this.tongue.pitch = MathHelper.cos(cosF * 0.8F) * 0.2F + 0.2F;
        this.tongue.yaw = MathHelper.cos(limbAngle * 0.2F) * limbDistance;
        if (!entity.getEquippedStack(EquipmentSlot.MAINHAND).isEmpty()) {
            this.right_arm.setAngles(0F,0.6F, 1.5F);
            this.right_arm.setPivot(0, -5, 0);
            this.left_arm.setAngles(0F,-0.6F, -1.5F);
            this.left_arm.setPivot(0, -5, 0);
        } else {
            this.right_arm.setPivot(0, 0, 0);
            this.left_arm.setPivot(0, 0, 0);

            if (entity.isHiding() && entity.isDabbing()) {
                this.right_arm.setAngles(0F,2F,0F);
                this.left_arm.setAngles(0F,2F,0F);

            } else if (entity.isAttacking()) {
                this.right_arm.setAngles(0F,0F, -0.2F);
                this.left_arm.setAngles(0F,0F, 0.2F);

            } else if (entity.isInSittingPose()) {
                this.right_arm.setAngles(0F,0.4F, 0.2F);
                this.left_arm.setAngles(0F,-0.4F, -0.2F);
            } else {
                this.right_arm.setAngles(0F,0F, MathHelper.cos(cosF) * 0.2F);
                this.left_arm.setAngles(0F,0F, MathHelper.cos(cosF) * -0.2F);
            }
        }
    }
}
