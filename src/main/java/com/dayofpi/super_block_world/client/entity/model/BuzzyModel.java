package com.dayofpi.super_block_world.client.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
@Environment(EnvType.CLIENT)
public class BuzzyModel<T extends Entity> extends SinglePartEntityModel<T> {
    private final ModelPart BODY;
    private final ModelPart SHELL;
    private final ModelPart LEFT_FRONT_LEG;
    private final ModelPart RIGHT_FRONT_LEG;
    private final ModelPart LEFT_HIND_LEG;
    private final ModelPart RIGHT_HIND_LEG;
    private final ModelPart SPIKE_1;
    private final ModelPart SPIKE_2;
    private final ModelPart SUPPORT;

    public BuzzyModel(ModelPart root) {
        this.BODY = root.getChild("body");
        this.SHELL = BODY.getChild("shell");
        this.LEFT_FRONT_LEG = BODY.getChild("left_front_leg");
        this.RIGHT_FRONT_LEG = BODY.getChild("right_front_leg");
        this.LEFT_HIND_LEG = BODY.getChild("left_hind_leg");
        this.RIGHT_HIND_LEG = BODY.getChild("right_hind_leg");
        this.SPIKE_1 = SHELL.getChild("spike_1");
        this.SPIKE_2 = SHELL.getChild("spike_2");
        this.SUPPORT = SHELL.getChild("support");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create().uv(44, 27).cuboid(-5.0F, -10.0F, -7.0F, 10.0F, 6.0F, 14.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        ModelPartData shell = body.addChild("shell", ModelPartBuilder.create().uv(0, 0).cuboid(-9.0F, -9.8333F, -9.0F, 18.0F, 9.0F, 18.0F).uv(0, 27).cuboid(-9.0F, -0.8333F, -9.0F, 4.0F, 7.0F, 18.0F).mirrored().uv(0, 27).cuboid(5.0F, -0.8333F, -9.0F, 4.0F, 7.0F, 18.0F), ModelTransform.pivot(0.0F, -9.1667F, 0.0F));
        body.addChild("left_front_leg", ModelPartBuilder.create().uv(54, 0).cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 6.0F, 5.0F), ModelTransform.pivot(5.5F, -5.0F, -2.5F));
        body.addChild("right_front_leg", ModelPartBuilder.create().uv(54, 0).cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 6.0F, 5.0F), ModelTransform.pivot(-5.5F, -5.0F, -2.5F));
        body.addChild("left_hind_leg", ModelPartBuilder.create().uv(54, 0).cuboid(-2.5F, -1.0F, -2F, 5.0F, 6.0F, 5.0F), ModelTransform.pivot(5.5F, -5.0F, 5.5F));
        body.addChild("right_hind_leg", ModelPartBuilder.create().uv(54, 0).cuboid(-2.5F, -1.0F, -2.5F, 5.0F, 6.0F, 5.0F), ModelTransform.pivot(-5.5F, -5.0F, 5.5F));
        shell.addChild("spike_1", ModelPartBuilder.create().uv(72, 17).cuboid(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 0.0F), ModelTransform.pivot(0.0F, -16.8333F, 0.0F));
        shell.addChild("spike_2", ModelPartBuilder.create().uv(72, 17).cuboid(-5.0F, -5.0F, 0.0F, 10.0F, 10.0F, 0.0F), ModelTransform.pivot(0.0F, -16.8333F, 0.0F));
        shell.addChild("support", ModelPartBuilder.create().uv(74, 0).cuboid(-5.0F, -11.8333F, -5.0F, 10.0F, 2.0F, 10.0F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.BODY;
    }

    public Iterable<ModelPart> getSpikeParts() {
        return ImmutableList.of(this.SPIKE_1, this.SPIKE_2, this.SUPPORT);
    }

    @Override
    public void setAngles(Entity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.SPIKE_1.yaw = 0.7854F;
        this.SPIKE_2.yaw = -16.8333F;
        this.BODY.setAngles(0F, 0F, 0.1F * MathHelper.sin(limbAngle) * limbDistance);
        this.SHELL.roll = 0.1F * MathHelper.sin(limbAngle) * limbDistance;
        this.LEFT_FRONT_LEG.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.RIGHT_FRONT_LEG.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.LEFT_HIND_LEG.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.RIGHT_HIND_LEG.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    }
}
