package com.dayofpi.super_block_world.main;

import com.dayofpi.super_block_world.client.BlockRendering;
import com.dayofpi.super_block_world.client.GlobalReceivers;
import com.dayofpi.super_block_world.client.entity.EntityRendering;
import com.dayofpi.super_block_world.client.FluidRendering;
import com.dayofpi.super_block_world.client.ParticleRendering;
import com.dayofpi.super_block_world.main.util.entity.CustomSpawnPacket;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;
@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {
    public static final Identifier PacketID = new Identifier(Main.MOD_ID, "spawn_packet");

    @Override
    public void onInitializeClient() {
        GlobalReceivers.registerGlobalReceivers();
        FluidRendering.renderFluids();
        BlockRendering.setBlockColors();
        BlockRendering.setRenderLayers();
        EntityRendering.registerEntityRenderers();
        ParticleRendering.renderParticles();
        receiveEntityPacket();
    }

    public void receiveEntityPacket() {
        ClientSidePacketRegistry.INSTANCE.register(PacketID, (ctx, byteBuf) -> {
            EntityType<?> et = Registry.ENTITY_TYPE.get(byteBuf.readVarInt());
            UUID uuid = byteBuf.readUuid();
            int entityId = byteBuf.readVarInt();
            Vec3d pos = CustomSpawnPacket.PacketBufUtil.readVec3d(byteBuf);
            float pitch = CustomSpawnPacket.PacketBufUtil.readAngle(byteBuf);
            float yaw = CustomSpawnPacket.PacketBufUtil.readAngle(byteBuf);
            ctx.getTaskQueue().execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity e = et.create(MinecraftClient.getInstance().world);
                if (e == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(et) + "\"!");
                e.updateTrackedPosition(pos);
                e.setPos(pos.x, pos.y, pos.z);
                e.setPitch(pitch);
                e.setYaw(yaw);
                e.setId(entityId);
                e.setUuid(uuid);
                MinecraftClient.getInstance().world.addEntity(entityId, e);
            });
        });
    }}
