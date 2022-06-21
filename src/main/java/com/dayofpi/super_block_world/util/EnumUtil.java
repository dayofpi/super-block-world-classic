package com.dayofpi.super_block_world.util;

import com.dayofpi.super_block_world.mixin.SignTypeAccessor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.map.MapIcon;
import net.minecraft.util.SignType;

public class EnumUtil {
    public static BoatEntity.Type AMANITA_BOAT;
    public static BoatEntity.Type DARK_AMANITA_BOAT;
    public static BoatEntity.Type BELL_BOAT;
    public static AbstractMinecartEntity.Type TRAMPOLINE;
    public static MapIcon.Type BOSS_ARENA;
    public static MapIcon.Type YOSHI;
    public static Instrument BLING;
    public static Instrument BLOCK;
    public static Instrument CHOIR;
    public static Instrument PAN_FLUTE;
    public static Instrument DINODRUM;

    public static final SignType AMANITA_SIGN = SignTypeAccessor.registerNew(SignTypeAccessor.newSignType("amanita"));
    public static final SignType DARK_AMANITA_SIGN = SignTypeAccessor.registerNew(SignTypeAccessor.newSignType("dark_amanita"));
    public static final SignType BELL_SIGN = SignTypeAccessor.registerNew(SignTypeAccessor.newSignType("bell"));

}
