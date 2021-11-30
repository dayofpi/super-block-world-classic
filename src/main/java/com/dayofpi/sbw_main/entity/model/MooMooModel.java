package com.dayofpi.sbw_main.entity.model;

import com.dayofpi.sbw_main.entity.type.mobs.MooMooEntity;
import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
@Environment(EnvType.CLIENT)
public class MooMooModel<T extends MooMooEntity> extends AnimalModel<T> {
    private final ModelPart head;
    private final ModelPart ear1;
    private final ModelPart ear2;
    private final ModelPart body;
    private final ModelPart rf_leg;
    private final ModelPart lf_leg;
    private final ModelPart rb_leg;
    private final ModelPart lb_leg;

    public MooMooModel(ModelPart root) {
        super(true, 12.0F, 2F);
        this.head = root.getChild("head");
        this.ear1 = head.getChild("ear1");
        this.ear2 = head.getChild("ear2");
        this.body = root.getChild("body");
        this.rf_leg = root.getChild("rf_leg");
        this.lf_leg = root.getChild("lf_leg");
        this.rb_leg = root.getChild("rb_leg");
        this.lb_leg = root.getChild("lb_leg");
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData head = modelPartData.addChild("head", ModelPartBuilder.create().uv(0, 24).cuboid(-4.0F, -5.0F, -4.5F, 8.0F, 9.0F, 6.0F).uv(0,0).cuboid("right_horn",-4.0F, -8.0F, -2.5F, 2.0F, 3.0F, 2.0F).uv(0,0).cuboid("left_horn",2.0F, -8.0F, -2.5F, 2.0F, 3.0F, 2.0F).mirrored().uv(28,35).cuboid("snoot",-4.0F, -1.0F, -6.5F, 8.0F, 5.0F, 2.0F), ModelTransform.pivot(0.0F, 13.5F, -7.5F));
        head.addChild("ear1", ModelPartBuilder.create().uv(13, 40).cuboid(-5.5F, -0.5F, -1.5F, 5.0F, 1.0F, 3.0F), ModelTransform.pivot(-3.5F, -3.0F, -1.0F));
        head.addChild("ear2", ModelPartBuilder.create().uv(0, 39).cuboid(0.5F, -0.5F, -1.5F, 5.0F, 1.0F, 3.0F).mirrored(), ModelTransform.pivot(3.5F, -3.0F, -1.0F));
        modelPartData.addChild("body", ModelPartBuilder.create().uv(0,0).cuboid(-5.0F, -16.0F, -7.0F, 10.0F, 11.0F, 13.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        modelPartData.addChild("rf_leg", ModelPartBuilder.create().uv(33,0).cuboid(-2.0F, -1.5F, -2.0F, 4.0F, 7.0F, 4.0F), ModelTransform.pivot(-3.5F, 18.5F, -4.0F));
        modelPartData.addChild("lf_leg", ModelPartBuilder.create().uv(33,0).cuboid(-2.0F, -1.5F, -2.0F, 4.0F, 7.0F, 4.0F).mirrored(), ModelTransform.pivot(3.5F, 18.5F, -4.0F));
        modelPartData.addChild("rb_leg", ModelPartBuilder.create().uv(28,24).cuboid(-2.0F, -1.5F, -2.0F, 4.0F, 7.0F, 4.0F), ModelTransform.pivot(-3.5F, 18.5F, 5.0F));
        modelPartData.addChild("lb_leg", ModelPartBuilder.create().uv(33,0).cuboid(-2.0F, -1.5F, -2.0F, 4.0F, 7.0F, 4.0F).mirrored(), ModelTransform.pivot(3.5F, 18.5F, 5.0F));
        return modelData;
    }

    public static TexturedModelData getTexturedModelData() {
        return TexturedModelData.of(getModelData(), 64, 64);
    }

    @Override
    protected Iterable<ModelPart> getHeadParts() {
        return ImmutableList.of(this.head);
    }

    @Override
    protected Iterable<ModelPart> getBodyParts() {
        return ImmutableList.of(this.body, this.lf_leg, this.lb_leg, this.rf_leg, this.rb_leg);
    }

    protected Iterable<ModelPart> getLegs() {
        return ImmutableList.of(this.lf_leg, this.lb_leg, this.rf_leg, this.rb_leg);
    }

    @Override
    public void setAngles(MooMooEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.head.pitch = headPitch * 0.017453292F;
        this.head.yaw = headYaw * 0.017453292F;
        this.ear1.roll = -0.5236F + MathHelper.cos(limbAngle * 0.06662F) * 1.4F * limbDistance;
        this.ear2.roll = 0.5236F + MathHelper.cos(limbAngle * 0.06662F + 3.1415927F) * 1.4F * limbDistance;

        if (entity.isLying()) {
            this.getLegs().forEach((modelPart) -> modelPart.pitch = -1.5F);
        } else {
            this.rb_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
            this.lb_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
            this.rf_leg.pitch = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance;
            this.lf_leg.pitch = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        if (!this.child) {
            matrices.scale(1.5F, 1.5F, 1.5F);
            matrices.translate(0.0F, -0.5, 0.0F);
        } else {
            matrices.scale(0.5F, 0.5F, 0.5F);
            matrices.translate(0.0F, 1.5F, 0.0F);
        }
        this.getBodyParts().forEach((modelPart -> modelPart.render(matrices, vertices, light, overlay, red, green, blue, alpha)));
        this.getHeadParts().forEach((modelPart) -> modelPart.render(matrices, vertices, light, overlay, red, green, blue, alpha));
    }
}