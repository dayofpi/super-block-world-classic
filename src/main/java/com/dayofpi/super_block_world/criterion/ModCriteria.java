package com.dayofpi.super_block_world.criterion;

import com.dayofpi.super_block_world.criterion.criteria.*;

public class ModCriteria {
    public static final UseWarpPipeCriterion USE_WARP_PIPE = new UseWarpPipeCriterion();
    public static final JumpUnderBlockCriterion JUMP_UNDER_BLOCK = new JumpUnderBlockCriterion();
    public static final CureToadCriterion CURE_TOAD = new CureToadCriterion();
    public static final RidePropellerBlockCriterion RIDE_PROPELLER_BLOCK = new RidePropellerBlockCriterion();
    public static final SoulmatesCriterion SOULMATES = new SoulmatesCriterion();
    public static final StompEntityCriterion STOMP_ENTITY = new StompEntityCriterion();
    public static final EatEntityWithYoshiCriterion EAT_ENTITY_WITH_YOSHI = new EatEntityWithYoshiCriterion();

    public static void register() {
        net.minecraft.advancement.criterion.Criteria.register(USE_WARP_PIPE);
        net.minecraft.advancement.criterion.Criteria.register(JUMP_UNDER_BLOCK);
        net.minecraft.advancement.criterion.Criteria.register(CURE_TOAD);
        net.minecraft.advancement.criterion.Criteria.register(RIDE_PROPELLER_BLOCK);
        net.minecraft.advancement.criterion.Criteria.register(SOULMATES);
        net.minecraft.advancement.criterion.Criteria.register(STOMP_ENTITY);
        net.minecraft.advancement.criterion.Criteria.register(EAT_ENTITY_WITH_YOSHI);
    }
}
