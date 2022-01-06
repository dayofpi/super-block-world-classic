package com.dayofpi.super_block_world.client;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.registry.other.ParticleInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class ParticleRendering {
    public static void renderParticles() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> registry.register(new Identifier(Main.MOD_ID, "particle/poison_0"))));
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> registry.register(new Identifier(Main.MOD_ID, "particle/star_bit_0"))));

        ParticleFactoryRegistry.getInstance().register(ParticleInit.POISON_BUBBLE, SoulParticle.Factory::new);
        ParticleFactoryRegistry.getInstance().register(ParticleInit.STAR_BIT, SoulParticle.Factory::new);

    }
}
