package com.dayofpi.sbw_main.world.registry;

import com.dayofpi.sbw_main.Main;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;

public class ParticleClient {
    public static void particleRendering() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> registry.register(new Identifier(Main.MOD_ID, "particle/poison_0"))));

        ParticleFactoryRegistry.getInstance().register(ModParticles.POISON_BUBBLE, SoulParticle.Factory::new);

    }
}
