package com.dayofpi.sbw_main.client.model;

import com.dayofpi.sbw_main.common.entity.type.mobs.KoopaEntity;
import com.dayofpi.sbw_main.common.entity.type.mobs.ParatroopaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
@Environment(EnvType.CLIENT)
public class KoopaModel<T extends KoopaEntity> extends SinglePartEntityModel<T> {
    private final ModelPart ROOT;
    private final ModelPart LEFT_LEG;
    private final ModelPart RIGHT_LEG;
    private final ModelPart HEAD;
    private final ModelPart RIGHT_WING;
    private final ModelPart LEFT_WING;
    private final ModelPart LEFT_ARM;
    private final ModelPart RIGHT_ARM;
    public KoopaModel(ModelPart root) {
        this.ROOT = root;
        this.LEFT_LEG = root.getChild("left_leg");
        this.RIGHT_LEG = root.getChild("right_leg");
        this.HEAD = root.getChild("head");
        this.LEFT_WING = root.getChild("left_wing");
        this.RIGHT_WING = root.getChild("right_wing");
        this.LEFT_ARM = root.getChild("left_arm");
        this.RIGHT_ARM = root.getChild("right_arm");
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0, 10)
        .cuboid(-5.5F, -4.0F, -5.0F, 10.0F, 9.0F, 10.0F), ModelTransform.of(0.5F, 13.0F, 0.0F,-0.3927F, 0.0F, 0.0F));

        modelPartData.addChild("left_arm", ModelPartBuilder.create().uv(0, 13)
        .cuboid(-1.5F, -0.5F, -1.0F, 3.0F, 5.0F, 2.0F), ModelTransform.pivot(2.0F, 16.0144F, -3.8771F));

        modelPartData.addChild("right_arm", ModelPartBuilder.create().uv(0, 13)
        .cuboid(-1.5F, -0.5F, -1.0F, 3.0F, 5.0F, 2.0F).mirrored(), ModelTransform.pivot(-2.0F, 16.0144F, -3.8771F));

        modelPartData.addChild("left_leg", ModelPartBuilder.create().uv(24, 0)
        .cuboid(-2.0F, -0.5F, -1.5F, 4.0F, 7.0F, 3.0F), ModelTransform.pivot(2.5F, 17.5F, 1.5F));

        modelPartData.addChild("right_leg", ModelPartBuilder.create().uv(24, 0)
        .cuboid(-2.0F, -0.5F, -1.5F, 4.0F, 7.0F, 3.0F).mirrored(), ModelTransform.pivot(-2.5F, 17.5F, 1.5F));

        modelPartData.addChild("left_wing", ModelPartBuilder.create().uv(40, 20)
        .cuboid(-1.0F, -10.0F, -0.5F, 2.0F, 10.0F, 7.0F), ModelTransform.of(5.0F, 10.9397F, -2.842F,-0.5672F, 0.0F, 0.0F));

        modelPartData.addChild("right_wing", ModelPartBuilder.create().uv(40, 20)
        .cuboid(-1.0F, -10.0F, -0.5F, 2.0F, 10.0F, 7.0F), ModelTransform.of(-5.0F, 10.9397F, -2.842F,-0.5672F, 0.0F, 0.0F));

        modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 0)
                .cuboid(-4.0F, -10.5F, -4.5F, 8.0F, 6.0F, 4.0F)
                .uv(30,8).cuboid(-4.0F, -4.5F, -7.5F, 8.0F, 4.0F, 8.0F), ModelTransform.pivot(0.0F, 10.5F, -3.5F));
        return modelData;
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(getModelData(), 64, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.ROOT;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.LEFT_WING.visible = entity instanceof ParatroopaEntity;
        this.RIGHT_WING.visible = entity instanceof ParatroopaEntity;
        float l = animationProgress * 5F * 0.017453292F;
        float a;
        if (entity instanceof ParatroopaEntity && !(entity).isOnGround()) {
            this.RIGHT_LEG.pitch = 0.30F;
            this.LEFT_LEG.pitch = 0.30F;
        } else {
            a = 0F;
            this.RIGHT_ARM.setAngles(a,0F, MathHelper.cos(l) * 0.2F);
            this.LEFT_ARM.setAngles(a,0F, MathHelper.cos(l) * 0.2F);
            this.RIGHT_LEG.pitch =  MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
            this.LEFT_LEG.pitch =  MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        }
        this.HEAD.pitch = headPitch * 0.017453292F;
        this.HEAD.yaw = headYaw * 0.017453292F;
        this.RIGHT_WING.roll =  MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * limbDistance;
        this.LEFT_WING.roll =  MathHelper.cos(limbAngle * 0.6662F) * limbDistance;
    }
}
