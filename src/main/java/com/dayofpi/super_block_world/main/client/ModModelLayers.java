package com.dayofpi.super_block_world.main.client;


import com.dayofpi.super_block_world.main.Main;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ModModelLayers {
    public static final EntityModelLayer BUZZY_SHELL = new EntityModelLayer(new Identifier(Main.MOD_ID, "buzzy_shell"), "main");
    public static final EntityModelLayer MOO_MOO = new EntityModelLayer(new Identifier(Main.MOD_ID, "moo_moo"), "main");
    public static final EntityModelLayer GOOMBA = new EntityModelLayer(new Identifier(Main.MOD_ID, "goomba"), "main");
    public static final EntityModelLayer PARAGOOMBA = new EntityModelLayer(new Identifier(Main.MOD_ID, "paragoomba"), "main");
    public static final EntityModelLayer KOOPA = new EntityModelLayer(new Identifier(Main.MOD_ID, "koopa"), "main");
    public static final EntityModelLayer PARATROOPA = new EntityModelLayer(new Identifier(Main.MOD_ID, "paratroopa"), "main");
    public static final EntityModelLayer KOOPA_SADDLE = new EntityModelLayer(new Identifier(Main.MOD_ID, "koopa_saddle"), "main");
    public static final EntityModelLayer HAMMER_BRO = new EntityModelLayer(new Identifier(Main.MOD_ID, "hammer_bro"), "main");
    public static final EntityModelLayer FIRE_BRO = new EntityModelLayer(new Identifier(Main.MOD_ID, "fire_bro"), "main");
    public static final EntityModelLayer BOB_OMB = new EntityModelLayer(new Identifier(Main.MOD_ID, "bob_omb"), "main");
    public static final EntityModelLayer BOO = new EntityModelLayer(new Identifier(Main.MOD_ID, "boo"), "main");
    public static final EntityModelLayer BUZZY_BEETLE = new EntityModelLayer(new Identifier(Main.MOD_ID, "buzzy"), "main");
    public static final EntityModelLayer BUZZY_BEETLE_SADDLE = new EntityModelLayer(new Identifier(Main.MOD_ID, "buzzy_saddle"), "main");
    public static final EntityModelLayer NIPPER_PLANT = new EntityModelLayer(new Identifier(Main.MOD_ID, "nipper_plant"), "main");
    public static final EntityModelLayer STINGBY = new EntityModelLayer(new Identifier(Main.MOD_ID, "stingby"), "main");
    public static final EntityModelLayer ROTTEN_MUSHROOM = new EntityModelLayer(new Identifier(Main.MOD_ID, "rotten_mushroom"), "main");
    public static final EntityModelLayer THWOMP = new EntityModelLayer(new Identifier(Main.MOD_ID, "thwomp"), "main");
    public static final EntityModelLayer FAKE_BLOCK = new EntityModelLayer(new Identifier(Main.MOD_ID, "fake_block"), "main");

}