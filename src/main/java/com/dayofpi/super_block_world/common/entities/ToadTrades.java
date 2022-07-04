package com.dayofpi.super_block_world.common.entities;

import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModItems;
import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.random.Random;
import net.minecraft.village.TradeOffer;
import net.minecraft.village.TradeOffers;
import org.jetbrains.annotations.Nullable;

public class ToadTrades {
    public static final Int2ObjectMap<TradeOffers.Factory[]> MAILTOAD_TRADES = copyToFastUtilMap(ImmutableMap.of(
            1, new TradeOffers.Factory[]{
                    new SellStampFactory(new ItemStack(ModBlocks.PAWFLOWER), new ItemStack(ModItems.CAT_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(ModItems.CLOUD_PUFF), new ItemStack(ModItems.CLOUD_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(ModItems.FIRE_FLOWER), new ItemStack(ModItems.FLOWER_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(ModItems.FUZZBALL), new ItemStack(ModItems.FUZZY_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(Items.BROWN_MUSHROOM), new ItemStack(ModItems.GOOMBA_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(Items.FEATHER), new ItemStack(ModItems.CHEEP_CHEEP_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(ModBlocks.SMILEY_SUNFLOWER), new ItemStack(ModItems.SMILEY_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(ModItems.SUPER_MUSHROOM), new ItemStack(ModItems.MUSHROOM_STAMP), 8, 2),
            }, 2, new TradeOffers.Factory[]{
                    new SellStampFactory(new ItemStack(Items.RED_DYE), new ItemStack(ModItems.MARIO_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(Items.GREEN_DYE), new ItemStack(ModItems.LUIGI_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(Items.WHITE_DYE), new ItemStack(ModItems.PRINCESS_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(Items.ARROW), new ItemStack(ModItems.ARROW_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(Items.INK_SAC), new ItemStack(ModItems.BLOOPER_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(ModItems.BOTTLED_GHOST), new ItemStack(ModItems.BOO_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(ModItems.BUZZY_SHELL_PIECE), new ItemStack(ModItems.BUZZY_STAMP), 8, 2),
                    new SellStampFactory(new ItemStack(ModItems.PURPLE_STAR_BIT), new ItemStack(ModItems.MOM_STAMP), 8, 2)
            }));

    private static Int2ObjectMap<TradeOffers.Factory[]> copyToFastUtilMap(ImmutableMap<Integer, TradeOffers.Factory[]> map) {
        return new Int2ObjectOpenHashMap<>(map);
    }

    static class SellStampFactory implements TradeOffers.Factory {
        private final ItemStack ingredient;
        private final ItemStack stamp;
        private final int maxUses;
        private final int experience;
        private final float multiplier;

        public SellStampFactory(ItemStack ingredient, ItemStack stamp, int maxUses, int experience) {
            this.ingredient = ingredient;
            this.stamp = stamp;
            this.maxUses = maxUses;
            this.experience = experience;
            this.multiplier = 0.05F;
        }

        @Nullable
        @Override
        public TradeOffer create(Entity entity, Random random) {
            return new TradeOffer(new ItemStack(ModItems.COIN, 10), new ItemStack(ingredient.getItem()), new ItemStack(stamp.getItem(), 1), this.maxUses, this.experience, this.multiplier);
        }
    }
}
