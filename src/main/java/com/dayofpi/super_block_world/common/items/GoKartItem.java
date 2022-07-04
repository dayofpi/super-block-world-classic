package com.dayofpi.super_block_world.common.items;

import com.dayofpi.super_block_world.common.entities.misc.GoKartEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class GoKartItem extends Item {
    public static final String COLOR_KEY = "Color";
    private static final Predicate<Entity> RIDERS = EntityPredicates.EXCEPT_SPECTATOR.and(Entity::collides);

    public GoKartItem(FabricItemSettings settings) {
        super(settings);
    }

    public static int getKartColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound != null)
            return nbtCompound.getInt(COLOR_KEY);
        return 0;
    }

    public static void setColor(ItemStack stack, int color) {
        NbtCompound nbtCompound = stack.getOrCreateNbt();
        nbtCompound.putInt(COLOR_KEY, color);
    }

    @Override
    public ItemStack getDefaultStack() {
        ItemStack itemStack = new ItemStack(this);
        GoKartItem.setColor(itemStack, 0);
        return itemStack;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        NbtCompound nbtCompound = stack.getNbt();
        if (nbtCompound == null) {
            return;
        }
        int id = nbtCompound.getByte(COLOR_KEY);
        if (nbtCompound.contains(COLOR_KEY, NbtElement.INT_TYPE)) {
            tooltip.add(Text.translatable("color.minecraft." + DyeColor.byId(id).getName()).formatted(Formatting.GRAY));
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        BlockHitResult hitResult = GoKartItem.raycast(world, user, RaycastContext.FluidHandling.NONE);
        if (hitResult.getType() == HitResult.Type.MISS) {
            return TypedActionResult.pass(itemStack);
        }

        Vec3d vec3d = user.getRotationVec(1.0f);
        List<Entity> list = world.getOtherEntities(user, user.getBoundingBox().stretch(vec3d.multiply(5.0)).expand(1.0), RIDERS);
        if (!list.isEmpty()) {
            Vec3d vec3d2 = user.getEyePos();
            for (Entity entity : list) {
                Box box = entity.getBoundingBox().expand(entity.getTargetingMargin());
                if (!box.contains(vec3d2)) continue;
                return TypedActionResult.pass(itemStack);
            }
        }
        if (((HitResult)hitResult).getType() == HitResult.Type.BLOCK) {
            GoKartEntity goKartEntity = this.createEntity(world, hitResult);
            goKartEntity.setKartColor(DyeColor.byId(getKartColor(itemStack)));
            goKartEntity.setYaw(user.getYaw());
            if (!world.isSpaceEmpty(goKartEntity, goKartEntity.getBoundingBox())) {
                return TypedActionResult.fail(itemStack);
            }
            if (!world.isClient) {
                world.spawnEntity(goKartEntity);
                world.emitGameEvent(user, GameEvent.ENTITY_PLACE, hitResult.getPos());
                if (!user.getAbilities().creativeMode) {
                    itemStack.decrement(1);
                }
            }
            user.incrementStat(Stats.USED.getOrCreateStat(this));
            return TypedActionResult.success(itemStack, world.isClient());
        }
        return TypedActionResult.pass(itemStack);
    }

    private GoKartEntity createEntity(World world, HitResult hitResult) {
        return new GoKartEntity(world, hitResult.getPos().x, hitResult.getPos().y, hitResult.getPos().z);
    }
}
