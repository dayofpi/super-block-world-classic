package com.dayofpi.super_block_world.main.client;

import com.dayofpi.super_block_world.main.Main;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.fabric.api.client.rendering.v1.DimensionRenderingRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.gl.VertexBuffer;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class MushroomKingdomSky implements DimensionRenderingRegistry.SkyRenderer {
    private static final Identifier SUN_TEXTURE = new Identifier(Main.MOD_ID, "textures/sun.png");
    private static final Identifier MOON_TEXTURE = new Identifier(Main.MOD_ID, "textures/moon.png");
    @Nullable
    private VertexBuffer starsBuffer;

    public void render(WorldRenderContext context) {
        ClientWorld world = context.world();
        MatrixStack matrix = context.matrixStack();
        float tickDelta = context.tickDelta();

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthMask(false);
        RenderSystem.disableDepthTest();
        matrix.push();
        assert world != null;
        drawSun(tickDelta, matrix, world);
        drawMoon(tickDelta, matrix, world);
        matrix.pop();
        RenderSystem.depthMask(true);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
        this.renderStars();
    }

    public void drawSun(float partialTicks, MatrixStack matrix, ClientWorld world){
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        Tessellator tessellator = Tessellator.getInstance();

        float size = 20.0F;
        VertexFormat.DrawMode drawMode = VertexFormat.DrawMode.QUADS;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, SUN_TEXTURE);
        matrix.translate(0, 0, 40);
        matrix.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-90.0F));
        matrix.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(world.getSkyAngle(partialTicks) * 360));
        int k1 = world.getMoonPhase();
        int i2 = k1 % 4;
        int k2 = k1 / 4 % 2;
        float f22 = (float)(i2) / 4.0F;
        float f23 = (float)(k2) / 2.0F;
        float f24 = (float)(i2 + 1) / 4.0F;
        float f14 = (float)(k2 + 1) / 2.0F;
        Matrix4f matrix4f = matrix.peek().getPositionMatrix();
        bufferbuilder.begin(drawMode, VertexFormats.POSITION_TEXTURE);
        bufferbuilder.vertex(matrix4f, (-size), 100.0F, (-size)).texture(f24, f14).next();
        bufferbuilder.vertex(matrix4f, size, 100.0F, (-size)).texture(f22, f14).next();
        bufferbuilder.vertex(matrix4f, size, 100.0F, size).texture(f22, f23).next();
        bufferbuilder.vertex(matrix4f, (-size), 100.0F, size).texture(f24, f23).next();
        tessellator.draw();
    }

    public void drawMoon(float partialTicks, MatrixStack matrix, ClientWorld world){
        BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
        Tessellator tessellator = Tessellator.getInstance();

        float size = 20.0F;
        VertexFormat.DrawMode drawMode = VertexFormat.DrawMode.QUADS;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, MOON_TEXTURE);
        matrix.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90.0F));
        matrix.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion((world.getSkyAngle(partialTicks) * 360) * 0.0015F));
        Matrix4f matrix4f = matrix.peek().getPositionMatrix();
        bufferbuilder.begin(drawMode, VertexFormats.POSITION_TEXTURE);
        bufferbuilder.vertex(matrix4f, (-size), -100.0F, size).texture(0.0F, 0.0F).next();
        bufferbuilder.vertex(matrix4f, size, -100.0F, size).texture(1.0F, 0.0F).next();
        bufferbuilder.vertex(matrix4f, size, -100.0F, (-size)).texture(1.0F, 1.F).next();
        bufferbuilder.vertex(matrix4f, (-size), -100.0F, (-size)).texture(0.0F, 1.0F).next();
        tessellator.draw();
    }

    private void renderStars() {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionShader);
        if (this.starsBuffer != null) {
            this.starsBuffer.close();
        }
        this.starsBuffer = new VertexBuffer();
        this.renderStars(bufferBuilder);
        bufferBuilder.end();
        this.starsBuffer.upload(bufferBuilder);
    }

    private void renderStars(BufferBuilder buffer) {
        Random random = new Random(10842L);
        buffer.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION);
        for (int i = 0; i < 1500; ++i) {
            double d = random.nextFloat() * 2.0f - 1.0f;
            double e = random.nextFloat() * 2.0f - 1.0f;
            double f = random.nextFloat() * 2.0f - 1.0f;
            double g = 0.15f + random.nextFloat() * 0.1f;
            double h = d * d + e * e + f * f;
            if (!(h < 1.0) || !(h > 0.01)) continue;
            h = 1.0 / Math.sqrt(h);
            double j = (d *= h) * 100.0;
            double k = (e *= h) * 100.0;
            double l = (f *= h) * 100.0;
            double m = Math.atan2(d, f);
            double n = Math.sin(m);
            double o = Math.cos(m);
            double p = Math.atan2(Math.sqrt(d * d + f * f), e);
            double q = Math.sin(p);
            double r = Math.cos(p);
            double s = random.nextDouble() * Math.PI * 2.0;
            double t = Math.sin(s);
            double u = Math.cos(s);
            for (int v = 0; v < 4; ++v) {
                double ab;
                double w = 0.0;
                double x = (double)((v & 2) - 1) * g;
                double y = (double)((v + 1 & 2) - 1) * g;
                double z = 0.0;
                double aa = x * u - y * t;
                double ac = y * u + x * t;
                double ad = aa * q + 0.0 * r;
                double ae = 0.0 * q - aa * r;
                double af = ae * n - ac * o;
                double ah = ac * n + ae * o;
                buffer.vertex(j + af, k + ad, l + ah).next();
            }
        }
    }
}
