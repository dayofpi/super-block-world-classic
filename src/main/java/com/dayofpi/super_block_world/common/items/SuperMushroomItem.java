package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.common.util.TooltipUtil;
import com.dayofpi.super_block_world.registry.more.FoodComponents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SuperMushroomItem extends Item {
    public SuperMushroomItem(Settings settings) {
        super(settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        TooltipUtil.tooltipFromEffects(tooltip, FoodComponents.SUPER_MUSHROOM.getStatusEffects());
    }
}
