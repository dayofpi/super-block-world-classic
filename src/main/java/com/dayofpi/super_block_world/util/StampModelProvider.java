package com.dayofpi.super_block_world.util;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.common.entities.misc.StampEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.model.ExtraModelProvider;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Environment(EnvType.CLIENT)
public class StampModelProvider implements ExtraModelProvider {
    @Override
    public void provideExtraModels(ResourceManager manager, Consumer<Identifier> out) {
        List<StampEntity.Stamp> list = Arrays.stream(StampEntity.Stamp.values()).toList();
        for (StampEntity.Stamp stamp : list) {
            Identifier identifier = new Identifier(Main.MOD_ID, "block/stamp_" + stamp.asString());
            out.accept(identifier);
        }
    }
}
