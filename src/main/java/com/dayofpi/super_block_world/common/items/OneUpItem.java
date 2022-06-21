package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.util.TooltipUtil;
import com.dayofpi.super_block_world.registry.more.FoodComponents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class OneUpItem extends Item {
    public OneUpItem(Settings settings) {
        super(settings);
    }

    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.playSound(SoundInit.ITEM_ONE_UP, 1.0F, 1.0F);
        return super.finishUsing(stack, world, user);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        TooltipUtil.tooltipFromEffects(tooltip, FoodComponents.ONE_UP.getStatusEffects());
    }
}
