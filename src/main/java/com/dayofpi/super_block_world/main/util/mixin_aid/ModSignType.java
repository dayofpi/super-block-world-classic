package com.dayofpi.super_block_world.main.util.mixin_aid;

import com.dayofpi.super_block_world.mixin.main.enums_and_sets.SignTypeAccessor;
import net.minecraft.util.SignType;

public class ModSignType {
    public static final SignType AMANITA = SignTypeAccessor.registerNew(SignTypeAccessor.newSignType("amanita"));
    public static final SignType DARK_AMANITA = SignTypeAccessor.registerNew(SignTypeAccessor.newSignType("dark_amanita"));

}
