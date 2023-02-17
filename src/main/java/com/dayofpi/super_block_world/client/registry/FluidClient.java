package com.dayofpi.super_block_world.client.registry;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.world.ModFluids;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.client.render.fluid.v1.SimpleFluidRenderHandler;
import net.minecraft.util.Identifier;

@SuppressWarnings("deprecation")
public class FluidClient {
    private static final Identifier POISON_TEXTURE = new Identifier(Main.MOD_ID, "block/poison_still");
    private static final Identifier FLOWING_POISON_TEXTURE = new Identifier(Main.MOD_ID, "block/poison_flow");

    public static void renderFluids() {
        FluidRenderHandlerRegistry.INSTANCE.register(ModFluids.POISON, ModFluids.FLOWING_POISON, new SimpleFluidRenderHandler(POISON_TEXTURE, FLOWING_POISON_TEXTURE));
    }
}
