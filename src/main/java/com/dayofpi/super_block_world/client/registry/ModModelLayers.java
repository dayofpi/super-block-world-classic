package com.dayofpi.super_block_world.client.registry;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.models.*;
import com.dayofpi.super_block_world.client.renderers.ChinchoTorchRenderer;
import com.dayofpi.super_block_world.client.renderers.FlagRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.MinecartEntityModel;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    private static EntityModelLayer createMain(String id) {
        return new EntityModelLayer(new Identifier(Main.MOD_ID, id), "main");
    }

    private static EntityModelLayer create(String id, String name) {
        return new EntityModelLayer(new Identifier(Main.MOD_ID, id), name);
    }

    public static final EntityModelLayer BOO = createMain("boo");
    public static final EntityModelLayer CHINCHO_TORCH = createMain("chincho_torch");
    public static final EntityModelLayer DINO_RHINO = createMain("dino_rhino");
    public static final EntityModelLayer FAKE_BLOCK = createMain("fake_block");
    public static final EntityModelLayer FLAG = createMain("flag");
    public static final EntityModelLayer FUZZY = createMain("fuzzy");
    public static final EntityModelLayer GOOMBA = createMain("goomba");
    public static final EntityModelLayer GO_KART = createMain("go_kart");
    public static final EntityModelLayer KING_BOB_OMB = createMain("king_bob_omb");
    public static final EntityModelLayer KING_BOO = createMain("king_boo");
    public static final EntityModelLayer KOOPA_TROOPA = createMain("koopa_troopa");
    public static final EntityModelLayer KOOPA_TROOPA_SADDLE = create("koopa_troopa", "saddle");
    public static final EntityModelLayer LAUNCH_STAR = createMain("launch_star");
    public static final EntityModelLayer LIL_OINK = createMain("lil_oink");
    public static final EntityModelLayer MOO_MOO = createMain("moo_moo");
    public static final EntityModelLayer MUD_TROOPER = createMain("mud_trooper");
    public static final EntityModelLayer ROTTEN_MUSHROOM = createMain("rotten_mushroom");
    public static final EntityModelLayer SHY_GUY = createMain("shy_guy");
    public static final EntityModelLayer SPINDRIFT = createMain("spindrift");
    public static final EntityModelLayer SUPER_PICKAX = createMain("super_pickax");
    public static final EntityModelLayer TRAMPOLINE_MINECART = createMain("trampoline_minecart");

    public static void register() {
        EntityModelLayerRegistry.registerModelLayer(BOO, BooModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(CHINCHO_TORCH, ChinchoTorchRenderer::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(DINO_RHINO, DinoRhinoModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FAKE_BLOCK, FakeBlockModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FLAG, FlagRenderer::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(FUZZY, FuzzyModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(GO_KART, GoKartModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(KING_BOB_OMB, KingBobOmbModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(KING_BOO, KingBooModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(KOOPA_TROOPA, KoopaTroopaModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(KOOPA_TROOPA_SADDLE, KoopaTroopaModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(LAUNCH_STAR, LaunchStarModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(LIL_OINK, LilOinkModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(MUD_TROOPER, MudTrooperModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(ROTTEN_MUSHROOM, RottenMushroomModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SHY_GUY, ShyGuyModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SPINDRIFT, SpindriftModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(SUPER_PICKAX, SuperPickaxModel::getTexturedModelData);
        EntityModelLayerRegistry.registerModelLayer(TRAMPOLINE_MINECART, MinecartEntityModel::getTexturedModelData);
    }
}