package com.dayofpi.super_block_world.client.renderer.mob;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public abstract class LeadableEntityRenderer<T extends MobEntity & IAnimatable> extends GeoEntityRenderer<T> {
    protected LeadableEntityRenderer(EntityRendererFactory.Context ctx, AnimatedGeoModel<T> modelProvider) {
        super(ctx, modelProvider);
    }

    private int getBlockLightGeo(Entity entity, BlockPos pos) {
        if (entity.isOnFire()) {
            return 15;
        }
        return entity.world.getLightLevel(LightType.BLOCK, pos);
    }

    @Override
    public void render(T entity, float entityYaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int packedLightIn) {
        super.render(entity, entityYaw, tickDelta, matrices, provider, packedLightIn);
        Entity holdingEntity = entity.getHoldingEntity();
        if (holdingEntity == null) {
            return;
        }
        int u;
        matrices.push();
        Vec3d vec3d = holdingEntity.getLeashPos(tickDelta);
        double d = (double)(MathHelper.lerp(tickDelta, entity.bodyYaw, entity.prevBodyYaw) * ((float)Math.PI / 180)) + 1.5707963267948966;
        Vec3d vec3d2 = entity.getLeashOffset();
        double e = Math.cos(d) * vec3d2.z + Math.sin(d) * vec3d2.x;
        double f = Math.sin(d) * vec3d2.z - Math.cos(d) * vec3d2.x;
        double g = MathHelper.lerp(tickDelta, entity.prevX, entity.getX()) + e;
        double h = MathHelper.lerp(tickDelta, entity.prevY, entity.getY()) + vec3d2.y;
        double i = MathHelper.lerp(tickDelta, entity.prevZ, entity.getZ()) + f;
        matrices.translate(e, vec3d2.y, f);
        float j = (float)(vec3d.x - g);
        float k = (float)(vec3d.y - h);
        float l = (float)(vec3d.z - i);
        float m = 0.025f;
        VertexConsumer vertexConsumer = provider.getBuffer(RenderLayer.getLeash());
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        float n = MathHelper.fastInverseSqrt(j * j + l * l) * 0.025f / 2.0f;
        float o = l * n;
        float p = j * n;
        BlockPos blockPos = new BlockPos(entity.getCameraPosVec(tickDelta));
        BlockPos blockPos2 = new BlockPos(holdingEntity.getCameraPosVec(tickDelta));
        int q = this.getBlockLight(entity, blockPos);
        int r = this.getBlockLightGeo(holdingEntity, blockPos2);
        int s = entity.world.getLightLevel(LightType.SKY, blockPos);
        int t = entity.world.getLightLevel(LightType.SKY, blockPos2);
        for (u = 0; u <= 24; ++u) {
            renderLeashPieceGeo(vertexConsumer, matrix4f, j, k, l, q, r, s, t, 0.025f, 0.025f, o, p, u, false);
        }
        for (u = 24; u >= 0; --u) {
            renderLeashPieceGeo(vertexConsumer, matrix4f, j, k, l, q, r, s, t, 0.025f, 0.0f, o, p, u, true);
        }
        matrices.pop();
    }

    private static void renderLeashPieceGeo(VertexConsumer vertexConsumer, Matrix4f positionMatrix, float f, float g, float h, int leashedEntityBlockLight, int holdingEntityBlockLight, int leashedEntitySkyLight, int holdingEntitySkyLight, float i, float j, float k, float l, int pieceIndex, boolean isLeashKnot) {
        float m = (float)pieceIndex / 24.0f;
        int n = (int)MathHelper.lerp(m, leashedEntityBlockLight, holdingEntityBlockLight);
        int o = (int)MathHelper.lerp(m, leashedEntitySkyLight, holdingEntitySkyLight);
        int p = LightmapTextureManager.pack(n, o);
        float q = pieceIndex % 2 == (isLeashKnot ? 1 : 0) ? 0.7f : 1.0f;
        float r = 0.5f * q;
        float s = 0.4f * q;
        float t = 0.3f * q;
        float u = f * m;
        float v = g > 0.0f ? g * m * m : g - g * (1.0f - m) * (1.0f - m);
        float w = h * m;
        vertexConsumer.vertex(positionMatrix, u - k, v + j, w + l).color(r, s, t, 1.0f).light(p).next();
        vertexConsumer.vertex(positionMatrix, u + k, v + i - j, w - l).color(r, s, t, 1.0f).light(p).next();
    }
}
