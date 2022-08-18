package com.dayofpi.super_block_world.client.particles;

import com.dayofpi.super_block_world.client.models.KingBooModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.client.renderers.KingBooRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.Model;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class KingBooCurseParticle extends Particle {
    private final Model model;
    private final RenderLayer layer = RenderLayer.getEntityTranslucent(KingBooRenderer.TEXTURE);

    KingBooCurseParticle(ClientWorld clientWorld, double x, double y, double z) {
        super(clientWorld, x, y, z);
        this.model = new KingBooModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(ModModelLayers.KING_BOO));
        this.gravityStrength = 0.0f;
        this.maxAge = 30;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.CUSTOM;
    }

    @Override
    public void buildGeometry(VertexConsumer vertexConsumer, Camera camera, float tickDelta) {
        float f = ((float)this.age + tickDelta) / (float)this.maxAge;
        float g = 0.05f + 0.5f * MathHelper.sin(f * (float)Math.PI);
        MatrixStack matrixStack = new MatrixStack();
        matrixStack.multiply(camera.getRotation());
        matrixStack.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(150.0f * f - 60.0f));
        matrixStack.scale(-1.0f, -1.0f, 1.0f);
        matrixStack.translate(0.0, -1.101f, 1.5);
        VertexConsumerProvider.Immediate immediate = MinecraftClient.getInstance().getBufferBuilders().getEntityVertexConsumers();
        VertexConsumer vertexConsumer2 = immediate.getBuffer(this.layer);
        this.model.render(matrixStack, vertexConsumer2, 0xF000F0, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, g);
        immediate.draw();
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
            return new KingBooCurseParticle(clientWorld, x, y, z);
        }
    }
}
