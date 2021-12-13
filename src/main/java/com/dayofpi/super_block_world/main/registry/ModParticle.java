package com.dayofpi.super_block_world.main.registry;

import com.dayofpi.super_block_world.main.Main;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticle {
    public static final DefaultParticleType POISON_BUBBLE = FabricParticleTypes.simple();

    public static void registerParticles() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "poison_bubble"), POISON_BUBBLE);
    }
}
