package com.dayofpi.sbw_main;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static final SoundEvent MUSIC_GRASSLAND = new SoundEvent(new Identifier(Main.MOD_ID, "music.grassland"));
    public static final SoundEvent MUSIC_CAVE = new SoundEvent(new Identifier(Main.MOD_ID, "music.cave"));
    public static final SoundEvent MUSIC_WATER = new SoundEvent(new Identifier(Main.MOD_ID, "music.water"));

    public static final SoundEvent BLOCK_POISON_AMBIENT = new SoundEvent(new Identifier(Main.MOD_ID, "block.poison.ambient"));
    public static final SoundEvent BLOCK_POISON_FILL = new SoundEvent(new Identifier(Main.MOD_ID, "block.poison.fill"));
    public static final SoundEvent BLOCK_POISON_EMPTY = new SoundEvent(new Identifier(Main.MOD_ID, "block.poison.empty"));

    public static final SoundEvent BLOCK_FROSTED_STONE_BREAK = new SoundEvent(new Identifier(Main.MOD_ID, "block.frosted_stone.break"));
    public static final SoundEvent BLOCK_FROSTED_STONE_FALL = new SoundEvent(new Identifier(Main.MOD_ID, "block.frosted_stone.fall"));
    public static final SoundEvent BLOCK_FROSTED_STONE_HIT = new SoundEvent(new Identifier(Main.MOD_ID, "block.frosted_stone.hit"));
    public static final SoundEvent BLOCK_FROSTED_STONE_PLACE = new SoundEvent(new Identifier(Main.MOD_ID, "block.frosted_stone.place"));
    public static final SoundEvent BLOCK_FROSTED_STONE_STEP = new SoundEvent(new Identifier(Main.MOD_ID, "block.frosted_stone.step"));

    public static final SoundEvent BLOCK_ITEM_BLOCK_BUMP = new SoundEvent(new Identifier(Main.MOD_ID, "block.item_block.bump"));
    public static final SoundEvent BLOCK_ITEM_BLOCK_COIN = new SoundEvent(new Identifier(Main.MOD_ID, "block.item_block.coin"));
    public static final SoundEvent BLOCK_ITEM_BLOCK_STORE = new SoundEvent(new Identifier(Main.MOD_ID, "block.item_block.store"));
    public static final SoundEvent BLOCK_ITEM_BLOCK_HIT = new SoundEvent(new Identifier(Main.MOD_ID, "block.item_block.hit"));

    public static final SoundEvent BLOCK_WARP_PIPE_TELEPORT = new SoundEvent(new Identifier(Main.MOD_ID, "block.warp_pipe.teleport"));
    public static final SoundEvent BLOCK_DONUT_BLOCK_TRIGGER = new SoundEvent(new Identifier(Main.MOD_ID, "block.donut_block.trigger"));
    public static final SoundEvent BLOCK_SPIKES_RETRACT = new SoundEvent(new Identifier(Main.MOD_ID, "block.spikes.retract"));
    public static final SoundEvent BLOCK_SPIKES_EXTEND = new SoundEvent(new Identifier(Main.MOD_ID, "block.spikes.extend"));
    public static final SoundEvent BLOCK_BOO_LANTERN_HIDE = new SoundEvent(new Identifier(Main.MOD_ID, "block.boo_lantern.hide"));
    public static final SoundEvent BLOCK_BOO_LANTERN_REVEAL = new SoundEvent(new Identifier(Main.MOD_ID, "block.boo_lantern.reveal"));

    public static final SoundEvent ITEM_PROJECTILE_THROW = new SoundEvent(new Identifier(Main.MOD_ID, "item.projectile.throw"));
    public static final SoundEvent ITEM_SUPER_MUSHROOM_GROW = new SoundEvent(new Identifier(Main.MOD_ID, "item.super_mushroom.grow"));
    public static final SoundEvent ITEM_ONE_UP_USE = new SoundEvent(new Identifier(Main.MOD_ID, "item.one_up.use"));
    public static final SoundEvent ITEM_FIRE_FLOWER_SHOOT = new SoundEvent(new Identifier(Main.MOD_ID, "item.fire_flower.shoot"));
    public static final SoundEvent ITEM_ICE_FLOWER_SHOOT = new SoundEvent(new Identifier(Main.MOD_ID, "item.ice_flower.shoot"));
    public static final SoundEvent ITEM_SUPER_STAR_USE = new SoundEvent(new Identifier(Main.MOD_ID, "item.super_star.use"));

    public static final SoundEvent ENTITY_FIREBALL_BOUNCE = new SoundEvent(new Identifier(Main.MOD_ID, "entity.fireball.bounce"));
    public static final SoundEvent ENTITY_FIREBALL_HIT = new SoundEvent(new Identifier(Main.MOD_ID, "entity.fireball.hit"));
    public static final SoundEvent ENTITY_ICEBALL_BOUNCE = new SoundEvent(new Identifier(Main.MOD_ID, "entity.iceball.bounce"));
    public static final SoundEvent ENTITY_ICEBALL_HIT = new SoundEvent(new Identifier(Main.MOD_ID, "entity.iceball.hit"));
    public static final SoundEvent ENTITY_ICEBALL_FREEZE = new SoundEvent(new Identifier(Main.MOD_ID, "entity.iceball.freeze"));
    public static final SoundEvent ENTITY_JUMP_BOOTS_JUMP = new SoundEvent(new Identifier(Main.MOD_ID, "entity.jump_boots.jump"));

    public static final SoundEvent ENTITY_GOOMBA_AMBIENT = new SoundEvent(new Identifier(Main.MOD_ID, "entity.goomba.ambient"));
    public static final SoundEvent ENTITY_GOOMBA_HURT = new SoundEvent(new Identifier(Main.MOD_ID, "entity.goomba.hurt"));
    public static final SoundEvent ENTITY_GOOMBA_DEATH = new SoundEvent(new Identifier(Main.MOD_ID, "entity.goomba.death"));
    public static final SoundEvent ENTITY_GOOMBA_STEP = new SoundEvent(new Identifier(Main.MOD_ID, "entity.goomba.step"));
    public static final SoundEvent ENTITY_GOOMBA_FLUTTER = new SoundEvent(new Identifier(Main.MOD_ID, "entity.goomba.flutter"));

    public static void registerSounds() {
        Registry.register(Registry.SOUND_EVENT, "super_block_world:music.grassland", MUSIC_GRASSLAND);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:music.cave", MUSIC_CAVE);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:music.water", MUSIC_WATER);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.poison.ambient", BLOCK_POISON_AMBIENT);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.poison.fill", BLOCK_POISON_FILL);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.poison.empty", BLOCK_POISON_EMPTY);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.frosted_stone.break", BLOCK_FROSTED_STONE_BREAK);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.frosted_stone.fall", BLOCK_FROSTED_STONE_FALL);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.frosted_stone.hit", BLOCK_FROSTED_STONE_HIT);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.frosted_stone.place", BLOCK_FROSTED_STONE_PLACE);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.frosted_stone.step", BLOCK_FROSTED_STONE_STEP);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.item_block.bump", BLOCK_ITEM_BLOCK_BUMP);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.item_block.coin", BLOCK_ITEM_BLOCK_COIN);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.item_block.store", BLOCK_ITEM_BLOCK_STORE);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.item_block.hit", BLOCK_ITEM_BLOCK_HIT);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.warp_pipe.teleport", BLOCK_WARP_PIPE_TELEPORT);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.donut_block.trigger", BLOCK_DONUT_BLOCK_TRIGGER);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.spikes.retract", BLOCK_SPIKES_RETRACT);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.spikes.extend", BLOCK_SPIKES_EXTEND);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.boo_lantern.hide", BLOCK_BOO_LANTERN_HIDE);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:block.boo_lantern.reveal", BLOCK_BOO_LANTERN_REVEAL);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:item.projectile.throw", ITEM_PROJECTILE_THROW);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:item.super_mushroom.grow", ITEM_SUPER_MUSHROOM_GROW);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:item.one_up.use", ITEM_ONE_UP_USE);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:item.fire_flower.shoot", ITEM_FIRE_FLOWER_SHOOT);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:item.ice_flower.shoot", ITEM_ICE_FLOWER_SHOOT);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:item.super_star.use", ITEM_SUPER_STAR_USE);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:entity.fireball.bounce", ENTITY_FIREBALL_BOUNCE);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:entity.fireball.hit", ENTITY_FIREBALL_HIT);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:entity.iceball.bounce", ENTITY_ICEBALL_BOUNCE);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:entity.iceball.hit", ENTITY_ICEBALL_HIT);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:entity.iceball.freeze", ENTITY_ICEBALL_FREEZE);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:entity.jump_boots.jump", ENTITY_JUMP_BOOTS_JUMP);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:entity.goomba.ambient", ENTITY_GOOMBA_AMBIENT);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:entity.goomba.hurt", ENTITY_GOOMBA_HURT);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:entity.goomba.death", ENTITY_GOOMBA_DEATH);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:entity.goomba.step", ENTITY_GOOMBA_STEP);
        Registry.register(Registry.SOUND_EVENT, "super_block_world:entity.goomba.flutter", ENTITY_GOOMBA_FLUTTER);
    }
}
