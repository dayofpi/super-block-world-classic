package com.dayofpi.super_block_world.client.entity.model;

import com.dayofpi.super_block_world.main.common.entity.mob.ThwompEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public class ThwompModel<T extends ThwompEntity> extends SinglePartEntityModel<T> {
    private final ModelPart ROOT;
    private final ModelPart RIGHT_EYE;
    private final ModelPart LEFT_EYE;

    public ThwompModel(ModelPart root) {
        this.ROOT = root;
        this.RIGHT_EYE = root.getChild("right_eye");
        this.LEFT_EYE = root.getChild("left_eye");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("right_eye", ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F)
                .cuboid(-1.0F, -1.0F, 15.2F, 2.0F, 2.0F, 1.0F).mirrored(false), ModelTransform.pivot(-2.0F, 7.0F, -7.8F));
        modelPartData.addChild("left_eye", ModelPartBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -0.5F, 2.0F, 2.0F, 1.0F)
                .cuboid(-1.0F, -1.0F, 15.2F, 2.0F, 2.0F, 1.0F), ModelTransform.pivot(2.0F, 7.0F, -7.8F));
        modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, -29.0F, -8.0F, 20.0F, 24.0F, 16.0F)
                .uv(0, 40).cuboid(-12.0F, -32.0F, -4.0F, 24.0F, 30.0F, 8.0F)
                .uv(72, 0).cuboid(-14.0F, -34.0F, -5.0F, 8.0F, 10.0F, 10.0F)
                .uv(72, 0).cuboid(6.0F, -34.0F, -5.0F, 8.0F, 10.0F, 10.0F)
                .uv(72, 0).cuboid(6.0F, -10.0F, -5.0F, 8.0F, 10.0F, 10.0F)
                .uv(72, 0).cuboid(-14.0F, -10.0F, -5.0F, 8.0F, 10.0F, 10.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }
    @Override
    public ModelPart getPart() {
        return ROOT;
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        Entity target = MinecraftClient.getInstance().getCameraEntity();
        if (entity.getEyeTarget() != null)
            target = entity.getEyeTarget();

        if (target != null) {
            Vec3d vec3d = target.getCameraPosVec(0.0f);
            Vec3d vec3d2 = entity.getCameraPosVec(0.0f);
            Vec3d vec3d3 = entity.getRotationVec(0.0f);
            vec3d3 = new Vec3d(vec3d3.x, 0.0, vec3d3.z);
            Vec3d vec3d4 = new Vec3d(vec3d2.x - vec3d.x, 0.0, vec3d2.z - vec3d.z).normalize().rotateY(1.5707964f);
            double e = vec3d3.dotProduct(vec3d4);
            float rotation = entity.getYaw() == 0 ? -2F : 2F;
            this.RIGHT_EYE.pivotX = MathHelper.sqrt((float)Math.abs(e)) * rotation * (float)Math.signum(e) - 1.5F;
            this.LEFT_EYE.pivotX = MathHelper.sqrt((float)Math.abs(e)) * rotation * (float)Math.signum(e) + 2.5F;
            if (this.RIGHT_EYE.pivotX > -2.0F)
                this.RIGHT_EYE.pivotX = -2.0F;
            if (this.LEFT_EYE.pivotX < 2.0F)
                this.LEFT_EYE.pivotX = 2.0F;
        }

    }
}
