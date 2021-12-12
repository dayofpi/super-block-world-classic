package com.dayofpi.sbw_main.client;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.util.ModParticle;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.particle.SoulParticle;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
@Environment(EnvType.CLIENT)
public class ParticleClient {
    public static void particleRendering() {
        ClientSpriteRegistryCallback.event(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE).register(((atlasTexture, registry) -> registry.register(new Identifier(Main.MOD_ID, "particle/poison_0"))));

        ParticleFactoryRegistry.getInstance().register(ModParticle.POISON_BUBBLE, SoulParticle.Factory::new);

    }
}
