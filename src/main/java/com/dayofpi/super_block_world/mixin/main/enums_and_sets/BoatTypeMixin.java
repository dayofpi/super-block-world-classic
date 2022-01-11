package com.dayofpi.super_block_world.mixin.main.enums_and_sets;

import com.dayofpi.super_block_world.registry.main.BlockInit;
import com.dayofpi.super_block_world.common.util.mixin_aid.ModBoatType;
import net.minecraft.block.Block;
import net.minecraft.entity.vehicle.BoatEntity;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(BoatEntity.Type.class)
public class BoatTypeMixin {
    @Invoker("<init>")
    private static BoatEntity.Type newType(String internalName, int internalId, Block baseBlock, String name) {
        throw new AssertionError();
    }

    @Shadow
    @Mutable
    private static @Final
    BoatEntity.Type[] field_7724;

    @Inject(method = "<clinit>", at = @At(value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/entity/vehicle/BoatEntity$Type;field_7724:[Lnet/minecraft/entity/vehicle/BoatEntity$Type;",
            shift = At.Shift.AFTER))
    private static void addBoat(CallbackInfo info) {
        var variants = new ArrayList<>(Arrays.asList(field_7724));
        var last = variants.get(variants.size() - 1);

        var amanita = newType("AMANITA", last.ordinal() + 1, BlockInit.AMANITA_PLANKS, "amanita");
        var dark_amanita = newType("DARK_AMANITA", last.ordinal() + 2, BlockInit.DARK_AMANITA_PLANKS, "dark_amanita");
        var bell = newType("BELL", last.ordinal() + 3, BlockInit.BELL_PLANKS, "bell");

        ModBoatType.AMANITA = amanita;
        ModBoatType.DARK_AMANITA = dark_amanita;
        ModBoatType.BELL = bell;

        variants.add(amanita);
        variants.add(dark_amanita);
        variants.add(bell);

        field_7724 = variants.toArray(new BoatEntity.Type[0]);
    }
}
