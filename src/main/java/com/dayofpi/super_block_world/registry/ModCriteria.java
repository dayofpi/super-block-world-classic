package com.dayofpi.super_block_world.registry;

import com.dayofpi.super_block_world.common.criteria.*;

public class ModCriteria {
    public static final JumpUnderBlockCriterion HIT_BLOCK_WITH_HEAD = new JumpUnderBlockCriterion();
    public static final CureToadCriterion CURE_TOAD = new CureToadCriterion();
    public static final RidePropellerBlockCriterion RIDE_PROPELLER_BLOCK = new RidePropellerBlockCriterion();
    public static final StompEntityCriterion STOMP_ENTITY = new StompEntityCriterion();
    public static final EatEntityWithYoshiCriterion EAT_ENTITY_WITH_YOSHI = new EatEntityWithYoshiCriterion();

    public static void register() {
        net.minecraft.advancement.criterion.Criteria.register(HIT_BLOCK_WITH_HEAD);
        net.minecraft.advancement.criterion.Criteria.register(CURE_TOAD);
        net.minecraft.advancement.criterion.Criteria.register(RIDE_PROPELLER_BLOCK);
        net.minecraft.advancement.criterion.Criteria.register(STOMP_ENTITY);
        net.minecraft.advancement.criterion.Criteria.register(EAT_ENTITY_WITH_YOSHI);
    }
}
