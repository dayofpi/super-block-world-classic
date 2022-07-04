package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.StampModel;
import com.dayofpi.super_block_world.common.entities.misc.StampEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.core.util.Color;
import software.bernie.geckolib3.geo.render.built.GeoModel;
import software.bernie.geckolib3.renderers.geo.GeoProjectilesRenderer;

public class StampRenderer<T extends StampEntity> extends GeoProjectilesRenderer<T> {
    public StampRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new StampModel<>());
    }

    @Override
    public Identifier getTexture(StampEntity entity) {
        return new Identifier(Main.MOD_ID, "textures/stamp/" + entity.getStamp().getName() + ".png");
    }

    @Override
    public void render(T entity, float yaw, float partialTicks, MatrixStack stack, VertexConsumerProvider bufferIn, int light) {
        GeoModel model = this.getGeoModelProvider().getModel(this.getGeoModelProvider().getModelResource(entity));
        Direction direction = entity.getHorizontalFacing();
        Vec3d vec3d = this.getPositionOffset(entity, partialTicks);
        int rotation = entity.getRotation();

        stack.push();
        stack.translate(-vec3d.getX(), -vec3d.getY(), -vec3d.getZ());
        stack.translate((double) direction.getOffsetX() * 0.46875, (double) direction.getOffsetY() * 0.46875, (double) direction.getOffsetZ() * 0.46875);
        stack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(entity.getPitch()));
        stack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - entity.getYaw()));
        stack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float) rotation * 360.0f / 8.0f));

        stack.translate(0.0, -0.5, 0.9999);
        stack.scale(0.85F, 0.85F, 1.0F);
        MinecraftClient.getInstance().getTextureManager().bindTexture(getTexture(entity));
        Color renderColor = getRenderColor(entity, partialTicks, stack, bufferIn, null, light);
        RenderLayer renderType = getRenderType(entity, partialTicks, stack, bufferIn, null, light, getTexture(entity));
        render(model, entity, partialTicks, renderType, stack, bufferIn, null, light, getPackedOverlay(entity, 0), (float) renderColor.getRed() / 255f, (float) renderColor.getGreen() / 255f, (float) renderColor.getBlue() / 255f, (float) renderColor.getAlpha() / 255);
        stack.pop();
    }
}
