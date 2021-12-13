package com.dayofpi.super_block_world.main.registry;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.util.fluid.PoisonFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModFluid {
    public static final FlowableFluid STILL_POISON = new PoisonFluid.Still();
    public static final FlowableFluid FLOWING_POISON = new PoisonFluid.Flowing();

    public static void registerFluids() {
        Registry.register(Registry.FLUID, new Identifier(Main.MOD_ID, "poison"), STILL_POISON);
        Registry.register(Registry.FLUID, new Identifier(Main.MOD_ID, "flowing_poison"), FLOWING_POISON);
    }
}
