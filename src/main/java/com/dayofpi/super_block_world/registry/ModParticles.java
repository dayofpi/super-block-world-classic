package com.dayofpi.super_block_world.registry;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.util.ModParticleType;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModParticles {
    public static final DefaultParticleType FALLING_GOO = new ModParticleType(false);
    public static final DefaultParticleType KING_BOO_CURSE = new ModParticleType(true);
    public static final DefaultParticleType LANDING_GOO = new ModParticleType(false);
    public static final DefaultParticleType LEAF = FabricParticleTypes.simple();
    public static final DefaultParticleType MAGIC = FabricParticleTypes.simple();
    public static final DefaultParticleType POISON_BUBBLE = FabricParticleTypes.simple();
    public static final DefaultParticleType STAR_BIT = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "falling_goo"), FALLING_GOO);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "king_boo_curse"), KING_BOO_CURSE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "landing_goo"), LANDING_GOO);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "leaf"), LEAF);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "magic"), MAGIC);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "poison_bubble"), POISON_BUBBLE);
        Registry.register(Registry.PARTICLE_TYPE, new Identifier(Main.MOD_ID, "star_bit"), STAR_BIT);
    }
}
