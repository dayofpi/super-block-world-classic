package com.dayofpi.super_block_world.mixin;

import com.dayofpi.super_block_world.util.EnumAddons;
import net.minecraft.item.map.MapIcon;
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

@SuppressWarnings("unused")
@Mixin(MapIcon.Type.class)
public class MapIconMixin {
    @Shadow
    @Mutable
    private static @Final
    MapIcon.Type[] field_109;

    @Invoker("<init>")
    private static MapIcon.Type newType(String internalName, int internalId, boolean alwaysRender, int tintColor, boolean bl) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At(value = "FIELD",
            opcode = Opcodes.PUTSTATIC,
            target = "Lnet/minecraft/item/map/MapIcon$Type;field_109:[Lnet/minecraft/item/map/MapIcon$Type;",
            shift = At.Shift.AFTER))
    private static void addIcon(CallbackInfo info) {
        var variants = new ArrayList<>(Arrays.asList(field_109));
        var last = variants.get(variants.size() - 1);

        var boss_arena = newType("BOSS_ARENA", last.ordinal() + 1, true, 12334173, false);
        var yoshi = newType("YOSHI", last.ordinal() + 2, true, 6880830, false);

        EnumAddons.BOSS_ARENA = boss_arena;
        EnumAddons.YOSHI = yoshi;

        variants.add(boss_arena);
        variants.add(yoshi);

        field_109 = variants.toArray(new MapIcon.Type[0]);
    }
}
