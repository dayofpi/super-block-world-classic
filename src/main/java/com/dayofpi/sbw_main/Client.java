package com.dayofpi.sbw_main;

import com.dayofpi.sbw_main.block.registry.BlockClient;
import com.dayofpi.sbw_main.entity.registry.EntityClient;
import com.dayofpi.sbw_main.misc.SpawnPacket;
import com.dayofpi.sbw_main.world.fluid.FluidRendering;
import com.dayofpi.sbw_main.world.registry.ParticleClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.UUID;

@Environment(EnvType.CLIENT)
public class Client implements ClientModInitializer {
    public static final Identifier PacketID = new Identifier(Main.MOD_ID, "spawn_packet");

    @Override
    public void onInitializeClient() {
        FluidRendering.renderFluids();
        BlockClient.setRenderLayers();
        EntityClient.registerEntityRenderers();
        ParticleClient.particleRendering();
        receiveEntityPacket();
    }

    public void receiveEntityPacket() {
        ClientPlayNetworking.registerGlobalReceiver(PacketID, (client, handler, buf, responseSender) ->
        {
            EntityType<?> type = Registry.ENTITY_TYPE.get(buf.readVarInt());
            UUID uuid = buf.readUuid();
            int entityId = buf.readVarInt();
            Vec3d pos = SpawnPacket.PacketBufUtil.readVec3d(buf);
            float pitch = SpawnPacket.PacketBufUtil.readAngle(buf);
            float yaw = SpawnPacket.PacketBufUtil.readAngle(buf);
            client.execute(() -> {
                if (MinecraftClient.getInstance().world == null)
                    throw new IllegalStateException("Tried to spawn entity in a null world!");
                Entity entity = type.create(MinecraftClient.getInstance().world);
                if (entity == null)
                    throw new IllegalStateException("Failed to create instance of entity \"" + Registry.ENTITY_TYPE.getId(type) + "\"!");
                    entity.updateTrackedPosition(pos);
                    entity.setPos(pos.x, pos.y, pos.z);
                    entity.getPitch(pitch);
                    entity.getYaw(yaw);
                    entity.setId(entityId);
                    entity.setUuid(uuid);
                    MinecraftClient.getInstance().world.addEntity(entityId, entity);
            });
        });
    }
}
