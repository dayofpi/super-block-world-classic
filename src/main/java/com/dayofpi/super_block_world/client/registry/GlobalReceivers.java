package com.dayofpi.super_block_world.client.registry;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.common.blocks.BrickBlock;
import com.dayofpi.super_block_world.common.blocks.FakeBlock;
import com.dayofpi.super_block_world.common.blocks.ReactiveBlock;
import com.dayofpi.super_block_world.common.entities.hostile.FakeBlockEntity;
import com.dayofpi.super_block_world.registry.ModEntities;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.List;

public class GlobalReceivers {
    public static final Identifier FAKE_BLOCK_STATE = new Identifier(Main.MOD_ID, "fake_block_state");
    public static final Identifier FAKE_BLOCK_EVENT = new Identifier(Main.MOD_ID, "fake_block_event");

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(FAKE_BLOCK_EVENT, (server, player, handler, buf, responseSender) -> {
            int entityId = buf.readInt();
            Identifier worldId = buf.readIdentifier();

            ServerWorld serverWorld = server.getWorld(RegistryKey.of(Registry.WORLD_KEY, worldId));

            if (serverWorld != null)
                server.execute(() -> {
                    for (FakeBlockEntity entity : serverWorld.getEntitiesByType(ModEntities.FAKE_BLOCK, EntityPredicates.VALID_LIVING_ENTITY)) {
                        if (entity.getId() != entityId)
                            continue;
                        entity.setAttackCooldown(70);
                        entity.playSound(Sounds.ENTITY_GENERIC_TAIL_ATTACK, 1.0F, entity.getSoundPitch());
                        World world = entity.getWorld();
                        for (BlockPos blockPos : BlockPos.iterateOutwards(entity.getBlockPos(), 1, 0, 1)) {
                            List<Entity> list = world.getOtherEntities(entity, entity.getBoundingBox().expand(1, 0, 1), Entity::isPlayer);
                            BlockState state = world.getBlockState(blockPos);
                            if (!list.isEmpty()) {
                                list.forEach(entity1 -> entity1.damage(DamageSource.mob(entity), (float) entity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE)));
                            }
                            Block block = state.getBlock();
                            if (block instanceof BrickBlock || block instanceof FakeBlock) {
                                world.breakBlock(blockPos, true);
                            } else if (block instanceof ReactiveBlock reactiveBlock) {
                                reactiveBlock.react(world, blockPos, entity);
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
                    for (Entity entity : client.world.getEntities()) {
                        if (entity.getId() != entityId)
                            continue;

                        ((FakeBlockEntity) entity).setTwirling(newState);
                        break;
                    }
            });
        }));

    }
}
