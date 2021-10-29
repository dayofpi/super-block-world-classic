package com.dayofpi.sbw_main.item.types;

import com.dayofpi.sbw_main.entity.types.AbstractShell;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;


public class ShellItem<T extends AbstractShell> extends ArmorItem {
    private final EntityType<T> shellEntity;

    public ShellItem(ArmorMaterial material, EntityType<T> entityType, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
        this.shellEntity = entityType;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.SOURCE_ONLY);
        if (hitResult.getType() != HitResult.Type.BLOCK) {
            return TypedActionResult.pass(itemStack);
        } else if (!(world instanceof ServerWorld)) {
            return TypedActionResult.success(itemStack);
        } else {
            BlockPos blockPos = hitResult.getBlockPos();
            EntityType<?> entityType = this.getEntityType(itemStack.getNbt());
            if (entityType.spawnFromItemStack((ServerWorld) world, itemStack, user, blockPos, SpawnReason.SPAWN_EGG, true, false) == null) {
                return TypedActionResult.pass(itemStack);
            } else {
                if (!user.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
                user.incrementStat(Stats.USED.getOrCreateStat(this));
                world.emitGameEvent(GameEvent.ENTITY_PLACE, user);
                return TypedActionResult.consume(itemStack);
            }
        }
    }

    public EntityType<?> getEntityType(@Nullable NbtCompound nbt) {
        if (nbt != null && nbt.contains("EntityTag", 10)) {
            NbtCompound nbtCompound = nbt.getCompound("EntityTag");
            if (nbtCompound.contains("id", 8)) {
                return EntityType.get(nbtCompound.getString("id")).orElse(this.shellEntity);
            }
        }

        return this.shellEntity;
    }
}
