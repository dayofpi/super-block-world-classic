package com.dayofpi.sbw_main;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static final SoundEvent MUSIC_GRASSLAND = createSound("music.grassland");
    public static final SoundEvent MUSIC_FOREST = createSound("music.forest");
    public static final SoundEvent MUSIC_CAVE = createSound("music.cave");
    public static final SoundEvent MUSIC_WATER = createSound("music.water");
    public static final SoundEvent MUSIC_ILLUSION = createSound("music.illusion");

    public static final SoundEvent BLOCK_PORTAL_TRIGGER = createSound("block.portal.trigger");
    public static final SoundEvent BLOCK_PORTAL_TRAVEL = createSound("block.portal.travel");

    public static final SoundEvent BLOCK_POISON_AMBIENT = createSound("block.poison.ambient");
    public static final SoundEvent BLOCK_POISON_SWIM = createSound("block.poison.swim");
    public static final SoundEvent BLOCK_POISON_FILL = createSound("block.poison.fill");
    public static final SoundEvent BLOCK_POISON_EMPTY = createSound("block.poison.empty");

    public static final SoundEvent BLOCK_TOADSTONE_BREAK = createSound("block.toadstone.break");
    public static final SoundEvent BLOCK_TOADSTONE_FALL = createSound("block.toadstone.fall");
    public static final SoundEvent BLOCK_TOADSTONE_HIT = createSound("block.toadstone.hit");
    public static final SoundEvent BLOCK_TOADSTONE_PLACE = createSound("block.toadstone.place");
    public static final SoundEvent BLOCK_TOADSTONE_STEP = createSound("block.toadstone.step");

    public static final SoundEvent BLOCK_GRASSY_STONE_BREAK = createSound("block.grassy_stone.break");
    public static final SoundEvent BLOCK_GRASSY_STONE_FALL = createSound("block.grassy_stone.fall");
    public static final SoundEvent BLOCK_GRASSY_STONE_HIT = createSound("block.grassy_stone.hit");
    public static final SoundEvent BLOCK_GRASSY_STONE_PLACE = createSound("block.grassy_stone.place");
    public static final SoundEvent BLOCK_GRASSY_STONE_STEP = createSound("block.grassy_stone.step");

    public static final SoundEvent BLOCK_FROSTED_STONE_BREAK = createSound("block.frosted_stone.break");
    public static final SoundEvent BLOCK_FROSTED_STONE_FALL = createSound("block.frosted_stone.fall");
    public static final SoundEvent BLOCK_FROSTED_STONE_HIT = createSound("block.frosted_stone.hit");
    public static final SoundEvent BLOCK_FROSTED_STONE_PLACE = createSound("block.frosted_stone.place");
    public static final SoundEvent BLOCK_FROSTED_STONE_STEP = createSound("block.frosted_stone.step");

    public static final SoundEvent BLOCK_ITEM_BLOCK_BUMP = createSound("block.item_block.bump");
    public static final SoundEvent BLOCK_ITEM_BLOCK_COIN = createSound("block.item_block.coin");
    public static final SoundEvent BLOCK_ITEM_BLOCK_STORE = createSound("block.item_block.store");
    public static final SoundEvent BLOCK_ITEM_BLOCK_HIT = createSound("block.item_block.hit");
    public static final SoundEvent BLOCK_VEGETABLE_PLUCK = createSound("block.item_block.pluck");

    public static final SoundEvent BLOCK_WARP_PIPE_TELEPORT = createSound("block.warp_pipe.teleport");
    public static final SoundEvent BLOCK_DONUT_BLOCK_TRIGGER = createSound("block.donut_block.trigger");
    public static final SoundEvent BLOCK_TRAMPOLINE_JUMP = createSound("block.trampoline.jump");
    public static final SoundEvent BLOCK_TRAMPOLINE_SUPER_JUMP = createSound("block.trampoline.super_jump");
    public static final SoundEvent BLOCK_SPIKES_RETRACT = createSound("block.spikes.retract");
    public static final SoundEvent BLOCK_SPIKES_EXTEND = createSound("block.spikes.extend");
    public static final SoundEvent BLOCK_BOO_LANTERN_HIDE = createSound("block.boo_lantern.hide");
    public static final SoundEvent BLOCK_BOO_LANTERN_REVEAL = createSound("block.boo_lantern.reveal");

    public static final SoundEvent ITEM_PROJECTILE_THROW = createSound("item.projectile.throw");
    public static final SoundEvent ITEM_SUPER_MUSHROOM_GROW = createSound("item.super_mushroom.grow");
    public static final SoundEvent ITEM_ONE_UP_USE = createSound("item.one_up.use");
    public static final SoundEvent ITEM_FIRE_FLOWER_SHOOT = createSound("item.fire_flower.shoot");
    public static final SoundEvent ITEM_ICE_FLOWER_SHOOT = createSound("item.ice_flower.shoot");
    public static final SoundEvent ITEM_SUPER_STAR_USE = createSound("item.super_star.use");

    public static final SoundEvent ENTITY_FIREBALL_BOUNCE = createSound("entity.fireball.bounce");
    public static final SoundEvent ENTITY_FIREBALL_HIT = createSound("entity.fireball.hit");
    public static final SoundEvent ENTITY_ICEBALL_BOUNCE = createSound("entity.iceball.bounce");
    public static final SoundEvent ENTITY_ICEBALL_HIT = createSound("entity.iceball.hit");
    public static final SoundEvent ENTITY_ICEBALL_FREEZE = createSound("entity.iceball.freeze");
    public static final SoundEvent ENTITY_JUMP_BOOTS_JUMP = createSound("entity.jump_boots.jump");
    public static final SoundEvent ENTITY_JUMP_BOOTS_ATTACK = createSound("entity.jump_boots.attack");
    public static final SoundEvent ENTITY_JUMP_BOOTS_BOUNCE = createSound("entity.jump_boots.bounce");
    public static final SoundEvent ENTITY_ENEMY_SPOT = createSound("entity.enemy.spot");
    public static final SoundEvent ENTITY_MISC_TRANSFORM = createSound("entity.misc.transform");

    public static final SoundEvent ENTITY_MOO_MOO_AMBIENT = createSound("entity.moo_moo.ambient");
    public static final SoundEvent ENTITY_MOO_MOO_HURT = createSound("entity.moo_moo.hurt");
    public static final SoundEvent ENTITY_MOO_MOO_DEATH = createSound("entity.moo_moo.death");
    public static final SoundEvent ENTITY_MOO_MOO_STEP = createSound("entity.moo_moo.step");
    public static final SoundEvent ENTITY_MOO_MOO_BELL = createSound("entity.moo_moo.bell");

    public static final SoundEvent ENTITY_GOOMBA_AMBIENT = createSound("entity.goomba.ambient");
    public static final SoundEvent ENTITY_GOOMBA_HURT = createSound("entity.goomba.hurt");
    public static final SoundEvent ENTITY_GOOMBA_DEATH = createSound("entity.goomba.death");
    public static final SoundEvent ENTITY_GOOMBA_STEP = createSound("entity.goomba.step");
    public static final SoundEvent ENTITY_GOOMBA_FLUTTER = createSound("entity.goomba.flutter");

    public static final SoundEvent ENTITY_BUZZY_HURT = createSound("entity.buzzy.hurt");
    public static final SoundEvent ENTITY_BUZZY_DEATH = createSound("entity.buzzy.death");
    public static final SoundEvent ENTITY_BUZZY_BLOCK = createSound("entity.buzzy.block");
    public static final SoundEvent ENTITY_BUZZY_DROP = createSound("entity.buzzy.drop");
    public static final SoundEvent ENTITY_BUZZY_IMPACT = createSound("entity.buzzy.impact");

    public static final SoundEvent ENTITY_BOO_AMBIENT = createSound("entity.boo.ambient");
    public static final SoundEvent ENTITY_BOO_HURT = createSound("entity.boo.hurt");
    public static final SoundEvent ENTITY_BOO_DEATH = createSound("entity.boo.death");
    public static final SoundEvent ENTITY_BOO_SHY = createSound("entity.boo.shy");
    public static final SoundEvent ENTITY_BOO_ATTACK = createSound("entity.boo.attack");

    public static final SoundEvent ENTITY_KOOPA_AMBIENT = createSound("entity.koopa.ambient");
    public static final SoundEvent ENTITY_KOOPA_HURT = createSound("entity.koopa.hurt");
    public static final SoundEvent ENTITY_KOOPA_DEATH = createSound("entity.koopa.death");

    public static final SoundEvent ENTITY_BOB_OMB_HURT = createSound("entity.bob_omb.hurt");
    public static final SoundEvent ENTITY_BOB_OMB_DEATH = createSound("entity.bob_omb.death");
    public static final SoundEvent ENTITY_BOB_OMB_STEP = createSound("entity.bob_omb.step");
    public static final SoundEvent ENTITY_BOB_OMB_FUSE = createSound("entity.bob_omb.fuse");

    public static final SoundEvent ENTITY_THWOMP_HURT = createSound("entity.thwomp.hurt");
    public static final SoundEvent ENTITY_THWOMP_DEATH = createSound("entity.thwomp.death");
    public static final SoundEvent ENTITY_THWOMP_FALL = createSound("entity.thwomp.fall");
    public static final SoundEvent ENTITY_THWOMP_REST = createSound("entity.thwomp.rest");
    public static final SoundEvent ENTITY_THWOMP_LAND = createSound("entity.thwomp.land");


    private static SoundEvent createSound(String string) {
        return new SoundEvent(new Identifier(Main.MOD_ID, string));
    }

    private static void registerSound(Identifier id, SoundEvent type) {
        Registry.register(Registry.SOUND_EVENT, id, type);
    }

    public static void registerSounds() {
       registerSound(MUSIC_GRASSLAND.getId(), MUSIC_GRASSLAND);
       registerSound(MUSIC_FOREST.getId(), MUSIC_FOREST);
       registerSound(MUSIC_CAVE.getId(), MUSIC_CAVE);
       registerSound(MUSIC_WATER.getId(), MUSIC_WATER);
       registerSound(MUSIC_ILLUSION.getId(), MUSIC_ILLUSION);
       registerSound(BLOCK_PORTAL_TRIGGER.getId(), BLOCK_PORTAL_TRIGGER);
       registerSound(BLOCK_PORTAL_TRAVEL.getId(), BLOCK_PORTAL_TRAVEL);
       registerSound(BLOCK_POISON_AMBIENT.getId(), BLOCK_POISON_AMBIENT);
       registerSound(BLOCK_POISON_SWIM.getId(), BLOCK_POISON_SWIM);
       registerSound(BLOCK_POISON_FILL.getId(), BLOCK_POISON_FILL);
       registerSound(BLOCK_POISON_EMPTY.getId(), BLOCK_POISON_EMPTY);
       registerSound(BLOCK_TOADSTONE_BREAK.getId(), BLOCK_TOADSTONE_BREAK);
       registerSound(BLOCK_TOADSTONE_FALL.getId(), BLOCK_TOADSTONE_FALL);
       registerSound(BLOCK_TOADSTONE_HIT.getId(), BLOCK_TOADSTONE_HIT);
       registerSound(BLOCK_TOADSTONE_PLACE.getId(), BLOCK_TOADSTONE_PLACE);
       registerSound(BLOCK_TOADSTONE_STEP.getId(), BLOCK_TOADSTONE_STEP);

       registerSound(BLOCK_GRASSY_STONE_BREAK.getId(), BLOCK_GRASSY_STONE_BREAK);
       registerSound(BLOCK_GRASSY_STONE_FALL.getId(), BLOCK_GRASSY_STONE_FALL);
       registerSound(BLOCK_GRASSY_STONE_HIT.getId(), BLOCK_GRASSY_STONE_HIT);
       registerSound(BLOCK_GRASSY_STONE_PLACE.getId(), BLOCK_GRASSY_STONE_PLACE);
       registerSound(BLOCK_GRASSY_STONE_STEP.getId(), BLOCK_GRASSY_STONE_STEP);

       registerSound(BLOCK_FROSTED_STONE_BREAK.getId(), BLOCK_FROSTED_STONE_BREAK);
       registerSound(BLOCK_FROSTED_STONE_FALL.getId(), BLOCK_FROSTED_STONE_FALL);
       registerSound(BLOCK_FROSTED_STONE_HIT.getId(), BLOCK_FROSTED_STONE_HIT);
       registerSound(BLOCK_FROSTED_STONE_PLACE.getId(), BLOCK_FROSTED_STONE_PLACE);
       registerSound(BLOCK_FROSTED_STONE_STEP.getId(), BLOCK_FROSTED_STONE_STEP);

       registerSound(BLOCK_ITEM_BLOCK_BUMP.getId(), BLOCK_ITEM_BLOCK_BUMP);
       registerSound(BLOCK_ITEM_BLOCK_COIN.getId(), BLOCK_ITEM_BLOCK_COIN);
       registerSound(BLOCK_ITEM_BLOCK_STORE.getId(), BLOCK_ITEM_BLOCK_STORE);
       registerSound(BLOCK_ITEM_BLOCK_HIT.getId(), BLOCK_ITEM_BLOCK_HIT);
       registerSound(BLOCK_VEGETABLE_PLUCK.getId(), BLOCK_VEGETABLE_PLUCK);
       registerSound(BLOCK_WARP_PIPE_TELEPORT.getId(), BLOCK_WARP_PIPE_TELEPORT);
       registerSound(BLOCK_DONUT_BLOCK_TRIGGER.getId(), BLOCK_DONUT_BLOCK_TRIGGER);
       registerSound(BLOCK_TRAMPOLINE_JUMP.getId(), BLOCK_TRAMPOLINE_JUMP);
       registerSound(BLOCK_TRAMPOLINE_SUPER_JUMP.getId(), BLOCK_TRAMPOLINE_SUPER_JUMP);
       registerSound(BLOCK_SPIKES_RETRACT.getId(), BLOCK_SPIKES_RETRACT);
       registerSound(BLOCK_SPIKES_EXTEND.getId(), BLOCK_SPIKES_EXTEND);
       registerSound(BLOCK_BOO_LANTERN_HIDE.getId(), BLOCK_BOO_LANTERN_HIDE);
       registerSound(BLOCK_BOO_LANTERN_REVEAL.getId(), BLOCK_BOO_LANTERN_REVEAL);

       registerSound(ITEM_PROJECTILE_THROW.getId(), ITEM_PROJECTILE_THROW);
       registerSound(ITEM_SUPER_MUSHROOM_GROW.getId(), ITEM_SUPER_MUSHROOM_GROW);
       registerSound(ITEM_ONE_UP_USE.getId(), ITEM_ONE_UP_USE);
       registerSound(ITEM_FIRE_FLOWER_SHOOT.getId(), ITEM_FIRE_FLOWER_SHOOT);
       registerSound(ITEM_ICE_FLOWER_SHOOT.getId(), ITEM_ICE_FLOWER_SHOOT);
       registerSound(ITEM_SUPER_STAR_USE.getId(), ITEM_SUPER_STAR_USE);

       registerSound(ENTITY_FIREBALL_BOUNCE.getId(), ENTITY_FIREBALL_BOUNCE);
       registerSound(ENTITY_FIREBALL_HIT.getId(), ENTITY_FIREBALL_HIT);
       registerSound(ENTITY_ICEBALL_BOUNCE.getId(), ENTITY_ICEBALL_BOUNCE);
       registerSound(ENTITY_ICEBALL_HIT.getId(), ENTITY_ICEBALL_HIT);
       registerSound(ENTITY_ICEBALL_FREEZE.getId(), ENTITY_ICEBALL_FREEZE);
       registerSound(ENTITY_JUMP_BOOTS_JUMP.getId(), ENTITY_JUMP_BOOTS_JUMP);
       registerSound(ENTITY_JUMP_BOOTS_ATTACK.getId(), ENTITY_JUMP_BOOTS_ATTACK);
       registerSound(ENTITY_JUMP_BOOTS_BOUNCE.getId(), ENTITY_JUMP_BOOTS_BOUNCE);
       registerSound(ENTITY_ENEMY_SPOT.getId(), ENTITY_ENEMY_SPOT);
       registerSound(ENTITY_MISC_TRANSFORM.getId(), ENTITY_MISC_TRANSFORM);

       registerSound(ENTITY_MOO_MOO_AMBIENT.getId(), ENTITY_MOO_MOO_AMBIENT);
       registerSound(ENTITY_MOO_MOO_HURT.getId(), ENTITY_MOO_MOO_HURT);
       registerSound(ENTITY_MOO_MOO_DEATH.getId(), ENTITY_MOO_MOO_DEATH);
       registerSound(ENTITY_MOO_MOO_STEP.getId(), ENTITY_MOO_MOO_STEP);
       registerSound(ENTITY_MOO_MOO_BELL.getId(), ENTITY_MOO_MOO_BELL);
       registerSound(ENTITY_GOOMBA_AMBIENT.getId(), ENTITY_GOOMBA_AMBIENT);
       registerSound(ENTITY_GOOMBA_HURT.getId(), ENTITY_GOOMBA_HURT);
       registerSound(ENTITY_GOOMBA_DEATH.getId(), ENTITY_GOOMBA_DEATH);
       registerSound(ENTITY_GOOMBA_STEP.getId(), ENTITY_GOOMBA_STEP);
       registerSound(ENTITY_GOOMBA_FLUTTER.getId(), ENTITY_GOOMBA_FLUTTER);
       registerSound(ENTITY_BUZZY_HURT.getId(), ENTITY_BUZZY_HURT);
       registerSound(ENTITY_BUZZY_DEATH.getId(), ENTITY_BUZZY_DEATH);
       registerSound(ENTITY_BUZZY_BLOCK.getId(), ENTITY_BUZZY_BLOCK);
       registerSound(ENTITY_BUZZY_DROP.getId(), ENTITY_BUZZY_DROP);
       registerSound(ENTITY_BUZZY_IMPACT.getId(), ENTITY_BUZZY_IMPACT);
       registerSound(ENTITY_BOO_AMBIENT.getId(), ENTITY_BOO_AMBIENT);
       registerSound(ENTITY_BOO_HURT.getId(), ENTITY_BOO_HURT);
       registerSound(ENTITY_BOO_DEATH.getId(), ENTITY_BOO_DEATH);
       registerSound(ENTITY_BOO_ATTACK.getId(), ENTITY_BOO_ATTACK);
       registerSound(ENTITY_BOO_SHY.getId(), ENTITY_BOO_SHY);
       registerSound(ENTITY_KOOPA_AMBIENT.getId(), ENTITY_KOOPA_AMBIENT);
       registerSound(ENTITY_KOOPA_HURT.getId(), ENTITY_KOOPA_HURT);
       registerSound(ENTITY_KOOPA_DEATH.getId(), ENTITY_KOOPA_DEATH);
       registerSound(ENTITY_BOB_OMB_HURT.getId(), ENTITY_BOB_OMB_HURT);
       registerSound(ENTITY_BOB_OMB_DEATH.getId(), ENTITY_BOB_OMB_DEATH);
       registerSound(ENTITY_BOB_OMB_STEP.getId(), ENTITY_BOB_OMB_STEP);
       registerSound(ENTITY_BOB_OMB_FUSE.getId(), ENTITY_BOB_OMB_FUSE);

       registerSound(ENTITY_THWOMP_HURT.getId(), ENTITY_THWOMP_HURT);
       registerSound(ENTITY_THWOMP_DEATH.getId(), ENTITY_THWOMP_DEATH);
       registerSound(ENTITY_THWOMP_FALL.getId(), ENTITY_THWOMP_FALL);
       registerSound(ENTITY_THWOMP_REST.getId(), ENTITY_THWOMP_REST);
       registerSound(ENTITY_THWOMP_LAND.getId(), ENTITY_THWOMP_LAND);
    }
}
