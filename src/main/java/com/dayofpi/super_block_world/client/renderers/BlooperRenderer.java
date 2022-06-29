package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.client.models.BlooperModel;
import com.dayofpi.super_block_world.common.entities.hostile.BlooperEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class BlooperRenderer<T extends BlooperEntity> extends GeoEntityRenderer<T> {
    public BlooperRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BlooperModel<>());
        this.shadowRadius = 0.4F;
    }

    @Override
    protected void applyRotations(T entity, MatrixStack stack, float ageInTicks, float rotationYaw, float partialTicks) {
        if (entity.isBaby())
            stack.scale(0.5F, 0.5F, 0.5F);
        float tilt = MathHelper.lerp(partialTicks, entity.prevTiltAngle, entity.tiltAngle);
        float roll = MathHelper.lerp(partialTicks, entity.prevRollAngle, entity.rollAngle);

        stack.translate(0.0, 0.5, 0.0);
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - rotationYaw));
        stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(tilt));
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(roll));
        stack.translate(0.0, -1.2f, 0.0);
        super.applyRotations(entity, stack, ageInTicks, rotationYaw, partialTicks);
    }
}
