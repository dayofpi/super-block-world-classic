/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.super_block_world.criterion.criteria;

import com.dayofpi.super_block_world.Main;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class UseWarpPipeCriterion extends AbstractCriterion<UseWarpPipeCriterion.Conditions> {
    private static final Identifier ID = new Identifier(Main.MOD_ID, "use_warp_pipe");

    @Override
    public Identifier getId() {
        return ID;
    }

    @Override
    public Conditions conditionsFromJson(JsonObject jsonObject, EntityPredicate.Extended extended, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
        return new Conditions(extended);
    }

    public void trigger(ServerPlayerEntity player) {
        this.trigger(player, conditions -> true);
    }

    public static class Conditions extends AbstractCriterionConditions {
        public Conditions(EntityPredicate.Extended player) {
            super(ID, player);
        }
    }
}

