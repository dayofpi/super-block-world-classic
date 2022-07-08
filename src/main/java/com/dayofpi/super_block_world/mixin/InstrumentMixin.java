package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModTags;
import com.dayofpi.super_block_world.util.EnumUtil;
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

@SuppressWarnings("unused")
@Mixin(Instrument.class)
public class InstrumentMixin {
    @Shadow
    @Mutable
    private static @Final
    Instrument[] field_12652;

    @Invoker("<init>")
    private static Instrument newInstrument(String internalName, int internalId, String name, SoundEvent sound) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD", opcode = Opcodes.PUTSTATIC, target = "Lnet/minecraft/block/enums/Instrument;field_12652:[Lnet/minecraft/block/enums/Instrument;", shift = At.Shift.AFTER))
    private static void addCustomInstrument(CallbackInfo info) {
        var variants = new ArrayList<>(Arrays.asList(field_12652));
        var last = variants.get(variants.size() - 1);

        var bling = newInstrument("BLING", last.ordinal() + 1, "bling", Sounds.NOTE_BLOCK_BLING);
        var block = newInstrument("BLOCK", last.ordinal() + 2, "block", Sounds.NOTE_BLOCK_BLOCK);
        var choir = newInstrument("CHOIR", last.ordinal() + 3, "choir", Sounds.NOTE_BLOCK_CHOIR);
        var pan_flute = newInstrument("PAN_FLUTE", last.ordinal() + 4, "pan_flute", Sounds.NOTE_BLOCK_PAN_FLUTE);
        var dinodrum = newInstrument("DINODRUM", last.ordinal() + 5, "dinodrum", Sounds.NOTE_BLOCK_DINODRUM);

        EnumUtil.BLING = bling;
        EnumUtil.BLOCK = block;
        EnumUtil.CHOIR = choir;
        EnumUtil.PAN_FLUTE = pan_flute;
        EnumUtil.DINODRUM = dinodrum;

        variants.add(bling);
        variants.add(block);
        variants.add(choir);
        variants.add(pan_flute);
        variants.add(dinodrum);

        field_12652 = variants.toArray(new Instrument[0]);
    }

    @Inject(method = "fromBlockState(Lnet/minecraft/block/BlockState;)Lnet/minecraft/block/enums/Instrument;", at = @At("HEAD"), cancellable = true)
    private static void fromBlockState(BlockState state, CallbackInfoReturnable<Instrument> info) {
        if (state.isOf(ModBlocks.QUESTION_BLOCK)) {
            info.setReturnValue(EnumUtil.BLING);
        }
        if (state.isOf(ModBlocks.EMPTY_BLOCK)) {
            info.setReturnValue(EnumUtil.BLOCK);
        }
        if (state.isOf(ModBlocks.WARP_FRAME)) {
            info.setReturnValue(EnumUtil.CHOIR);
        }
        if (state.isIn(ModTags.ROYALITE)) {
            info.setReturnValue(EnumUtil.PAN_FLUTE);
        }
        if (state.isOf(ModBlocks.YOSHI_EGG)) {
            info.setReturnValue(EnumUtil.DINODRUM);
        }
    }
}
