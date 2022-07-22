package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.common.entities.misc.KoopaShellEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.util.TooltipUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ShellItem extends ArmorItem {
    private final int variant;
    public static final StatusEffectInstance redShellEffect = new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 200, 0);
    public static final StatusEffectInstance blueShellEffect = new StatusEffectInstance(StatusEffects.SPEED, 200, 0);
    public static final StatusEffectInstance goldShellEffect = new StatusEffectInstance(StatusEffects.RESISTANCE, 200, 0);

    public ShellItem(int variant, ArmorMaterial material, Settings settings) {
        super(material, EquipmentSlot.HEAD, settings);
        this.variant = variant;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult blockHitResult = raycast(world, user, RaycastContext.FluidHandling.ANY);
        if (blockHitResult.getType() == HitResult.Type.MISS) {
            return super.use(world, user, hand);
        } else if (blockHitResult.getType() != HitResult.Type.BLOCK) {
            return super.use(world, user, hand);
        }

        if (world instanceof ServerWorld) {
            KoopaShellEntity koopaShell = (KoopaShellEntity) ModEntities.KOOPA_SHELL.spawnFromItemStack((ServerWorld) world, itemStack, user, blockHitResult.getBlockPos(), SpawnReason.MOB_SUMMONED, true, false);
            if (koopaShell == null)
                return TypedActionResult.pass(itemStack);
            koopaShell.setVariant(this.variant);
            world.emitGameEvent(user, GameEvent.ENTITY_PLACE, blockHitResult.getBlockPos());
        }

        user.incrementStat(Stats.USED.getOrCreateStat(this));
        if (!user.getAbilities().creativeMode) {
            itemStack.decrement(1);
        }

        return TypedActionResult.success(itemStack, world.isClient());
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.translatable("tooltip.super_block_world.brick_breaking").formatted(Formatting.GOLD));
        if (stack.isOf(ModItems.BUZZY_SHELL))
            tooltip.add(Text.translatable("tooltip.super_block_world.projectile_deflection").formatted(Formatting.GOLD));
        if (stack.isOf(ModItems.RED_SHELL))
            TooltipUtil.tooltipFromEffect(tooltip, redShellEffect, 1);
        if (stack.isOf(ModItems.BLUE_SHELL))
            TooltipUtil.tooltipFromEffect(tooltip, blueShellEffect, 1);
        if (stack.isOf(ModItems.GOLD_SHELL))
            TooltipUtil.tooltipFromEffect(tooltip, goldShellEffect, 1);
    }
}
