package com.dayofpi.sbw_main.entity.types.bases;

import com.dayofpi.sbw_main.Client;
import com.dayofpi.sbw_main.item.registry.ModItems;
import com.dayofpi.sbw_main.misc.SpawnPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.world.World;

public abstract class AbstractShell extends Entity {

    public AbstractShell(EntityType<?> type, World world) {
        super(type, world);
    }

    public ItemStack getPickBlockStack() {
        return new ItemStack(this.asItem());
    }

    public Item asItem() {
        return ModItems.BUZZY_SHELL;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return SpawnPacket.create(this, Client.PacketID);
    }

}
