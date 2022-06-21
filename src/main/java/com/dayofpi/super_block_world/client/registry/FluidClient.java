package com.dayofpi.super_block_world.client.registry;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.registry.ModFluids;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class FluidClient {
    private static final Identifier POISON_TEXTURE = new Identifier(Main.MOD_ID, "block/poison_still");
    private static final Identifier FLOWING_POISON_TEXTURE = new Identifier(Main.MOD_ID, "block/poison_flow");

    public static void renderFluids() {
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.POISON, ModFluids.FLOWING_POISON, new SimpleFluidRenderHandler(POISON_TEXTURE, FLOWING_POISON_TEXTURE));
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(POISON_TEXTURE));
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register((atlasTexture, registry) -> registry.register(FLOWING_POISON_TEXTURE));

    }
}
