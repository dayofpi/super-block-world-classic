package com.dayofpi.super_block_world.world;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.util.PoisonFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModFluids {
    public static final FlowableFluid POISON = new PoisonFluid.Still();
    public static final FlowableFluid FLOWING_POISON = new PoisonFluid.Flowing();

    public static void register() {
        Registry.register(Registry.FLUID, new Identifier(Main.MOD_ID, "poison"), POISON);
        Registry.register(Registry.FLUID, new Identifier(Main.MOD_ID, "flowing_poison"), FLOWING_POISON);
    }
}
