package com.dayofpi.super_block_world.client.particles;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.random.Random;

@Environment(EnvType.CLIENT)
public class LeafParticle extends AscendingParticle {
    private final SpriteProvider spriteProvider;

    LeafParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, float scaleMultiplier, SpriteProvider spriteProvider) {
        super(world, x, y, z, 0.1f, -0.1f, 0.1f, velocityX, velocityY, velocityZ, scaleMultiplier, spriteProvider, 0.0f, 20, 0.0125f, false);
        this.spriteProvider = spriteProvider;
        this.scale(1.5f);
        this.red = 1.0f;
        this.blue = 1.0f;
        this.green = 1.0f;
        this.setSpriteForAge(spriteProvider);
        this.setSpriteForAge(spriteProvider);
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        super.tick();
        this.setSpriteForAge(this.spriteProvider);
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            Random random = clientWorld.random;
            double j = (double)random.nextFloat() * -1.9 * (double)random.nextFloat() * 0.1;
            double k = (double)random.nextFloat() * -0.5 * (double)random.nextFloat() * 0.1 * 5.0;
            double l = (double)random.nextFloat() * -1.9 * (double)random.nextFloat() * 0.1;
            LeafParticle leafParticle = new LeafParticle(clientWorld, d, e, f, j, k, l, 1.0f, this.spriteProvider);
            leafParticle.setAlpha(1.0f);
            return leafParticle;
        }
    }
}
