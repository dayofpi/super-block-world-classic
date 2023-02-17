package com.dayofpi.super_block_world.entity.entities.hostile;

import com.dayofpi.super_block_world.entity.entities.boss.PeteyPiranhaEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class TweesterEntity extends Entity {
    private float lifeTime;
    private boolean persistent;

    public TweesterEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public boolean isPersistent() {
        return this.persistent;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isInsideWall())
            this.addVelocity(0.0, 0.2, 0.0);
        Vec3d velocity = this.getVelocity();
        this.setVelocity(velocity.multiply(1.2));
        double x = MathHelper.clamp(velocity.x, -1.2, 1.2);
        double z = MathHelper.clamp(velocity.z, -1.2, 1.2);
        this.move(MovementType.SELF, this.getVelocity());
        this.setVelocity(x, velocity.y, z);
        List<Entity> entities = world.getOtherEntities(null, this.getBoundingBox().expand(0.75), entity -> entity instanceof ItemEntity);
        if (!entities.isEmpty()) {
            for (Entity entity : entities) {
                if (entity instanceof ItemEntity && entities.indexOf(entity) < 100) {
                    Vec3d vec3d = new Vec3d((this.getX() + (entities.indexOf(entity) * 0.1)) - entity.getX(), (this.getY()) - entity.getY(), (this.getZ() + (entities.indexOf(entity) * 0.1)) - entity.getZ());
                    entity.setVelocity(vec3d.multiply(0.2));
                }
            }
        }
        if (!this.world.isClient) {
            if (!this.isPersistent()) {
                ++this.lifeTime;
                if (this.lifeTime >= 300) {
                    this.discard();
                }
            }
        }
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        super.pushAwayFrom(entity);
        if (entity instanceof PeteyPiranhaEntity)
            return;
        entity.addVelocity(0.0, 0.2, 0.0);
    }

    @Override
    public boolean canHit() {
        return true;
    }

    @Override
    protected void initDataTracker() {

    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.persistent = nbt.getBoolean("PersistenceRequired");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putBoolean("PersistenceRequired", this.persistent);
    }

    @Override
    public Packet<ClientPlayPacketListener> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this);
    }
}
