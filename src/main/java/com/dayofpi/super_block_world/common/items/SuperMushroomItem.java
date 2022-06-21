package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.util.TooltipUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
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

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (stack.isOf(ModItems.ONE_UP))
            user.playSound(Sounds.ITEM_ONE_UP, 1.0F, 1.0F);
        return super.finishUsing(stack, world, user);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        FoodComponent foodComponent = stack.getItem().getFoodComponent();
        if (foodComponent != null)
            TooltipUtil.tooltipFromEffects(tooltip, foodComponent.getStatusEffects());
    }
}
