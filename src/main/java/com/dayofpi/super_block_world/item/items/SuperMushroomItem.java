package com.dayofpi.super_block_world.item.items;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.util.PowerUp;
import com.dayofpi.super_block_world.item.ModItems;
import com.dayofpi.super_block_world.util.FormManager;
import com.dayofpi.super_block_world.util.TooltipUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class SuperMushroomItem extends Item {
    private final SoundEvent soundEvent;
    public SuperMushroomItem(SoundEvent soundEvent, Settings settings) {
        super(settings);
        this.soundEvent = soundEvent;
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        if (this.soundEvent != null)
            user.playSound(this.soundEvent, 1.0F, user.getSoundPitch());
        if (stack.isOf(ModItems.POISON_MUSHROOM) && !user.getDataTracker().get(FormManager.POWER_UP).equals(PowerUp.NONE.asString())) {
            user.getDataTracker().set(FormManager.POWER_UP, PowerUp.NONE.asString());
            user.playSound(Sounds.ENTITY_GENERIC_POWER_DOWN, 1.0F, 1.0F);
            if (!world.isClient()) {
                Random random = world.getRandom();
                for (int i = 0; i < 4; ++i) {
                    ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, user.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), user.getY() + 0.5D, user.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
        }
        return super.finishUsing(stack, world, user);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        FoodComponent foodComponent = stack.getItem().getFoodComponent();
        if (foodComponent != null)
            TooltipUtil.tooltipFromEffects(tooltip, foodComponent.getStatusEffects());
    }
}
