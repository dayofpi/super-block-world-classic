package com.dayofpi.sbw_main.block.types.template;

import com.dayofpi.sbw_main.block.block_entity.ModSignBE;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.SignType;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModSignTemplate extends AbstractSignBlock {
    public ModSignTemplate(Settings settings, SignType signType) {
        super(settings, signType);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ModSignBE(pos, state);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        Item item = itemStack.getItem();
        boolean bl = item instanceof DyeItem;
        boolean bl2 = itemStack.isOf(Items.GLOW_INK_SAC);
        boolean bl3 = itemStack.isOf(Items.INK_SAC);
        boolean bl4 = (bl2 || bl || bl3) && player.getAbilities().allowModifyWorld;
        if (world.isClient) {
            return bl4 ? ActionResult.SUCCESS : ActionResult.CONSUME;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (!(blockEntity instanceof ModSignBE signBE)) {
                return ActionResult.PASS;
            } else {
                boolean bl5 = signBE.isGlowingText();
                if ((!bl2 || !bl5) && (!bl3 || bl5)) {
                    if (bl4) {
                        boolean bl8;
                        if (bl2) {
                            world.playSound(null, pos, SoundEvents.ITEM_GLOW_INK_SAC_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            bl8 = signBE.setGlowingText(true);
                            if (player instanceof ServerPlayerEntity) {
                                Criteria.ITEM_USED_ON_BLOCK.trigger((ServerPlayerEntity) player, pos, itemStack);
                            }
                        } else if (bl3) {
                            world.playSound(null, pos, SoundEvents.ITEM_INK_SAC_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            bl8 = signBE.setGlowingText(false);
                        } else {
                            world.playSound(null, pos, SoundEvents.ITEM_DYE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                            bl8 = signBE.setTextColor(((DyeItem) item).getColor());
                        }

                        if (bl8) {
                            if (!player.isCreative()) {
                                itemStack.decrement(1);
                            }

                            player.incrementStat(Stats.USED.getOrCreateStat(item));
                        }
                    }

                    return signBE.onActivate((ServerPlayerEntity) player) ? ActionResult.SUCCESS : ActionResult.PASS;
                } else {
                    return ActionResult.PASS;
                }
            }
        }
    }
}
