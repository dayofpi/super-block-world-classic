package com.dayofpi.super_block_world.entity.entities;

import com.dayofpi.super_block_world.Main;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.Identifier;

public record BooFace(String id, Identifier texture) {
    public static final BooFace CHEEKY = BooFace.register("cheeky");
    public static final BooFace COOL = BooFace.register("cool");
    public static final BooFace MAD = BooFace.register("mad");
    public static final BooFace POYO = BooFace.register("poyo");
    public static final BooFace SCRUNKLY = BooFace.register("scrunkly");
    public static final ImmutableList<BooFace> LIST = ImmutableList.of(CHEEKY, COOL, MAD, POYO, SCRUNKLY);

    public static BooFace fromName(String name) {
        for (BooFace type : LIST) {
            if (!type.id.equals(name)) continue;
            return type;
        }
        return CHEEKY;
    }

    private static BooFace register(String id) {
        return new BooFace(id, new Identifier(Main.MOD_ID, "textures/entity/boo/face_" + id + ".png"));
    }
}
