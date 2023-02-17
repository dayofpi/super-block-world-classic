package com.dayofpi.super_block_world;

import com.dayofpi.super_block_world.util.ModParticleType;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModParticles {
    public static final DefaultParticleType FALLING_GOO = new ModParticleType(false);
    public static final DefaultParticleType LANDING_GOO = new ModParticleType(false);
    public static final DefaultParticleType KING_BOO_CURSE = FabricParticleTypes.simple();
    public static final DefaultParticleType LEAF = FabricParticleTypes.simple();
    public static final DefaultParticleType MAGIC = FabricParticleTypes.simple();
    public static final DefaultParticleType POISON_BUBBLE = FabricParticleTypes.simple();
    public static final DefaultParticleType STAR_BIT = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "falling_goo"), FALLING_GOO);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "landing_goo"), LANDING_GOO);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "king_boo_curse"), KING_BOO_CURSE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "leaf"), LEAF);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "magic"), MAGIC);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "poison_bubble"), POISON_BUBBLE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "star_bit"), STAR_BIT);
    }
}
