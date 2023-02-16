package com.dayofpi.super_block_world.entity.entities.misc;

import com.dayofpi.super_block_world.audio.Sounds;
import com.dayofpi.super_block_world.block.ModBlocks;
import com.dayofpi.super_block_world.entity.ModEntities;
import com.dayofpi.super_block_world.item.ModItems;
import com.dayofpi.super_block_world.util.EnumAddons;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.List;

public class TrampolineMinecartEntity extends TntMinecartEntity {
    private static final TrackedData<Boolean> SQUASH = DataTracker.registerData(TrampolineMinecartEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    public TrampolineMinecartEntity(EntityType<? extends TntMinecartEntity> entityType, World world) {
        super(entityType, world);
    }

    public TrampolineMinecartEntity(World world, double x, double y, double z) {
        this(ModEntities.TRAMPOLINE_MINECART, world);
        this.setPosition(x, y, z);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(SQUASH, false);
    }

    @Override
    public void tick() {
        super.tick();
        Box box = this.getBoundingBox().contract(0.4D, 0.0D, 0.4D).offset(0.0D, 0.2D, 0.02D);
        List<Entity> list = world.getOtherEntities(this, box, EntityPredicates.EXCEPT_SPECTATOR);
        for (Entity entity : list) {
            if (this.isSquashed())
                this.bounce(entity, 1.2);
            else this.bounce(entity, 0.8);
        }
    }

    @Override
    protected void moveOnRail(BlockPos pos, BlockState state) {
        super.moveOnRail(pos, state);
        if (!state.isOf(Blocks.ACTIVATOR_RAIL))
            this.setSquashed(false);
    }

    @Override
    protected void moveOffRail() {
        super.moveOffRail();
        this.setSquashed(false);
    }

    protected void bounce(Entity entity, double strength) {
        Vec3d vec3d = entity.getVelocity();
        entity.setVelocity(vec3d.x, strength, vec3d.z);
        this.playSound(Sounds.BLOCK_TRAMPOLINE_RELEASE, 1.0F, (float) strength + 0.2F);
    }

    public boolean isSquashed() {
        return this.getDataTracker().get(SQUASH);
    }

    public void setSquashed(boolean present) {
        this.getDataTracker().set(SQUASH, present);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        boolean bl;
        if (this.world.isClient || this.isRemoved()) {
            return true;
        }
        if (this.isInvulnerableTo(source)) {
            return false;
        }
        this.setDamageWobbleSide(-this.getDamageWobbleSide());
        this.setDamageWobbleTicks(10);
        this.scheduleVelocityUpdate();
        this.setDamageWobbleStrength(this.getDamageWobbleStrength() + amount * 10.0f);
        this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
        bl = source.getAttacker() instanceof PlayerEntity && ((PlayerEntity) source.getAttacker()).getAbilities().creativeMode;
        if (bl || this.getDamageWobbleStrength() > 40.0f) {
            this.removeAllPassengers();
            if (!bl || this.hasCustomName()) {
                this.dropItems(source);
            } else {
                this.discard();
            }
        }
        return true;
    }

    @Override
    public void onActivatorRail(int x, int y, int z, boolean powered) {
        this.setSquashed(powered);
    }

    public void prime() {
    }

    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if (this.hasPassengers()) {
            for (Entity entity : this.getPassengerList()) {
                entity.handleFallDamage(fallDistance, damageMultiplier, damageSource);
            }
        }
        return false;
    }

    public void dropItems(DamageSource damageSource) {
        this.kill();
        if (this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
            ItemStack itemStack = new ItemStack(this.getItem());
            if (this.hasCustomName()) {
                itemStack.setCustomName(this.getCustomName());
            }
            this.dropStack(itemStack);
        }
    }

    protected void explode(double velocity) {
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.setSquashed(nbt.getBoolean("Squashed"));
        if (nbt.getBoolean("CustomDisplayTile")) {
            this.setCustomBlock(NbtHelper.toBlockState(nbt.getCompound("DisplayState")));
            this.setCustomBlockOffset(nbt.getInt("DisplayOffset"));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putBoolean("Squashed", this.isSquashed());
        if (this.hasCustomBlock()) {
            nbt.putBoolean("CustomDisplayTile", true);
            nbt.put("DisplayState", NbtHelper.fromBlockState(this.getContainedBlock()));
            nbt.putInt("DisplayOffset", this.getBlockOffset());
        }
    }

    @Override
    public Type getMinecartType() {
        return EnumAddons.TRAMPOLINE;
    }

    @Override
    protected Item getItem() {
        return ModItems.TRAMPOLINE_MINECART;
    }

    @Override
    public BlockState getDefaultContainedBlock() {
        return ModBlocks.TRAMPOLINE.getDefaultState();
    }
}
