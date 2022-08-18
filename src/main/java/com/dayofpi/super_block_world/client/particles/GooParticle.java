/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.super_block_world.client.particles;

import com.dayofpi.super_block_world.registry.ModParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

@Environment(value = EnvType.CLIENT)
public class GooParticle extends SpriteBillboardParticle {

    GooParticle(ClientWorld world, double x, double y, double z) {
        super(world, x, y, z);
        this.setBoundingBoxSpacing(0.01f, 0.01f);
        this.gravityStrength = 0.06f;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;
        this.updateAge();
        if (this.dead) {
            return;
        }
        this.velocityY -= this.gravityStrength;
        this.move(this.velocityX, this.velocityY, this.velocityZ);
        this.updateVelocity();
        if (this.dead) {
            return;
        }
        this.velocityX *= 0.98f;
        this.velocityY *= 0.98f;
        this.velocityZ *= 0.98f;
    }

    protected void updateAge() {
        if (this.maxAge-- <= 0) {
            this.markDead();
        }
    }

    protected void updateVelocity() {
    }

    @Environment(value = EnvType.CLIENT)
    public record LandingGooFactory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            Landing blockLeakParticle = new Landing(clientWorld, d, e, f);
            blockLeakParticle.maxAge = (int) (28.0 / (Math.random() * 0.8 + 0.2));
            blockLeakParticle.setColor(0.62352941176F, 0.65098039215F, 0.40784313725F);
            blockLeakParticle.setSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    @Environment(value = EnvType.CLIENT)
    public record FallingGooFactory(SpriteProvider spriteProvider) implements ParticleFactory<DefaultParticleType> {

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            ContinuousFalling blockLeakParticle = new ContinuousFalling(clientWorld, d, e, f);
            blockLeakParticle.gravityStrength = 0.01f;
            blockLeakParticle.setColor(0.62352941176F, 0.65098039215F, 0.40784313725F);
            blockLeakParticle.setSprite(this.spriteProvider);
            return blockLeakParticle;
        }
    }

    @Environment(value = EnvType.CLIENT)
    static class Landing
            extends GooParticle {
        Landing(ClientWorld clientWorld, double d, double e, double f) {
            super(clientWorld, d, e, f);
            this.maxAge = (int) (16.0 / (Math.random() * 0.8 + 0.2));
        }
    }

    @Environment(value = EnvType.CLIENT)
    static class Falling extends GooParticle {
        Falling(ClientWorld clientWorld, double d, double e, double f) {
            this(clientWorld, d, e, f, (int) (64.0 / (Math.random() * 0.8 + 0.2)));
        }

        Falling(ClientWorld world, double x, double y, double z, int maxAge) {
            super(world, x, y, z);
            this.maxAge = maxAge;
        }

        @Override
        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
            }
        }
    }

    @Environment(value = EnvType.CLIENT)
    static class ContinuousFalling extends Falling {
        protected final ParticleEffect nextParticle;

        ContinuousFalling(ClientWorld world, double x, double y, double z) {
            super(world, x, y, z);
            this.nextParticle = ModParticles.LANDING_GOO;
        }

        @Override
        protected void updateVelocity() {
            if (this.onGround) {
                this.markDead();
                this.world.addParticle(this.nextParticle, this.x, this.y, this.z, 0.0, 0.0, 0.0);
                this.world.playSound(this.x, this.y, this.z, SoundEvents.BLOCK_MUD_FALL, SoundCategory.BLOCKS, 0.05F, 0.15F, false);
            }
        }
    }
}

