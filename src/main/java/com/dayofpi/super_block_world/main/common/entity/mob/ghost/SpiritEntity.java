package com.dayofpi.super_block_world.main.common.entity.mob.ghost;

import com.dayofpi.super_block_world.main.Client;
import com.dayofpi.super_block_world.main.registry.main.ItemInit;
import com.dayofpi.super_block_world.main.util.entity.CustomSpawnPacket;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;

public class SpiritEntity extends Entity {
    private int lifeTime;

    public SpiritEntity(EntityType<? extends SpiritEntity> type, World world, double x, double y, double z) {
        super(type, world);
        this.setPosition(x, y, z);
    }

    public SpiritEntity(EntityType<SpiritEntity> type, World world) {
        super(type, world);
    }

    public boolean collides() {
        return true;
    }

    public void tick() {
        this.setVelocity(0.0D, 1.0D, 0.0D);

        ++this.lifeTime;
        if (this.lifeTime >= 100) {
            this.discard();
        }
        if (this.world.isClient) {
            Direction direction = Direction.random(random);
            double d = direction.getOffsetX() == 0 ? random.nextDouble() : (double)direction.getOffsetX() * 0.3D;
            double e = direction.getOffsetY() == 0 ? random.nextDouble() : (double)direction.getOffsetY() * 0.3D;
            double f = direction.getOffsetZ() == 0 ? random.nextDouble() : (double)direction.getOffsetZ() * 0.3D;
            world.addParticle(ParticleTypes.SOUL_FIRE_FLAME, this.getX() + d, this.getY() + e, this.getZ() + f, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.LARGE_SMOKE, this.getX() + d, this.getY() + e, this.getZ() + f, 0.0D, 0.0D, 0.0D);
            world.addParticle(ParticleTypes.ENCHANTED_HIT, this.getX() + d, this.getY() + e, this.getZ() + f, 0.0D, 0.0D, 0.0D);
        }
        super.tick();
    }

    public final ActionResult interact(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if (itemStack.isOf(Items.GLASS_BOTTLE)) {
            player.playSound(SoundEvents.ITEM_BOTTLE_FILL_DRAGONBREATH, 1.0F, 1.0F);
            ItemStack itemStack2 = ItemUsage.exchangeStack(itemStack, player, ItemInit.BOTTLED_GHOST.getDefaultStack());
            player.setStackInHand(hand, itemStack2);
            this.discard();
            return ActionResult.success(this.world.isClient);
        } else {
            return ActionResult.PASS;
        }
    }

    @Override
    protected void initDataTracker() {
    }

    public void readCustomDataFromNbt(NbtCompound tag) {
        this.lifeTime = tag.getInt("Lifetime");
    }

    public void writeCustomDataToNbt(NbtCompound tag) {
        tag.putInt("Lifetime", this.lifeTime);
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return CustomSpawnPacket.create(this, Client.PacketID);
    }
}
