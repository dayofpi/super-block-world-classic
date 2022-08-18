package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.block_entities.WarpPipeBE;
import com.dayofpi.super_block_world.common.blocks.WarpPipeBlock;
import com.mojang.logging.LogUtils;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.Optional;

public class WarpLinkItem extends Item {
    private static final String WARP_PIPE_POS_KEY = "WarpPipePos";
    private static final String WARP_PIPE_DIMENSION_KEY = "WarpPipeDimension";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final int MAX_DISTANCE = 140;

    public WarpLinkItem(Settings settings) {
        super(settings);
    }

    public static boolean isLinked(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        return nbtCompound != null && (nbtCompound.contains(WARP_PIPE_DIMENSION_KEY) || nbtCompound.contains(WARP_PIPE_POS_KEY));
    }

    private static Optional<RegistryKey<World>> getWarpPipeDimension(NbtCompound nbt) {
        return World.CODEC.parse(NbtOps.INSTANCE, nbt.get(WARP_PIPE_DIMENSION_KEY)).result();
    }

    @Nullable
    private static GlobalPos createWarpPipePos(NbtCompound nbt) {
        Optional<RegistryKey<World>> optional;
        boolean bl = nbt.contains(WARP_PIPE_POS_KEY);
        boolean bl2 = nbt.contains(WARP_PIPE_DIMENSION_KEY);
        if (bl && bl2 && (optional = WarpLinkItem.getWarpPipeDimension(nbt)).isPresent()) {
            BlockPos blockPos = NbtHelper.toBlockPos(nbt.getCompound(WARP_PIPE_POS_KEY));
            return GlobalPos.create(optional.get(), blockPos);
        }
        return null;
    }

    private void spawnParticles(World world, BlockPos blockPos) {
        if (world instanceof ServerWorld) {
            Random random = world.getRandom();

            for (int i = 0; i < 4; ++i) {
                ((ServerWorld) world).spawnParticles(ParticleTypes.POOF, blockPos.getX() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), blockPos.getY() + 0.5D, blockPos.getZ() + 0.5D + (0.5D * (random.nextBoolean() ? 1 : -1)), 1, 0.0D, 0.0D, 0.0D, 0.0D);
            }
        }
    }

    private ActionResult reset(World world, BlockPos blockPos, NbtCompound nbtCompound, boolean fromBreak) {
        this.playSound(world, blockPos, fromBreak ? Sounds.ITEM_WARP_LINK_BREAK : Sounds.ITEM_WARP_LINK_RESET);
        nbtCompound.remove(WARP_PIPE_POS_KEY);
        nbtCompound.remove(WARP_PIPE_DIMENSION_KEY);
        return ActionResult.success(world.isClient);
    }

    private void link(BlockPos blockPos, World world, NbtCompound nbtCompound, WarpPipeBE warpPipeBE, WarpPipeBE warpPipeBE1) {
        this.spawnParticles(world, blockPos);
        this.playSound(world, blockPos, Sounds.ITEM_WARP_LINK_END);
        warpPipeBE.setDestinationPos(warpPipeBE1.getPos());
        warpPipeBE1.setDestinationPos(blockPos);
        nbtCompound.remove(WARP_PIPE_POS_KEY);
        nbtCompound.remove(WARP_PIPE_DIMENSION_KEY);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient()) {
            return;
        }
        if (WarpLinkItem.isLinked(stack)) {
            NbtCompound nbtCompound = stack.getOrCreateNbt();
            GlobalPos globalPos = WarpLinkItem.createWarpPipePos(nbtCompound);
            if (globalPos == null || !(entity instanceof PlayerEntity)) return;
            boolean bl = ((PlayerEntity) entity).getOffHandStack() == stack;
            if (selected || bl) {
                if (!globalPos.getPos().isWithinDistance(entity.getPos(), MAX_DISTANCE)) {
                    Hand hand = Hand.MAIN_HAND;
                    if (bl)
                        hand = Hand.OFF_HAND;
                    Hand finalHand = hand;
                    stack.damage(2, (PlayerEntity) entity, p -> p.sendToolBreakStatus(finalHand));
                    this.reset(world, entity.getBlockPos(), nbtCompound, true);
                }
            }
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();
        World world = context.getWorld();
        ActionResult actionResult = super.useOnBlock(context);
        if (WarpPipeBlock.isLinkableWarpPipe(world, blockPos)) {
            ItemStack itemStack = context.getStack();
            if (WarpLinkItem.isLinked(itemStack)) {
                NbtCompound nbtCompound = itemStack.getOrCreateNbt();
                GlobalPos globalPos = WarpLinkItem.createWarpPipePos(nbtCompound);
                if (globalPos == null)
                    return actionResult;
                if (globalPos.getPos().equals(blockPos)) {
                    return this.reset(world, blockPos, nbtCompound, false);
                }
                BlockEntity blockEntity = world.getBlockEntity(blockPos);
                BlockEntity blockEntity1 = world.getBlockEntity(globalPos.getPos());
                if (blockEntity instanceof WarpPipeBE warpPipeBE && blockEntity1 instanceof WarpPipeBE warpPipeBE1) {
                    if (WarpPipeBlock.isLinkableWarpPipe(world, globalPos.getPos())) {
                        if (player != null)
                            itemStack.damage(1, player, p -> p.sendToolBreakStatus(context.getHand()));
                        this.link(blockPos, world, nbtCompound, warpPipeBE, warpPipeBE1);
                        return ActionResult.success(world.isClient);
                    }
                } else return this.reset(world, blockPos, nbtCompound, false);
            } else {
                this.playSound(world, blockPos, Sounds.ITEM_WARP_LINK_START);
                PlayerEntity playerEntity = context.getPlayer();
                if (playerEntity == null)
                    return actionResult;
                this.writeNbt(world.getRegistryKey(), blockPos, itemStack.getOrCreateNbt());
                return ActionResult.success(world.isClient);
            }
        }
        return actionResult;
    }

    private void playSound(World world, BlockPos blockPos, SoundEvent soundEvent) {
        world.playSound(null, blockPos, soundEvent, SoundCategory.PLAYERS, 1.0f, 1.0f);
    }

    private void writeNbt(RegistryKey<World> worldKey, BlockPos pos, NbtCompound nbt) {
        nbt.put(WARP_PIPE_POS_KEY, NbtHelper.fromBlockPos(pos));
        World.CODEC.encodeStart(NbtOps.INSTANCE, worldKey).resultOrPartial(LOGGER::error).ifPresent(nbtElement -> nbt.put(WARP_PIPE_DIMENSION_KEY, nbtElement));
    }
}
