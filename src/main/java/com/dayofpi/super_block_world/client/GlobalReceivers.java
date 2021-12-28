package com.dayofpi.super_block_world.client;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.entity.mob.FakeBlockEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public class GlobalReceivers {
    public static final Identifier FAKE_BLOCK_STATE = new Identifier(Main.MOD_ID, "fake_block_state");
    public static void registerGlobalReceivers() {
        ClientPlayNetworking.registerGlobalReceiver(FAKE_BLOCK_STATE, ((client, handler, buf, responseSender) -> {
            int entityId = buf.readInt();
            //State
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
