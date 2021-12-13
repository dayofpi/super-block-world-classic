package com.dayofpi.super_block_world.main.client.model;

import com.dayofpi.super_block_world.main.common.entity.type.mobs.ParagoombaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
@Environment(EnvType.CLIENT)
public class ParagoombaModel<T extends ParagoombaEntity> extends SinglePartEntityModel<T> {
    private final ModelPart ROOT;
    private final ModelPart HEAD;
    private final ModelPart LEFT_FOOT;
    private final ModelPart RIGHT_FOOT;
    private final ModelPart LEFT_WING;
    private final ModelPart RIGHT_WING;

    public ParagoombaModel(ModelPart root) {
        this.ROOT = root;
        ModelPart body = root.getChild("body");
        ModelPart head = this.HEAD = body.getChild("head");
        this.LEFT_FOOT = body.getChild("left_foot");
        this.RIGHT_FOOT = body.getChild("right_foot");
        this.LEFT_WING = head.getChild("left_wing");
        this.RIGHT_WING = head.getChild("right_wing");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(20, 18).cuboid(-2.5F, -5.0F, -2.5F, 5.0F, 3.0F, 5.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(0,0).cuboid(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F), ModelTransform.pivot(0.0F, -5.5F, 0.0F));
        body.addChild("left_foot", ModelPartBuilder.create().uv(0, 18).cuboid(1.0F, 1.0F, -3.0F, 4.0F, 2.0F, 6.0F), ModelTransform.pivot(0.0F, -3.0F, 0.0F));
        body.addChild("right_foot", ModelPartBuilder.create().uv(0, 18).cuboid(-5.0F, 1.0F, -3.0F, 4.0F, 2.0F, 6.0F), ModelTransform.pivot(0.0F, -3.0F, 0.0F));
        head.addChild("left_wing", ModelPartBuilder.create().uv(40, 9).cuboid(-1.0F, -5.0F, 0.5F, 2.0F, 10.0F, 7.0F), ModelTransform.pivot(2.5F, -6.5F, 3.5F));
        head.addChild("right_wing", ModelPartBuilder.create().uv(40, 9).cuboid(-1.0F, -5.0F, 0.5F, 2.0F, 10.0F, 7.0F), ModelTransform.pivot(-2.5F, -6.5F, 3.5F));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public ModelPart getPart() {
        return ROOT;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.ROOT.roll = 0.05F * MathHelper.sin(limbAngle) * limbDistance;
        this.RIGHT_WING.yaw = (-0.5F) + MathHelper.cos(limbAngle + 3.1415927F) * 1.4F * limbDistance;
        this.LEFT_WING.yaw = (0.5F) + MathHelper.cos(limbAngle) * 1.4F * limbDistance;
        this.HEAD.yaw = headYaw * 0.017453292F;
        this.HEAD.pitch = headPitch * 0.017453292F;
        if (entity.isOnGround()) {
            this.RIGHT_FOOT.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
            this.LEFT_FOOT.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        } else {
            this.RIGHT_FOOT.pitch = -1.3F;
            this.LEFT_FOOT.pitch = -1.3F;
        }
    }
}
