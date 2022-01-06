package com.dayofpi.super_block_world.client;

import com.dayofpi.super_block_world.client.sound.SoundInit;
import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.block.item_block.ReactiveBlock;
import com.dayofpi.super_block_world.main.common.entity.mob.FakeBlockEntity;
import com.dayofpi.super_block_world.main.registry.main.EntityInit;
import com.dayofpi.super_block_world.main.registry.main.TagInit;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.List;

public class GlobalReceivers {
    public static final Identifier FAKE_BLOCK_STATE = new Identifier(Main.MOD_ID, "fake_block_state");
    public static final Identifier FAKE_BLOCK_EVENT = new Identifier(Main.MOD_ID, "fake_block_event");
    public static void registerGlobalReceivers() {
        ServerPlayNetworking.registerGlobalReceiver(FAKE_BLOCK_EVENT, (server, player, handler, buf, responseSender) -> {
            int entityId = buf.readInt();
            Identifier worldId = buf.readIdentifier();

            server.execute(() -> {
                for(Entity entity : server.getWorld(RegistryKey.of(Registry.WORLD_KEY, worldId)).getEntitiesByType(EntityInit.FAKE_BLOCK, EntityPredicates.VALID_LIVING_ENTITY)) {
                    if (entity.getId() != entityId)
                        continue;

                    entity.playSound(SoundInit.ENTITY_MISC_TAIL_ATTACK, 1.0F, 1.0F);
                    World world = entity.getWorld();
                    for (BlockPos blockPos : BlockPos.iterateOutwards(entity.getBlockPos(), 1, 0, 1)) {
                        List<Entity> list = world.getOtherEntities(entity, entity.getBoundingBox().expand(1, 0, 1), Entity::isPlayer);
                        BlockState state = world.getBlockState(blockPos);
                        if (!list.isEmpty()) {
                            list.forEach(entity1 -> entity1.damage(DamageSource.mob((LivingEntity) entity), (float) ((LivingEntity) entity).getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
                        }
                        if (state.isIn(TagInit.BRICKS)) {
                            world.breakBlock(blockPos, true);
                        } else if (state.getBlock() instanceof ReactiveBlock reactiveBlock) {
                            reactiveBlock.activate(state, world, blockPos);
                        }
                    }
                    break;
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(FAKE_BLOCK_STATE, ((client, handler, buf, responseSender) -> {
            int entityId = buf.readInt();
            boolean newState = buf.readBoolean();

            client.execute(() -> {
                //Find entity by id
                if (client.world != null)
                for(Entity entity : client.world.getEntities()) {
                    if (entity.getId() != entityId)
                        continue;

                    ((FakeBlockEntity)entity).setTwirling(newState);
                    break;
                }
            });
        }));

    }
}
