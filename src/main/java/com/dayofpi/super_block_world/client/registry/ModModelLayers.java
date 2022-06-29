package com.dayofpi.super_block_world.client.registry;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.*;
import com.dayofpi.super_block_world.client.renderers.FlagRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    private static EntityModelLayer createMain(String name) {
        return new EntityModelLayer(new Identifier(Main.MOD_ID, name), "main");
    }

    public static final EntityModelLayer FLAG = createMain("flag");
    public static final EntityModelLayer FUZZY = createMain("fuzzy");
    public static final EntityModelLayer ROTTEN_MUSHROOM = createMain("rotten_mushroom");
    public static final EntityModelLayer GO_KART = createMain("go_kart");
    public static final EntityModelLayer LAUNCH_STAR = createMain("launch_star");
    public static final EntityModelLayer LIL_OINK = createMain("lil_oink");
    public static final EntityModelLayer SPINDRIFT = createMain("spindrift");
    public static final EntityModelLayer SUPER_PICKAX = createMain("super_pickax");
    public static final EntityModelLayer TRAMPOLINE_MINECART = createMain("trampoline_minecart");

    public static void register() {
        EntityModelLayerRegistry.registerModelLayer(FLAG, FlagRenderer::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FUZZY, FuzzyModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ROTTEN_MUSHROOM, RottenMushroomModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GO_KART, GoKartModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(LAUNCH_STAR, LaunchStarModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(LIL_OINK, LilOinkModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SPINDRIFT, SpindriftModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SUPER_PICKAX, SuperPickaxModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(TRAMPOLINE_MINECART, MinecartEntityModel::getTexturedModelData);
    }
}