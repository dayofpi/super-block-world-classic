package com.dayofpi.super_block_world.util;

import com.dayofpi.super_block_world.Main;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class StampModelProvider implements ModelResourceProvider {
    public static final Identifier STAMP = new Identifier(Main.MOD_ID, "block/stamp_arrow");

    @Override
    public @Nullable UnbakedModel loadModelResource(Identifier resourceId, ModelProviderContext context) {
        if (resourceId.equals(STAMP)) {
            return context.loadModel(STAMP);
        }
        else return null;
    }
}
