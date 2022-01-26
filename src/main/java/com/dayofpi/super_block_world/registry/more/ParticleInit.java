package com.dayofpi.super_block_world.registry.more;

import com.dayofpi.super_block_world.Main;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ParticleInit {
    public static final DefaultParticleType FEATHER = FabricParticleTypes.simple();
    public static final DefaultParticleType POISON_BUBBLE = FabricParticleTypes.simple();
    public static final DefaultParticleType STAR_BIT = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "feather"), FEATHER);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "poison_bubble"), POISON_BUBBLE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "star_bit"), STAR_BIT);
    }
}
