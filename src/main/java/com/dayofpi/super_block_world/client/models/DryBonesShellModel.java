package com.dayofpi.super_block_world.client.models;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.DryBonesShellEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

@Environment(EnvType.CLIENT)
public class DryBonesShellModel<T extends DryBonesShellEntity> extends AnimatedGeoModel<T> {
    private static final Identifier MODEL = new Identifier(Main.MOD_ID, "geo/dry_bones_shell.geo.json");
    @Override
    public Identifier getModelLocation(T object) {
        return MODEL;
    }

    @Override
    public Identifier getTextureLocation(T object) {
        return new Identifier(Main.MOD_ID, "textures/entity/shell/bones.png");
    }

    @Override
    public Identifier getAnimationFileLocation(T animatable) {
        return new Identifier(Main.MOD_ID, "animations/dry_bones_shell.animation.json");
    }
}
