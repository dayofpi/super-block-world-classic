package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.MechakoopaModel;
import com.dayofpi.super_block_world.common.entities.hostile.MechakoopaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.*;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class MechakoopaRenderer<T extends MechakoopaEntity> extends GeoEntityRenderer<T> {
    private static final RenderLayer BEAM_LAYER = RenderLayer.getEntityCutoutNoCull(new Identifier(Main.MOD_ID, "textures/entity/mechakoopa/beam.png"));
    private static final Identifier MECHAKOOPA = new Identifier(Main.MOD_ID, "textures/entity/mechakoopa/mechakoopa");
    private static final Identifier BLASTA = new Identifier(Main.MOD_ID, "textures/entity/mechakoopa/blasta");
    private static final Identifier ZAPPA = new Identifier(Main.MOD_ID, "textures/entity/mechakoopa/zappa");


    public MechakoopaRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new MechakoopaModel<>());
        this.shadowRadius = 0.3F;
    }

    private static void vertex(VertexConsumer vertexConsumer, Matrix4f positionMatrix, Matrix3f normalMatrix, float x, float y, float z, int red, int green, int blue, float u, float v) {
        vertexConsumer.vertex(positionMatrix, x, y, z).color(red, green, blue, 255).texture(u, v).overlay(OverlayTexture.DEFAULT_UV).light(15728880).normal(normalMatrix, 0.0F, 1.0F, 0.0F).next();
    }

    @Override
    public Identifier getTexture(T entity) {
        String string = ".png";
        if (entity.isInSittingPose())
            string = "_sit.png";
        return switch (entity.getPower()) {
            default -> Identifier.tryParse(MECHAKOOPA + string);
            case 1 -> Identifier.tryParse(BLASTA + string);
            case 2 -> Identifier.tryParse(ZAPPA + string);
        };
    }

    private Vec3d fromLerpedPosition(LivingEntity entity, double yOffset, float delta) {
        double d = MathHelper.lerp(delta, entity.lastRenderX, entity.getX());
        double e = MathHelper.lerp(delta, entity.lastRenderY, entity.getY()) + yOffset;
        double f = MathHelper.lerp(delta, entity.lastRenderZ, entity.getZ());
        return new Vec3d(d, e, f);
    }

    @Override
    public RenderLayer getRenderType(T animatable, float partialTicks, MatrixStack stack, VertexConsumerProvider renderTypeBuffer, VertexConsumer vertexBuilder, int packedLightIn, Identifier textureLocation) {
        return RenderLayer.getEntityCutoutNoCull(textureLocation);
    }

    public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(entity, entityYaw, partialTicks, matrixStack, vertexConsumerProvider, i);
        LivingEntity target = entity.getBeamTarget();
        if (target != null) {
            float beamProgress = entity.getBeamProgress(partialTicks);
            float time = ((float) entity.world.getTime() + partialTicks) * 0.5F;
            float eyeHeight = entity.getStandingEyeHeight();
            matrixStack.push();
            matrixStack.translate(0.0D, eyeHeight, 0.0D);
            Vec3d targetVec = this.fromLerpedPosition(target, (double) target.getHeight() * 0.5D, partialTicks);
            Vec3d entityVec = this.fromLerpedPosition(entity, eyeHeight, partialTicks);
            Vec3d diff = targetVec.subtract(entityVec);
            float diffL = (float) (diff.length() + 1.0D);
            diff = diff.normalize();
            float acos = (float) Math.acos(diff.y);
            float atan2 = (float) Math.atan2(diff.z, diff.x);
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((1.5707964F - atan2) * 57.295776F));
            matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(acos * 57.295776F));
            float invertedTime = time * 0.05F * -1.5F;
            float r = beamProgress * beamProgress;
            int s = 64 + (int) (r * 191.0F);
            int t = 64 + (int) (r * 191.0F);
            int u = 64 + (int) (r * 191.0F);
            float x = MathHelper.cos(invertedTime + 2.3561945F) * 0.282F;
            float y = MathHelper.sin(invertedTime + 2.3561945F) * 0.282F;
            float z = MathHelper.cos(invertedTime + 0.7853982F) * 0.282F;
            float aa = MathHelper.sin(invertedTime + 0.7853982F) * 0.282F;
            float ab = MathHelper.cos(invertedTime + 3.926991F) * 0.282F;
            float ac = MathHelper.sin(invertedTime + 3.926991F) * 0.282F;
            float ad = MathHelper.cos(invertedTime + 5.4977875F) * 0.282F;
            float ae = MathHelper.sin(invertedTime + 5.4977875F) * 0.282F;
            float af = MathHelper.cos(invertedTime + 3.1415927F) * 0.2F;
            float ag = MathHelper.sin(invertedTime + 3.1415927F) * 0.2F;
            float ah = MathHelper.cos(invertedTime + 0.0F) * 0.2F;
            float ai = MathHelper.sin(invertedTime + 0.0F) * 0.2F;
            float aj = MathHelper.cos(invertedTime + 1.5707964F) * 0.2F;
            float ak = MathHelper.sin(invertedTime + 1.5707964F) * 0.2F;
            float al = MathHelper.cos(invertedTime + 4.712389F) * 0.2F;
            float am = MathHelper.sin(invertedTime + 4.712389F) * 0.2F;
            float aq = -1.0F + time;
            float ar = diffL * 2.5F + aq;
            VertexConsumer vertexConsumer = vertexConsumerProvider.getBuffer(BEAM_LAYER);
            MatrixStack.Entry entry = matrixStack.peek();
            Matrix4f matrix4f = entry.getPositionMatrix();
            Matrix3f matrix3f = entry.getNormalMatrix();
            vertex(vertexConsumer, matrix4f, matrix3f, af, diffL, ag, s, t, u, 0.4999F, ar);
            vertex(vertexConsumer, matrix4f, matrix3f, af, 0.0F, ag, s, t, u, 0.4999F, aq);
            vertex(vertexConsumer, matrix4f, matrix3f, ah, 0.0F, ai, s, t, u, 0.0F, aq);
            vertex(vertexConsumer, matrix4f, matrix3f, ah, diffL, ai, s, t, u, 0.0F, ar);
            vertex(vertexConsumer, matrix4f, matrix3f, aj, diffL, ak, s, t, u, 0.4999F, ar);
            vertex(vertexConsumer, matrix4f, matrix3f, aj, 0.0F, ak, s, t, u, 0.4999F, aq);
            vertex(vertexConsumer, matrix4f, matrix3f, al, 0.0F, am, s, t, u, 0.0F, aq);
            vertex(vertexConsumer, matrix4f, matrix3f, al, diffL, am, s, t, u, 0.0F, ar);
            float as = 0.0F;
            if (entity.age % 2 == 0) {
                as = 0.5F;
            }

            vertex(vertexConsumer, matrix4f, matrix3f, x, diffL, y, s, t, u, 0.5F, as + 0.5F);
            vertex(vertexConsumer, matrix4f, matrix3f, z, diffL, aa, s, t, u, 1.0F, as + 0.5F);
            vertex(vertexConsumer, matrix4f, matrix3f, ad, diffL, ae, s, t, u, 1.0F, as);
            vertex(vertexConsumer, matrix4f, matrix3f, ab, diffL, ac, s, t, u, 0.5F, as);
            matrixStack.pop();
        }

    }
}
