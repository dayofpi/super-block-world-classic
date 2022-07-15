package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.misc.StampEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.BakedModelManagerHelper;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.BlockRenderManager;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

@SuppressWarnings("deprecation")
@Environment(value= EnvType.CLIENT)
public class StampRenderer<T extends StampEntity> extends EntityRenderer<T> {
    private final BlockRenderManager blockRenderManager;

    public StampRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.blockRenderManager = context.getBlockRenderManager();
    }

    @Override
    public Identifier getTexture(StampEntity entity) {
        return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE;
    }

    @Override
    public Vec3d getPositionOffset(T entity, float f) {
        return new Vec3d((float) entity.getHorizontalFacing().getOffsetX() * 0.3f, -0.25, (float) entity.getHorizontalFacing().getOffsetZ() * 0.3f);
    }

    @Override
    public void render(T stamp, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light) {
        super.render(stamp, yaw, tickDelta, matrixStack, vertexConsumerProvider, light);
        matrixStack.push();
        Direction direction = stamp.getHorizontalFacing();
        Vec3d vec3d = this.getPositionOffset(stamp, tickDelta);
        matrixStack.translate(-vec3d.getX(), -vec3d.getY(), -vec3d.getZ());
        matrixStack.translate((double)direction.getOffsetX() * 0.46875, (double)direction.getOffsetY() * 0.46875, (double)direction.getOffsetZ() * 0.46875);
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(stamp.getPitch()));
        matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0f - stamp.getYaw()));

        int stampRotation = stamp.getRotation();
        matrixStack.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion((float)stampRotation * 360.0f / 8.0f));
        boolean bl = stamp.isInvisible();
        if (!bl) {
            BakedModelManager bakedModelManager = this.blockRenderManager.getModels().getModelManager();
            Identifier identifier = new Identifier(Main.MOD_ID, "block/stamp_" + stamp.getStamp().getName());
            matrixStack.push();
            matrixStack.translate(-0.5, -0.5, -0.5);
            this.blockRenderManager.getModelRenderer().render(matrixStack.peek(), vertexConsumerProvider.getBuffer(TexturedRenderLayers.getEntityCutout()), null, BakedModelManagerHelper.getModel(bakedModelManager, identifier), 1.0f, 1.0f, 1.0f, light, OverlayTexture.DEFAULT_UV);
            matrixStack.pop();
        }
        matrixStack.pop();
    }
}
