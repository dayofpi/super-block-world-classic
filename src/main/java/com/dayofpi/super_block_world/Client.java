package com.dayofpi.super_block_world;

import com.dayofpi.super_block_world.client.registry.*;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        GlobalReceivers.register();
        ModModelLayers.register();
        EntityClient.renderEntities();
        ModelClient.renderBlocksAndItems();
        FluidClient.renderFluids();
        ParticleClient.renderParticles();
    }
}
