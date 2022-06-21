/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.super_block_world.common.block_entities;

import com.dayofpi.super_block_world.common.blocks.QuestionBoxBlock;
import com.dayofpi.super_block_world.registry.ModBlockEntities;
import com.dayofpi.super_block_world.registry.ModTags;
import com.dayofpi.super_block_world.util.EnumUtil;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapIcon;
import net.minecraft.item.map.MapState;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class QuestionBoxBE
        extends LootableContainerBlockEntity {
    private final ViewerCountManager stateManager = new ViewerCountManager() {

        @Override
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
            QuestionBoxBE.this.playSound(state, SoundEvents.BLOCK_BARREL_OPEN);
            QuestionBoxBE.this.setOpen(state, true);
        }

        @Override
        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
            QuestionBoxBE.this.playSound(state, SoundEvents.BLOCK_BARREL_CLOSE);
            QuestionBoxBE.this.setOpen(state, false);
        }

        @Override
        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {
        }

        @Override
        protected boolean isPlayerViewing(PlayerEntity player) {
            if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                Inventory inventory = ((GenericContainerScreenHandler) player.currentScreenHandler).getInventory();
                return inventory == QuestionBoxBE.this;
            }
            return false;
        }
    };
    private DefaultedList<ItemStack> inventory = DefaultedList.ofSize(27, ItemStack.EMPTY);

    public QuestionBoxBE(BlockPos pos, BlockState state) {
        super(ModBlockEntities.QUESTION_BOX, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        if (!this.serializeLootTable(nbt)) {
            Inventories.writeNbt(nbt, this.inventory);
        }
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        if (!this.deserializeLootTable(nbt)) {
            Inventories.readNbt(nbt, this.inventory);
        }
    }

    public void checkLootInteraction(@Nullable PlayerEntity player) {
        if (this.world == null)
            return;
        if (this.lootTableId != null && this.world.getServer() != null) {
            LootTable lootTable = this.world.getServer().getLootManager().getTable(this.lootTableId);
            if (player instanceof ServerPlayerEntity) {
                Criteria.PLAYER_GENERATES_CONTAINER_LOOT.trigger((ServerPlayerEntity) player, this.lootTableId);
            }
            this.lootTableId = null;
            LootContext.Builder builder = new LootContext.Builder((ServerWorld) this.world).parameter(LootContextParameters.ORIGIN, Vec3d.ofCenter(this.pos)).random(this.lootTableSeed);
            if (player != null) {
                builder.luck(player.getLuck()).parameter(LootContextParameters.THIS_ENTITY, player);
            }
            lootTable.supplyInventory(this, builder.build(LootContextTypes.CHEST));
            this.tryInsertingMap(this);
        }
    }

    private void tryInsertingMap(QuestionBoxBE blockEntity) {
        if (!(this.world instanceof ServerWorld serverWorld)) {
            return;
        }
        BlockPos blockPos;
        String nameKey = "map.super_block_world.boss_arena";
        MapIcon.Type iconType = EnumUtil.BOSS_ARENA;

        if (serverWorld.random.nextInt(5) == 0) {
            blockPos = serverWorld.locateStructure(ModTags.ON_YOSHIS_ISLAND_MAP, blockEntity.getPos(), 100, false);
            nameKey = "map.super_block_world.yoshi";
            iconType = EnumUtil.YOSHI;
        } else {
            blockPos = serverWorld.locateStructure(ModTags.BOSS_ARENAS, blockEntity.getPos(), 100, true);
        }

        if (blockPos != null) {
            ItemStack itemStack = FilledMapItem.createMap(serverWorld, blockPos.getX(), blockPos.getZ(), (byte) 2, true, true);
            FilledMapItem.fillExplorationMap(serverWorld, itemStack);
            MapState.addDecorationsNbt(itemStack, blockPos, "+", iconType);
            itemStack.setCustomName(Text.translatable(nameKey));
            for (int i = 0; i < blockEntity.size(); ++i) {
                if (blockEntity.getStack(i).isEmpty()) {
                    blockEntity.setStack(i, itemStack);
                    break;
                }
            }
        }
    }

    @Override
    public int size() {
        return 27;
    }

    @Override
    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("container.super_block_world.question_box");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return GenericContainerScreenHandler.createGeneric9x3(syncId, playerInventory, this);
    }

    @Override
    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    @Override
    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
        if (this.inventory.stream().allMatch(ItemStack::isEmpty) && this.world != null && this.world.getBlockState(pos).get(QuestionBoxBlock.TEMPORARY)) {
            world.breakBlock(this.getPos(), false);
        }
    }

    public void tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    void setOpen(BlockState state, boolean open) {
        if (this.world != null)
            this.world.setBlockState(this.getPos(), state.with(QuestionBoxBlock.OPEN, open), Block.NOTIFY_ALL);
    }

    void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = state.get(QuestionBoxBlock.FACING).getVector();
        double x = (double) this.pos.getX() + 0.5 + (double) vec3i.getX() / 2.0;
        double y = (double) this.pos.getY() + 0.5 + (double) vec3i.getY() / 2.0;
        double z = (double) this.pos.getZ() + 0.5 + (double) vec3i.getZ() / 2.0;
        if (this.world != null)
            this.world.playSound(null, x, y, z, soundEvent, SoundCategory.BLOCKS, 0.5f, this.world.random.nextFloat() * 0.1f + 0.9f);
    }
}

