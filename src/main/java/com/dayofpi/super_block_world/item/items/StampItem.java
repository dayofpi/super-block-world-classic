package com.dayofpi.super_block_world.item.items;

import com.dayofpi.super_block_world.entity.entities.misc.StampEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StampItem extends Item {
    private final StampEntity.Stamp stamp;

    public StampItem(StampEntity.Stamp stamp, Settings settings) {
        super(settings);
        this.stamp = stamp;
    }

    public String getTranslationKey(ItemStack stack) {
        return "item.super_block_world.stamp";
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        StampEntity stampEntity;
        BlockPos blockPos = context.getBlockPos();
        Direction direction = context.getSide();
        BlockPos blockPos2 = blockPos.offset(direction);
        PlayerEntity playerEntity = context.getPlayer();
        ItemStack itemStack = context.getStack();
        if (playerEntity != null && !this.canPlaceOn(playerEntity, direction, itemStack, blockPos2)) {
            return ActionResult.FAIL;
        }
        World world = context.getWorld();
        stampEntity = new StampEntity(world, blockPos2, direction);
        stampEntity.setStamp(this.stamp);
        NbtCompound nbtCompound = itemStack.getNbt();
        if (nbtCompound != null) {
            EntityType.loadFromEntityNbt(world, playerEntity, stampEntity, nbtCompound);
        }
        if (stampEntity.canStayAttached()) {
            if (!world.isClient) {
                stampEntity.onPlace();
                world.emitGameEvent(playerEntity, GameEvent.ENTITY_PLACE, blockPos);
                world.spawnEntity(stampEntity);
            }
            itemStack.decrement(1);
            return ActionResult.success(world.isClient);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(this.getDescription().formatted(Formatting.GRAY));
    }

    public MutableText getDescription() {
        return Text.translatable(this.getTranslationKey() + ".desc");
    }

    protected boolean canPlaceOn(PlayerEntity player, Direction side, ItemStack stack, BlockPos pos) {
        return !player.world.isOutOfHeightLimit(pos) && player.canPlaceOn(pos, side, stack);
    }
}
