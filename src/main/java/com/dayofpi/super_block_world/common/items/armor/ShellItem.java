package com.dayofpi.super_block_world.common.items.armor;

import com.dayofpi.super_block_world.common.entities.mob.KoopaShellEntity;
import com.dayofpi.super_block_world.common.util.TooltipUtil;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import com.dayofpi.super_block_world.registry.more.Materials;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;


public class ShellItem extends ArmorItem {
    public ShellItem(ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add( new TranslatableText("tooltip.super_block_world.brick_break").formatted(Formatting.GOLD));
        if (stack.isOf(ItemInit.BUZZY_SHELL)) {
            tooltip.add(new TranslatableText("tooltip.super_block_world.projectile_deflect").formatted(Formatting.GOLD));
        } else if (stack.isOf(ItemInit.RED_SHELL)) {
            TooltipUtil.tooltipFromEffect(tooltip, new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE), 1);
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockEntity;
        World world = context.getWorld();
        if (!(world instanceof ServerWorld)) {
            return ActionResult.SUCCESS;
        }
        ItemStack itemStack = context.getStack();
        BlockPos blockPos = context.getBlockPos();
        Direction direction = context.getSide();
        BlockState blockState = world.getBlockState(blockPos);
        blockEntity = blockState.getCollisionShape(world, blockPos).isEmpty() ? blockPos : blockPos.offset(direction);

        boolean invertY = !Objects.equals(blockPos, blockEntity) && direction == Direction.UP;
        if (itemStack.isOf(ItemInit.BUZZY_SHELL)) {
            EntityType<?> mobSpawnerLogic = EntityInit.BUZZY_SHELL;
            mobSpawnerLogic.spawnFromItemStack((ServerWorld) world, itemStack, context.getPlayer(), blockEntity, SpawnReason.SPAWN_EGG, true, invertY);
            itemStack.decrement(1);
            world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
        }
        else {
            EntityType<?> mobSpawnerLogic = EntityInit.KOOPA_SHELL;
            KoopaShellEntity shellEntity = (KoopaShellEntity) mobSpawnerLogic.spawnFromItemStack((ServerWorld) world, itemStack, context.getPlayer(), blockEntity, SpawnReason.SPAWN_EGG, true, invertY);
            if (shellEntity != null) {
                shellEntity.setKoopaType(this.getKoopaType());
                shellEntity.setPersistent();
                itemStack.decrement(1);
                world.emitGameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, blockPos);
            }
        }
        return ActionResult.CONSUME;
    }

    private int getKoopaType() {
        return this.getMaterial().equals(Materials.GREEN_SHELL) ? 0 : 1;
    }
}
