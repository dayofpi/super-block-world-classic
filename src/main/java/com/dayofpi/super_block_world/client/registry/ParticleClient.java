package com.dayofpi.super_block_world.client.registry;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.particles.GooParticle;
import com.dayofpi.super_block_world.client.particles.KingBooCurseParticle;
import com.dayofpi.super_block_world.client.particles.LeafParticle;
import com.dayofpi.super_block_world.registry.ModParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.FlameParticle;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ParticleClient {
    public static void renderParticles() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> registry.register(new Identifier(Main.MOD_ID, "particle/leaf_0"))));
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> registry.register(new Identifier(Main.MOD_ID, "particle/magic_0"))));
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> registry.register(new Identifier(Main.MOD_ID, "particle/poison_0"))));
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> registry.register(new Identifier(Main.MOD_ID, "particle/star_bit_0"))));

        ParticleFactoryRegistry.getInstance().register(ModParticles.FALLING_GOO, GooParticle.FallingGooFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.KING_BOO_CURSE, new KingBooCurseParticle.Factory());
        ParticleFactoryRegistry.getInstance().register(ModParticles.LANDING_GOO, GooParticle.LandingGooFactory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.LEAF, LeafParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.MAGIC, FlameParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.POISON_BUBBLE, SoulParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ModParticles.STAR_BIT, SoulParticle.Factory::new);
    }
}
