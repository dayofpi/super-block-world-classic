package com.dayofpi.super_block_world.mixin.main.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.passive.SnowGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(EntityType.class)
public abstract class SpawnGroupMixin {
    @Redirect(method = "<clinit>", at = @At(value = "INVOKE", ordinal = 0,
            target = "Lnet/minecraft/entity/EntityType$Builder;create(Lnet/minecraft/entity/EntityType$EntityFactory;Lnet/minecraft/entity/SpawnGroup;)Lnet/minecraft/entity/EntityType$Builder;"),
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=snow_golem")))
    private static EntityType.Builder<SnowGolemEntity> scratch_adjustArmourStandBounds(
            EntityType.EntityFactory<SnowGolemEntity> factory, SpawnGroup spawnGroup)
    {
        return EntityType.Builder.create(SnowGolemEntity::new, SpawnGroup.CREATURE);
    }
}
