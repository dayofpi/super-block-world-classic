package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.client.registry.ModAnimations;
import com.dayofpi.super_block_world.common.entities.passive.YoshiEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class YoshiModel extends SinglePartEntityModel<YoshiEntity> {
    private final ModelPart root;
    private final ModelPart head;
    private final ModelPart tongue;
    private final ModelPart cheeks;
    private final ModelPart leftArm;
    private final ModelPart rightArm;
    private final ModelPart leftLeg;
    private final ModelPart rightLeg;

    public YoshiModel(ModelPart root) {
        this.root = root.getChild(EntityModelPartNames.ROOT);
        ModelPart body = this.root.getChild(EntityModelPartNames.BODY);
        this.head = body.getChild(EntityModelPartNames.HEAD);
        this.tongue = this.head.getChild(EntityModelPartNames.TONGUE);
        this.cheeks = this.head.getChild("cheeks");
        this.leftArm = body.getChild(EntityModelPartNames.LEFT_ARM);
        this.rightArm = body.getChild(EntityModelPartNames.RIGHT_ARM);
        this.leftLeg = this.root.getChild(EntityModelPartNames.LEFT_LEG);
        this.rightLeg = this.root.getChild(EntityModelPartNames.RIGHT_LEG);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = root.addChild(EntityModelPartNames.BODY, ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -6.8333F, -9.3333F, 10.0F, 8.0F, 12.0F, new Dilation(0.0F)).uv(32, 41).cuboid(-3.0F, -6.8333F, 2.6667F, 6.0F, 4.0F, 4.0F, new Dilation(0.0F)).uv(0, 47).cuboid(-3.0F, -8.8333F, -9.3333F, 6.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -9.1667F, 2.3333F));
        ModelPartData head = body.addChild(EntityModelPartNames.HEAD, ModelPartBuilder.create().uv(32, 0).cuboid(-5.0F, -14.0F, -1.75F, 10.0F, 6.0F, 5.0F, new Dilation(0.0F)).uv(0, 33).cuboid(-5.0F, -9.0F, -7.75F, 10.0F, 8.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.8333F, -8.5833F));
        head.addChild(EntityModelPartNames.TONGUE, ModelPartBuilder.create().uv(58, 11).cuboid(-1.5F, 0.0F, 0.0F, 3.0F, 14.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.75F, 4.0F, -1.5708F, 0.0F, 0.0F));
        head.addChild(EntityModelPartNames.MOUTH, ModelPartBuilder.create().uv(32, 33).cuboid(-3.0F, -0.4824F, -4.0681F, 6.0F, 2.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -0.5F, -0.75F));
        head.addChild("cheeks", ModelPartBuilder.create().uv(0, 20).cuboid(-7.0F, -3.5F, -3.0F, 14.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -4.5F, 1.25F));
        body.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -2.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(4.5F, 0.1667F, -6.8333F, 0.0F, 0.0F, -0.4363F));
        body.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(0, 0).cuboid(-1.5F, -2.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(-4.5F, 0.1667F, -6.8333F, 0.0F, 0.0F, 0.4363F));
        root.addChild(EntityModelPartNames.LEFT_LEG, ModelPartBuilder.create().uv(40, 16).cuboid(-2.5F, 0.0F, -2.0F, 5.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, -8.0F, 1.0F));
        root.addChild(EntityModelPartNames.RIGHT_LEG, ModelPartBuilder.create().uv(40, 16).cuboid(-2.5F, 0.0F, -2.0F, 5.0F, 8.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.5F, -8.0F, 1.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(YoshiEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateAnimation(entity.usingTongueAnimationState, ModAnimations.Yoshi.USE_TONGUE, animationProgress);
        this.tongue.visible = entity.isTongueOut();
        if (!entity.getStoredEntity().isEmpty()) {
            this.cheeks.xScale = 1.3F;
            this.cheeks.yScale = 1.3F;
            this.cheeks.zScale = 1.3F;
        }

        head.pitch = headPitch * ((float) Math.PI / 180F) * 0.5F;
        head.yaw = headYaw * ((float) Math.PI / 180F) * 0.5F;

        this.rightArm.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leftArm.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;

        this.rightLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.leftLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
    }
}