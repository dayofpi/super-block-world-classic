package com.dayofpi.super_block_world.entity.models;

import com.dayofpi.super_block_world.entity.entities.hostile.AbstractBuzzy;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.util.math.MathHelper;
@Environment(EnvType.CLIENT)
public class BuzzyBeetleModel<T extends AbstractBuzzy> extends SinglePartEntityModel<T> {
    private final ModelPart body;
    private final ModelPart shell;
    private final ModelPart leftFrontLeg;
    private final ModelPart rightFrontLeg;
    private final ModelPart leftHindLeg;
    private final ModelPart rightHindLeg;
    private final ModelPart spike1;
    private final ModelPart spike2;
    private final ModelPart spikeBase;

    public BuzzyBeetleModel(ModelPart root) {
        this.body = root.getChild("body");
        this.shell = body.getChild("shell");
        this.leftFrontLeg = body.getChild("left_front_leg");
        this.rightFrontLeg = body.getChild("right_front_leg");
        this.leftHindLeg = body.getChild("left_hind_leg");
        this.rightHindLeg = body.getChild("right_hind_leg");
        this.spike1 = shell.getChild("spike_1");
        this.spike2 = shell.getChild("spike_2");
        this.spikeBase = shell.getChild("spike_base");
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
        shell.addChild("spike_base", ModelPartBuilder.create().uv(74, 0).cuboid(-5.0F, -11.8333F, -5.0F, 10.0F, 2.0F, 10.0F), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 128, 64);
    }

    @Override
    public ModelPart getPart() {
        return this.body;
    }

    public Iterable<ModelPart> getSpikeParts() {
        return ImmutableList.of(this.spike1, this.spike2, this.spikeBase);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.spike1.yaw = 0.7854F;
        this.spike2.yaw = -16.8333F;
        this.body.setAngles(0F, 0F, 0.1F * MathHelper.sin(limbAngle) * limbDistance);
        this.shell.roll = 0.1F * MathHelper.sin(limbAngle) * limbDistance;
        this.leftFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.rightFrontLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.leftHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
        this.rightHindLeg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
    }
}
