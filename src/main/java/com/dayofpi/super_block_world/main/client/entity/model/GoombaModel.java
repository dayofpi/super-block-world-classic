package com.dayofpi.super_block_world.main.client.entity.model;

import com.dayofpi.super_block_world.main.common.entity.mob.goomba.GoombaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
@Environment(EnvType.CLIENT)
public class GoombaModel<T extends GoombaEntity> extends SinglePartEntityModel<T> {
    private final ModelPart ROOT;
    private final ModelPart HEAD;
    private final ModelPart LEFT_FOOT;
    private final ModelPart RIGHT_FOOT;

    public GoombaModel(ModelPart root) {
        this.ROOT = root;
        ModelPart body = root.getChild("body");
        this.HEAD = body.getChild("head");
        this.LEFT_FOOT = body.getChild("left_foot");
        this.RIGHT_FOOT = body.getChild("right_foot");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(20, 18).cuboid(-2.5F, -5.0F, -2.5F, 5.0F, 3.0F, 5.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        body.addChild("head", ModelPartBuilder.create().uv(0,0).cuboid(-4.5F, -8.5F, -4.5F, 9.0F, 9.0F, 9.0F), ModelTransform.pivot(0.0F, -5.5F, 0.0F));
        body.addChild("left_foot", ModelPartBuilder.create().uv(0, 18).cuboid(1.0F, 1.0F, -3.0F, 4.0F, 2.0F, 6.0F), ModelTransform.pivot(0.0F, -3.0F, 0.0F));
        body.addChild("right_foot", ModelPartBuilder.create().uv(0, 18).cuboid(-5.0F, 1.0F, -3.0F, 4.0F, 2.0F, 6.0F), ModelTransform.pivot(0.0F, -3.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public ModelPart getPart() {
        return ROOT;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.ROOT.roll = 0.05F * MathHelper.sin(limbAngle) * limbDistance;
        this.HEAD.yaw = headYaw * 0.017453292F;
        this.HEAD.pitch = headPitch * 0.017453292F;
        this.RIGHT_FOOT.pitch = MathHelper.cos(limbAngle + 3.1415927F) * 1.6F * limbDistance;
        this.LEFT_FOOT.pitch = MathHelper.cos(limbAngle) * 1.6F * limbDistance;
    }
}
