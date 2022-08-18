package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.client.registry.ModAnimations;
import com.dayofpi.super_block_world.common.entities.boss.KingBooEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class KingBooModel extends SinglePartEntityModel<KingBooEntity> {
    private final ModelPart root;
    private final ModelPart tongue;
    private float alpha;
    public KingBooModel(ModelPart modelPart) {
        this.root = modelPart.getChild(EntityModelPartNames.ROOT);
        this.tongue = this.root.getChild(EntityModelPartNames.TONGUE);
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild(EntityModelPartNames.ROOT, ModelPartBuilder.create().uv(0, 64).cuboid(-7.0F, -40.0F, -7.0F, 14.0F, 8.0F, 14.0F, new Dilation(0.0F))
                .uv(44, 84).cuboid(-6.0F, -41.0F, -6.0F, 12.0F, 8.0F, 12.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(-16.0F, -32.0F, -16.0F, 32.0F, 32.0F, 32.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        root.addChild(EntityModelPartNames.LEFT_ARM, ModelPartBuilder.create().uv(0, 86).mirrored().cuboid(9.0F, -4.0F, -4.0F, 10.0F, 8.0F, 8.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(7.0F, -16.0F, -1.0F));
        root.addChild(EntityModelPartNames.RIGHT_ARM, ModelPartBuilder.create().uv(0, 86).cuboid(-19.0F, -4.0F, -4.0F, 10.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-7.0F, -16.0F, -1.0F));
        root.addChild(EntityModelPartNames.TONGUE, ModelPartBuilder.create().uv(56, 64).cuboid(-5.0F, 3.0F, -16.5F, 10.0F, 5.0F, 15.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -11.0F, -15.5F, 0.5672F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        alpha = this.alpha;
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(KingBooEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.alpha = entity.getAlpha();
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.updateAnimation(entity.chargingAnimationState, ModAnimations.KingBoo.CHARGE, animationProgress);

        this.root.pitch = headPitch * (MathHelper.PI / 180F) * 0.2F;
        this.root.yaw = headYaw * (MathHelper.PI / 180F) * 0.2F;
        this.root.roll = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 0.2F * limbDistance;

        float progress = animationProgress * 5F * 0.04F;
        this.tongue.yaw = MathHelper.cos(progress * 0.5F) * 0.1F;
        this.tongue.pitch = 0.2F + MathHelper.cos(progress * 0.5F) * 0.05F;
    }
}
