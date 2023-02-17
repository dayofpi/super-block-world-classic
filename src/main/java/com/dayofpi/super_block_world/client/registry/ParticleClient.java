package com.dayofpi.super_block_world.client.registry;

import com.dayofpi.super_block_world.ModParticles;
import com.dayofpi.super_block_world.client.particles.GooParticle;
import com.dayofpi.super_block_world.client.particles.LeafParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.SoulParticle;

@Environment(EnvType.CLIENT)
public class ParticleClient {
    public static void renderParticles() {
        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLING_GOO, GooParticle.FallingGooFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.KING_BOO_CURSE, SoulParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.LANDING_GOO, GooParticle.LandingGooFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.LEAF, LeafParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.MAGIC, FlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.POISON_BUBBLE, SoulParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.STAR_BIT, SoulParticle.Factory::new);
    }
}
