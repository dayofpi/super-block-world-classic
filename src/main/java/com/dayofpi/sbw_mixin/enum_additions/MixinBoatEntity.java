package com.dayofpi.sbw_mixin.enum_additions;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.misc.ModBoatType;
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
public class MixinBoatEntity {
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
        var amanita = newType("AMANITA", last.ordinal() + 1,
                ModBlocks.AMANITA_PLANKS, "amanita");
        var dark_amanita = newType("DARK_AMANITA", last.ordinal() + 2,
                ModBlocks.DARK_AMANITA_PLANKS, "dark_amanita");
        ModBoatType.AMANITA = amanita;
        ModBoatType.DARK_AMANITA = dark_amanita;
        variants.add(amanita);
        variants.add(dark_amanita);

        field_7724 = variants.toArray(new BoatEntity.Type[0]);
    }
}
