package com.dayofpi.sbw_mixin.enum_additions;

import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.SoundList;
import com.dayofpi.sbw_main.misc.EnumInstruments;
import net.minecraft.block.BlockState;
import net.minecraft.block.enums.Instrument;
import net.minecraft.sound.SoundEvent;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(Instrument.class)
public class MixinInstrument {
    @Shadow
    @Mutable
    private static @Final
    Instrument[] field_12652;

    @Invoker("<init>")
    private static Instrument newInstrument(String internalName, int internalId, String name, SoundEvent sound) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/block/enums/Instrument;field_12652:[Lnet/minecraft/block/enums/Instrument;",
            shift = At.Shift.AFTER))
    private static void addCustomInstrument(CallbackInfo info) {
        var variants = new ArrayList<>(Arrays.asList(field_12652));
        var last = variants.get(variants.size() - 1);
        var bling = newInstrument("BLING", last.ordinal() + 1, "bling", SoundList.blockCoin);
        EnumInstruments.BLING = bling;
        variants.add(bling);

        field_12652 = variants.toArray(new Instrument[0]);
    }

    @Inject(method = "fromBlockState(Lnet/minecraft/block/BlockState;)Lnet/minecraft/block/enums/Instrument;", at =@At("HEAD"), cancellable = true)
    private static void fromBlockState(BlockState state, CallbackInfoReturnable<Instrument> info) {
        if (state.isOf(ModBlocks.COIN_BLOCK) || state.isOf(ModBlocks.QUESTION_BLOCK)) {
            info.setReturnValue(EnumInstruments.BLING);
            info.cancel();
        }
    }
}
