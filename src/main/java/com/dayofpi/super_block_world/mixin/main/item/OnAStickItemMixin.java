package com.dayofpi.super_block_world.mixin.main.item;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.common.entities.mob.KoopaEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemSteerable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.OnAStickItem;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(OnAStickItem.class)
public class OnAStickItemMixin extends Item {
    @Shadow @Final private int damagePerUse;

    public OnAStickItemMixin(Settings settings) {
        super(settings);
    }

    @Inject(at=@At("HEAD"), method = "use", cancellable = true)
    private void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> info) {
        ItemStack itemStack = user.getStackInHand(hand);
        Entity entity = user.getVehicle();
        if (user.hasVehicle() && entity instanceof ItemSteerable && ((ItemSteerable) entity).consumeOnAStickItem()) {
            entity.playSound(SoundInit.ENTITY_MISC_BOOST, 1.0F, ((LivingEntity)entity).getSoundPitch());
        }
        if (user.hasVehicle() && entity instanceof KoopaEntity && ((ItemSteerable) entity).consumeOnAStickItem()) {
            itemStack.damage(this.damagePerUse, user, p -> p.sendToolBreakStatus(hand));
            if (itemStack.isEmpty()) {
                ItemStack itemStack2 = new ItemStack(Items.FISHING_ROD);
                itemStack2.setNbt(itemStack.getNbt());
                info.setReturnValue(TypedActionResult.success(itemStack2));
                info.cancel();
            }
            info.setReturnValue(TypedActionResult.success(itemStack));
            info.cancel();
        }
    }
}
