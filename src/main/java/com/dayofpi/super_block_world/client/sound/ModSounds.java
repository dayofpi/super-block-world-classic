package com.dayofpi.super_block_world.client.sound;

import com.dayofpi.super_block_world.main.Main;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {
    public static final SoundEvent MUSIC_MENU = createSound("music.menu");
    public static final SoundEvent MUSIC_GRASSLAND = createSound("music.grassland");
    public static final SoundEvent MUSIC_FOREST = createSound("music.forest");
    public static final SoundEvent MUSIC_CAVE = createSound("music.cave");
    public static final SoundEvent MUSIC_WATER = createSound("music.water");
    public static final SoundEvent MUSIC_ILLUSION = createSound("music.illusion");

    public static final SoundEvent BLOCK_PORTAL_TRIGGER = createSound("block.portal.trigger");
    public static final SoundEvent BLOCK_PORTAL_TRAVEL = createSound("block.portal.travel");

    public static final SoundEvent BLOCK_POISON_AMBIENT = createSound("block.poison.ambient");
    public static final SoundEvent BLOCK_POISON_SWIM = createSound("block.poison.swim");
    public static final SoundEvent ITEM_POISON_BUCKET_FILL = createSound("item.poison_bucket.fill");
    public static final SoundEvent ITEM_POISON_BUCKET_EMPTY = createSound("item.poison_bucket.empty");

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

    public static final SoundEvent BLOCK_SHOREGRASS_BREAK = createSound("block.shoregrass.break");
    public static final SoundEvent BLOCK_SHOREGRASS_FALL = createSound("block.shoregrass.fall");
    public static final SoundEvent BLOCK_SHOREGRASS_HIT = createSound("block.shoregrass.hit");
    public static final SoundEvent BLOCK_SHOREGRASS_PLACE = createSound("block.shoregrass.place");
    public static final SoundEvent BLOCK_SHOREGRASS_STEP = createSound("block.shoregrass.step");

    public static final SoundEvent BLOCK_SHERBET_SOIL_BREAK = createSound("block.sherbet_soil.break");
    public static final SoundEvent BLOCK_SHERBET_SOIL_FALL = createSound("block.sherbet_soil.fall");
    public static final SoundEvent BLOCK_SHERBET_SOIL_HIT = createSound("block.sherbet_soil.hit");
    public static final SoundEvent BLOCK_SHERBET_SOIL_PLACE = createSound("block.sherbet_soil.place");
    public static final SoundEvent BLOCK_SHERBET_SOIL_STEP = createSound("block.sherbet_soil.step");

    public static final SoundEvent BLOCK_EMPTY_BLOCK_BUMP = createSound("block.item_block.bump");
    public static final SoundEvent BLOCK_COIN_BLOCK_HIT = createSound("block.item_block.coin");
    public static final SoundEvent BLOCK_EMPTY_BLOCK_STORE = createSound("block.item_block.store");
    public static final SoundEvent BLOCK_QUESTION_BLOCK_HIT = createSound("block.item_block.hit");
    public static final SoundEvent BLOCK_VEGETABLE_PLUCK = createSound("block.item_block.pluck");

    public static final SoundEvent BLOCK_WARP_PIPE_TELEPORT = createSound("block.warp_pipe.teleport");
    public static final SoundEvent BLOCK_DONUT_BLOCK_TRIGGER = createSound("block.donut_block.trigger");
    public static final SoundEvent BLOCK_TRAMPOLINE_JUMP = createSound("block.trampoline.jump");
    public static final SoundEvent BLOCK_TRAMPOLINE_SUPER_JUMP = createSound("block.trampoline.super_jump");
    public static final SoundEvent BLOCK_SPIKES_RETRACT = createSound("block.spikes.retract");
    public static final SoundEvent BLOCK_SPIKES_EXTEND = createSound("block.spikes.extend");
    public static final SoundEvent BLOCK_BOO_LANTERN_HIDE = createSound("block.boo_lantern.hide");
    public static final SoundEvent BLOCK_BOO_LANTERN_REVEAL = createSound("block.boo_lantern.reveal");
    public static final SoundEvent BLOCK_JELLYBEAM_DAMAGE = createSound("block.jellybeam.damage");

    public static final SoundEvent ITEM_COIN_COLLECT = createSound("item.collect.coin");
    public static final SoundEvent ITEM_STAR_COIN_COLLECT = createSound("item.collect.star_coin");
    public static final SoundEvent ITEM_PROJECTILE_GENERIC = createSound("item.projectile.generic");
    public static final SoundEvent ITEM_PROJECTILE_HAMMER = createSound("item.projectile.hammer");
    public static final SoundEvent ITEM_PROJECTILE_BOMB = createSound("item.projectile.bomb");
    public static final SoundEvent ITEM_POWER_UP = createSound("item.power_up.use");
    public static final SoundEvent ITEM_ONE_UP = createSound("item.one_up.use");
    public static final SoundEvent ITEM_FIRE_FLOWER = createSound("item.fire_flower.shoot");
    public static final SoundEvent ITEM_ICE_FLOWER = createSound("item.ice_flower.shoot");
    public static final SoundEvent ITEM_SUPER_STAR = createSound("item.super_star.use");
    public static final SoundEvent ITEM_SUPER_PICKAX = createSound("item.super_pickax.use");
    public static final SoundEvent ITEM_FUZZY_MAGNET = createSound("item.fuzzy_magnet.pull");
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
    public static final SoundEvent ENTITY_MISC_TAIL_ATTACK = createSound("entity.misc.tail_attack");

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

    public static final SoundEvent ENTITY_STINGBY_AMBIENT = createSound("entity.stingby.ambient");
    public static final SoundEvent ENTITY_STINGBY_HURT = createSound("entity.stingby.hurt");
    public static final SoundEvent ENTITY_STINGBY_DEATH = createSound("entity.stingby.death");
    public static final SoundEvent ENTITY_STINGBY_STING = createSound("entity.stingby.sting");

    public static final SoundEvent ENTITY_BOB_OMB_HURT = createSound("entity.bob_omb.hurt");
    public static final SoundEvent ENTITY_BOB_OMB_DEATH = createSound("entity.bob_omb.death");
    public static final SoundEvent ENTITY_BOB_OMB_STEP = createSound("entity.bob_omb.step");
    public static final SoundEvent ENTITY_BOB_OMB_FUSE = createSound("entity.bob_omb.fuse");

    public static final SoundEvent ENTITY_FUZZY_BREAK = createSound("entity.fuzzy.break");

    public static final SoundEvent ENTITY_THWOMP_HURT = createSound("entity.thwomp.hurt");
    public static final SoundEvent ENTITY_THWOMP_DEATH = createSound("entity.thwomp.death");
    public static final SoundEvent ENTITY_THWOMP_FALL = createSound("entity.thwomp.fall");
    public static final SoundEvent ENTITY_THWOMP_REST = createSound("entity.thwomp.rest");
    public static final SoundEvent ENTITY_THWOMP_LAND = createSound("entity.thwomp.land");


    private static SoundEvent createSound(String string) {
        return new SoundEvent(new Identifier(Main.MOD_ID, string));
    }

    private static void registerSound(SoundEvent type) {
        Registry.register(Registry.SOUND_EVENT, type.getId(), type);
    }

    public static void registerSounds() {
       registerSound(MUSIC_MENU);
       registerSound(MUSIC_GRASSLAND);
       registerSound(MUSIC_FOREST);
       registerSound(MUSIC_CAVE);
       registerSound(MUSIC_WATER);
       registerSound(MUSIC_ILLUSION);
       registerSound(BLOCK_PORTAL_TRIGGER);
       registerSound(BLOCK_PORTAL_TRAVEL);
       registerSound(BLOCK_POISON_AMBIENT);
       registerSound(BLOCK_POISON_SWIM);
       registerSound(ITEM_POISON_BUCKET_FILL);
       registerSound(ITEM_POISON_BUCKET_EMPTY);
       registerSound(BLOCK_TOADSTONE_BREAK);
       registerSound(BLOCK_TOADSTONE_FALL);
       registerSound(BLOCK_TOADSTONE_HIT);
       registerSound(BLOCK_TOADSTONE_PLACE);
       registerSound(BLOCK_TOADSTONE_STEP);

       registerSound(BLOCK_GRASSY_STONE_BREAK);
       registerSound(BLOCK_GRASSY_STONE_FALL);
       registerSound(BLOCK_GRASSY_STONE_HIT);
       registerSound(BLOCK_GRASSY_STONE_PLACE);
       registerSound(BLOCK_GRASSY_STONE_STEP);

       registerSound(BLOCK_FROSTED_STONE_BREAK);
       registerSound(BLOCK_FROSTED_STONE_FALL);
       registerSound(BLOCK_FROSTED_STONE_HIT);
       registerSound(BLOCK_FROSTED_STONE_PLACE);
       registerSound(BLOCK_FROSTED_STONE_STEP);

        registerSound(BLOCK_SHOREGRASS_BREAK);
        registerSound(BLOCK_SHOREGRASS_FALL);
        registerSound(BLOCK_SHOREGRASS_HIT);
        registerSound(BLOCK_SHOREGRASS_PLACE);
        registerSound(BLOCK_SHOREGRASS_STEP);

        registerSound(BLOCK_SHERBET_SOIL_BREAK);
        registerSound(BLOCK_SHERBET_SOIL_FALL);
        registerSound(BLOCK_SHERBET_SOIL_HIT);
        registerSound(BLOCK_SHERBET_SOIL_PLACE);
        registerSound(BLOCK_SHERBET_SOIL_STEP);

       registerSound(BLOCK_EMPTY_BLOCK_BUMP);
       registerSound(BLOCK_COIN_BLOCK_HIT);
       registerSound(BLOCK_EMPTY_BLOCK_STORE);
       registerSound(BLOCK_QUESTION_BLOCK_HIT);
       registerSound(BLOCK_VEGETABLE_PLUCK);
       registerSound(BLOCK_WARP_PIPE_TELEPORT);
       registerSound(BLOCK_DONUT_BLOCK_TRIGGER);
       registerSound(BLOCK_TRAMPOLINE_JUMP);
       registerSound(BLOCK_TRAMPOLINE_SUPER_JUMP);
       registerSound(BLOCK_SPIKES_RETRACT);
       registerSound(BLOCK_SPIKES_EXTEND);
       registerSound(BLOCK_BOO_LANTERN_HIDE);
       registerSound(BLOCK_BOO_LANTERN_REVEAL);
       registerSound(BLOCK_JELLYBEAM_DAMAGE);

       registerSound(ITEM_COIN_COLLECT);
       registerSound(ITEM_STAR_COIN_COLLECT);
       registerSound(ITEM_PROJECTILE_GENERIC);
       registerSound(ITEM_PROJECTILE_HAMMER);
       registerSound(ITEM_PROJECTILE_BOMB);
       registerSound(ITEM_POWER_UP);
       registerSound(ITEM_ONE_UP);
       registerSound(ITEM_FIRE_FLOWER);
       registerSound(ITEM_ICE_FLOWER);
       registerSound(ITEM_SUPER_STAR);
       registerSound(ITEM_SUPER_PICKAX);
       registerSound(ITEM_FUZZY_MAGNET);

       registerSound(ENTITY_FIREBALL_BOUNCE);
       registerSound(ENTITY_FIREBALL_HIT);
       registerSound(ENTITY_ICEBALL_BOUNCE);
       registerSound(ENTITY_ICEBALL_HIT);
       registerSound(ENTITY_ICEBALL_FREEZE);
       registerSound(ENTITY_JUMP_BOOTS_JUMP);
       registerSound(ENTITY_JUMP_BOOTS_ATTACK);
       registerSound(ENTITY_JUMP_BOOTS_BOUNCE);
       registerSound(ENTITY_ENEMY_SPOT);
       registerSound(ENTITY_MISC_TRANSFORM);
       registerSound(ENTITY_MISC_TAIL_ATTACK);

       registerSound(ENTITY_MOO_MOO_AMBIENT);
       registerSound(ENTITY_MOO_MOO_HURT);
       registerSound(ENTITY_MOO_MOO_DEATH);
       registerSound(ENTITY_MOO_MOO_STEP);
       registerSound(ENTITY_MOO_MOO_BELL);

       registerSound(ENTITY_GOOMBA_AMBIENT);
       registerSound(ENTITY_GOOMBA_HURT);
       registerSound(ENTITY_GOOMBA_DEATH);
       registerSound(ENTITY_GOOMBA_STEP);
       registerSound(ENTITY_GOOMBA_FLUTTER);

       registerSound(ENTITY_BUZZY_HURT);
       registerSound(ENTITY_BUZZY_DEATH);
       registerSound(ENTITY_BUZZY_BLOCK);
       registerSound(ENTITY_BUZZY_DROP);
       registerSound(ENTITY_BUZZY_IMPACT);

       registerSound(ENTITY_BOO_AMBIENT);
       registerSound(ENTITY_BOO_HURT);
       registerSound(ENTITY_BOO_DEATH);
       registerSound(ENTITY_BOO_ATTACK);
       registerSound(ENTITY_BOO_SHY);

       registerSound(ENTITY_KOOPA_AMBIENT);
       registerSound(ENTITY_KOOPA_HURT);
       registerSound(ENTITY_KOOPA_DEATH);

        registerSound(ENTITY_STINGBY_AMBIENT);
        registerSound(ENTITY_STINGBY_HURT);
        registerSound(ENTITY_STINGBY_DEATH);
        registerSound(ENTITY_STINGBY_STING);

       registerSound(ENTITY_BOB_OMB_HURT);
       registerSound(ENTITY_BOB_OMB_DEATH);
       registerSound(ENTITY_BOB_OMB_STEP);
       registerSound(ENTITY_BOB_OMB_FUSE);

       registerSound(ENTITY_FUZZY_BREAK);

       registerSound(ENTITY_THWOMP_HURT);
       registerSound(ENTITY_THWOMP_DEATH);
       registerSound(ENTITY_THWOMP_FALL);
       registerSound(ENTITY_THWOMP_REST);
       registerSound(ENTITY_THWOMP_LAND);
    }
}