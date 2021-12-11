package com.dayofpi.sbw_main.block.type;

import com.dayofpi.sbw_main.ModSounds;
import com.dayofpi.sbw_main.block.block_entity.QuestionBlockBE;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.type.template.ReactiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class QuestionBlock extends ReactiveBlock implements BlockEntityProvider {
    public QuestionBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new QuestionBlockBE(pos, state);
    }

    public static List<ItemStack> defaultItems(ServerWorld world, BlockState state, BlockPos blockPos) {
        LootContext lootContext = new LootContext.Builder(world).parameter(LootContextParameters.BLOCK_STATE, state).parameter(LootContextParameters.TOOL, ItemStack.EMPTY).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(blockPos)).build(LootContextTypes.BLOCK);
        ServerWorld serverWorld = lootContext.getWorld();

        LootTable lootTable = serverWorld.getServer().getLootManager().getTable(state.getBlock().getLootTableId());
        return lootTable.generateLoot(lootContext);
    }

    @Override
    public void activate(BlockState state, World world, BlockPos blockPos) {
        Inventory blockEntity = (Inventory) world.getBlockEntity(blockPos);
        BlockPos pos = blockPos.up();
        if (state.isSolidBlock(world, blockPos.up())) {
            if (!state.isSolidBlock(world, blockPos.down())) {
                pos = blockPos.down();
            }
        }
        if (!world.isClient) {
            if (blockEntity != null) {
                if (!blockEntity.getStack(0).isEmpty()) {
                    Block.dropStack(world, pos, blockEntity.getStack(0));
                    blockEntity.removeStack(0);
                } else {
                    Block.dropStack(world, pos, defaultItems((ServerWorld) world, state, blockPos).iterator().next());
                }
                world.setBlockState(blockPos, pushEntitiesUpBeforeBlockChange(state, ModBlocks.EMPTY_BLOCK.getDefaultState(), world, blockPos));
                world.playSound(null, blockPos, ModSounds.BLOCK_ITEM_BLOCK_HIT, SoundCategory.NEUTRAL, 2.0F, 1.0F);
                ParticleUtil.spawnParticle(world, blockPos, ParticleTypes.WAX_OFF, UniformIntProvider.create(2, 3));
            }
        }
    }
}
