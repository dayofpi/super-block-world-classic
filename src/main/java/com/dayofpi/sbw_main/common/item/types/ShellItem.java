package com.dayofpi.sbw_main.common.item.types;

import com.dayofpi.sbw_main.common.entity.type.bases.AbstractShell;
import com.dayofpi.sbw_main.registry.ModItems;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.List;


public class ShellItem extends ArmorItem {
    private final EntityType<? extends AbstractShell> shellEntity;
    public ShellItem(EntityType<? extends AbstractShell> shellEntity, ArmorMaterial material, EquipmentSlot slot, Settings settings) {
        super(material, slot, settings);
        this.shellEntity = shellEntity;
    }

    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add( new TranslatableText("tooltip.super_block_world.brick_break").formatted(Formatting.GOLD));
        if (stack.isOf(ModItems.BUZZY_SHELL)) {
            tooltip.add(new TranslatableText("tooltip.super_block_world.projectile_deflect").formatted(Formatting.GOLD));
        }
    }

    private EntityType<? extends AbstractShell> getShellEntity() {
        return this.shellEntity;
    }

    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        HitResult hitResult = raycast(world, user, RaycastContext.FluidHandling.NONE);
        ItemStack itemStack = user.getStackInHand(hand);
        if (hitResult.getType() == net.minecraft.util.hit.HitResult.Type.MISS) {
            return super.use(world, user, hand);
        } else {
            Vec3d vec3d = user.getRotationVec(1.0F);
            List<Entity> list = world.getOtherEntities(user, user.getBoundingBox().stretch(vec3d.multiply(5.0D)).expand(1.0D), EntityPredicates.EXCEPT_SPECTATOR.and(Entity::collides));
            if (!list.isEmpty()) {
                Vec3d vec3d2 = user.getEyePos();

                for (Entity entity : list) {
                    Box box = entity.getBoundingBox().expand((double) entity.getTargetingMargin());
                    if (box.contains(vec3d2)) {
                        return TypedActionResult.pass(itemStack);
                    }
                }
            }

            if (hitResult.getType() == net.minecraft.util.hit.HitResult.Type.BLOCK) {
                AbstractShell shell = this.getShellEntity().create(world);

                if (shell != null) {
                    shell.setActive(false);
                    shell.setPos(hitResult.getPos().x, hitResult.getPos().y + 0.08, hitResult.getPos().z);
                    shell.setYaw(user.getYaw() +180);
                    if (!world.isSpaceEmpty(shell, shell.getBoundingBox())) {
                        return TypedActionResult.fail(itemStack);
                    } else {
                        if (!world.isClient) {
                            world.spawnEntity(shell);
                            world.emitGameEvent(user, GameEvent.ENTITY_PLACE, new BlockPos(hitResult.getPos()));
                            if (!user.getAbilities().creativeMode) {
                                itemStack.decrement(1);
                            }
                        }

                        user.incrementStat(Stats.USED.getOrCreateStat(this));
                        return TypedActionResult.success(itemStack, world.isClient());
                    }
                } else return TypedActionResult.fail(itemStack);
            } else return TypedActionResult.pass(itemStack);
        }
    }
}
