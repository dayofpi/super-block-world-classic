package com.dayofpi.super_block_world.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class ModDamageSource extends DamageSource {
    public static final DamageSource POW = new ModDamageSource("super_block_world.pow");
    public static final DamageSource POISON = new ModDamageSource("super_block_world.poison");
    public static final DamageSource MUNCHER = new ModDamageSource("super_block_world.muncher");
    public static final DamageSource JELLYBEAM = new ModDamageSource("super_block_world.jellybeam").setBypassesArmor();
    public static final DamageSource SPIKES = new ModDamageSource("super_block_world.spikes");
    private Entity source;
    private boolean stomp;

    public ModDamageSource(String name, Entity source) {
        super(name);
        this.source = source;
    }

    protected ModDamageSource(String name) {
        super(name);
    }

    public static DamageSource stomp(LivingEntity attacker) {
        return new ModDamageSource("super_block_world.stomp", attacker).setStomp();
    }

    @Override
    public Entity getAttacker() {
        return this.source;
    }

    @Override
    public Text getDeathMessage(LivingEntity entity) {
        if (source == null)
            return super.getDeathMessage(entity);
        ItemStack itemStack = this.source instanceof LivingEntity ? ((LivingEntity) this.source).getMainHandStack() : ItemStack.EMPTY;
        String string = "death.attack." + this.name;
        if (!itemStack.isEmpty() && itemStack.hasCustomName()) {
            return Text.translatable(string + ".item", entity.getDisplayName(), this.source.getDisplayName(), itemStack.toHoverableText());
        }
        return Text.translatable(string, entity.getDisplayName(), this.source.getDisplayName());
    }

    @Override
    @Nullable
    public Vec3d getPosition() {
        if (source == null)
            return super.getPosition();
        return this.source.getPos();
    }

    @Override
    public boolean isScaledWithDifficulty() {
        return this.source instanceof LivingEntity && !(this.source instanceof PlayerEntity);
    }

    public boolean isStomp() {
        return this.stomp;
    }

    protected DamageSource setStomp() {
        this.stomp = true;
        return this;
    }
}
