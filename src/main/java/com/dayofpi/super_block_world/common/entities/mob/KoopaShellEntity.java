package com.dayofpi.super_block_world.common.entities.mob;

import com.dayofpi.super_block_world.common.entities.abst.AbstractShell;
import com.dayofpi.super_block_world.registry.main.EntityInit;
import com.dayofpi.super_block_world.registry.main.ItemInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.item.Item;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;

public class KoopaShellEntity extends AbstractShell {
    private static final TrackedData<Integer> TYPE;

    static {
        TYPE = DataTracker.registerData(KoopaShellEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }
    public KoopaShellEntity(EntityType<? extends AbstractShell> entityType, World world) {
        super(entityType, world);
    }


    protected Item getShellItem() {
        return this.getKoopaType() == 0 ? ItemInit.GREEN_SHELL : ItemInit.RED_SHELL;
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(TYPE, 0);
    }

    public int getKoopaType() {
        return this.dataTracker.get(TYPE);
    }

    public void setKoopaType(int color) {
        this.dataTracker.set(TYPE, color);
    }

    @Override
    protected void leaveShell() {
        KoopaEntity koopaEntity = this.convertTo(EntityInit.KOOPA_TROOPA, true);
        if (koopaEntity != null)
            koopaEntity.setKoopaType(this.getKoopaType());
    }

    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Type", this.getKoopaType());
        nbt.putBoolean("HasMob", this.hasMob());
        nbt.putBoolean("Shaking", this.isShaking());
    }

    public void readCustomDataFromNbt(NbtCompound nbt) {
        this.setKoopaType(nbt.getInt("Type"));
        this.setHasMob(nbt.getBoolean("HasMob"));
        this.setShaking(nbt.getBoolean("Shaking"));
        super.readCustomDataFromNbt(nbt);
    }
}
