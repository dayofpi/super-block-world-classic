package com.dayofpi.super_block_world.registry.other;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.world.fluid.PoisonFluid;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class FluidInit {
    public static final FlowableFluid STILL_POISON = new PoisonFluid.Still();
    public static final FlowableFluid FLOWING_POISON = new PoisonFluid.Flowing();

    public static void register() {
        Registry.register(Registry.FLUID, new Identifier(Main.MOD_ID, "poison"), STILL_POISON);
        Registry.register(Registry.FLUID, new Identifier(Main.MOD_ID, "flowing_poison"), FLOWING_POISON);
    }
}
