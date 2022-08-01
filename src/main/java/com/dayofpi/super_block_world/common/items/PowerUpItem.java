package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.common.entities.PowerUp;
import com.dayofpi.super_block_world.util.FormManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.Objects;

public class PowerUpItem extends Item {
    private final SoundEvent soundEvent;
    private final PowerUp powerUp;
    public PowerUpItem(PowerUp powerUp, SoundEvent soundEvent, Settings settings) {
        super(settings);
        this.powerUp = powerUp;
        this.soundEvent = soundEvent;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (Objects.equals(user.getDataTracker().get(FormManager.POWER_UP), PowerUp.NONE.asString())) {
            user.getDataTracker().set(FormManager.POWER_HEALTH, 10);
            user.getDataTracker().set(FormManager.POWER_UP, this.powerUp.asString());
            user.playSound(this.soundEvent, 2.0F, 1.0F);
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            if (!user.getAbilities().creativeMode) {
                itemStack.decrement(1);
            }
            if (!world.isClient()) {
                Random random = world.getRandom();
                for (int i = 0; i < 4; ++i) {
                    ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, user.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), user.getY() + 0.5D, user.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
                }
            }
            return TypedActionResult.success(itemStack, world.isClient());
        }
        else return TypedActionResult.pass(itemStack);
    }
}
