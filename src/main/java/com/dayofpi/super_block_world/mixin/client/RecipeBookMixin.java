package com.dayofpi.super_block_world.mixin.client;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.registry.ModBlocks;
import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.registry.ModTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.tag.ItemTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(value = EnvType.CLIENT)
@Mixin(ClientRecipeBook.class)
public abstract class RecipeBookMixin {

    private static boolean getBlockInStack(ItemStack itemStack, Block block) {
        return (itemStack.isOf(block.asItem()));
    }

    private static boolean isRedstone(ItemStack itemStack) {
        return itemStack.isIn(ItemTags.WOODEN_PRESSURE_PLATES) || itemStack.isIn(ItemTags.WOODEN_BUTTONS) || itemStack.isIn(ItemTags.WOODEN_DOORS) || itemStack.isIn(ItemTags.WOODEN_TRAPDOORS) || getBlockInStack(itemStack, ModBlocks.REDSTONE_TRAMPOLINE) || getBlockInStack(itemStack, ModBlocks.JUMP_BLOCK) || getBlockInStack(itemStack, ModBlocks.DONUT_BLOCK) || getBlockInStack(itemStack, ModBlocks.P_SWITCH) || getBlockInStack(itemStack, ModBlocks.POW_BLOCK) || getBlockInStack(itemStack, ModBlocks.DOTTED_LINE_BLOCK) || getBlockInStack(itemStack, ModBlocks.RED_DOTTED_LINE_BLOCK) || getBlockInStack(itemStack, ModBlocks.EXCLAMATION_BLOCK) || getBlockInStack(itemStack, ModBlocks.PULL_BLOCK) || getBlockInStack(itemStack, ModBlocks.ON_OFF_SWITCH);
    }

    private static boolean isDecoration(ItemStack itemStack) {
        BlockState state = Block.getBlockFromItem(itemStack.getItem()).getDefaultState();
        if (state.isIn(ModTags.FLAGS) || state.isIn(ModTags.PIPE_BODIES) || state.isIn(ModTags.WARP_PIPES))
            return true;
        if (itemStack.isOf(ModItems.COIN) || itemStack.isOf(ModItems.STAR_COIN))
            return true;
        else
            return getBlockInStack(itemStack, ModBlocks.DASH_BLOCK) || getBlockInStack(itemStack, ModBlocks.GLOW_BLOCK) || getBlockInStack(itemStack, ModBlocks.STAR_PANEL) || getBlockInStack(itemStack, ModBlocks.STONE_TORCH) || getBlockInStack(itemStack, ModBlocks.BOO_LANTERN) || getBlockInStack(itemStack, ModBlocks.CHINCHO_TORCH) || getBlockInStack(itemStack, ModBlocks.STRAWBERRY_CORAL) || getBlockInStack(itemStack, ModBlocks.GIRDER) || getBlockInStack(itemStack, ModBlocks.TRAMPOLINE) || getBlockInStack(itemStack, ModBlocks.FLAGPOLE);
    }

    @Inject(method = "getGroupForRecipe", at = @At("HEAD"), cancellable = true)
    private static void getGroupForRecipe(Recipe<?> recipe, CallbackInfoReturnable<RecipeBookGroup> cir) {
        ItemStack itemStack = recipe.getOutput();
        ItemGroup itemGroup = itemStack.getItem().getGroup();
        RecipeType<?> recipeType = recipe.getType();

        if (itemGroup != Main.ITEM_GROUP) {
            return;
        }

        if (recipeType == RecipeType.CRAFTING) {
            if (itemStack.isOf(ModItems.PRINCESS_CROWN) || itemStack.isOf(ModItems.JUMP_BOOTS) || itemStack.isOf(ModItems.CLOUD_BOOTS) || itemStack.isIn(ModTags.SHELLMETS) || itemStack.isOf(ModItems.BOMB) || itemStack.isOf(ModItems.HAMMER))
                cir.setReturnValue(RecipeBookGroup.CRAFTING_EQUIPMENT);
            if (isRedstone(itemStack)) {
                cir.setReturnValue(RecipeBookGroup.CRAFTING_REDSTONE);
                cir.cancel();
            } else if (itemStack.getItem() instanceof BlockItem && !isDecoration(itemStack)) {
                cir.setReturnValue(RecipeBookGroup.CRAFTING_BUILDING_BLOCKS);
                cir.cancel();
            }
        }
    }
}
