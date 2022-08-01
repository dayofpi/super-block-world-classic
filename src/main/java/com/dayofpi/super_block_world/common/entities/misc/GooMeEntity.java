package com.dayofpi.super_block_world.common.entities.misc;

import com.dayofpi.super_block_world.registry.ModItems;
import com.dayofpi.super_block_world.util.FormManager;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class GooMeEntity extends TameableEntity {
    @Nullable
    private PlayerListEntry cachedScoreboardEntry;
    public GooMeEntity(EntityType<? extends TameableEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createGooMeAttributes() {
        return MobEntity.createMobAttributes();
    }

    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(ModItems.GOO_ME);
    }

    @Override
    public void kill() {
        if (this.getOwner() instanceof PlayerEntity) {
            this.unFreakyFriday((PlayerEntity) this.getOwner());
        }
        this.remove(Entity.RemovalReason.KILLED);
        this.emitGameEvent(GameEvent.ENTITY_DIE);
    }

    private void unFreakyFriday(PlayerEntity entity) {
        entity.setPosition(this.getPos());
        entity.getDataTracker().set(FormManager.GOO_ME_UUID, Optional.empty());
        this.setOwnerUuid(null);
    }

    @Override
    public boolean canBeLeashedBy(PlayerEntity player) {
        return false;
    }

    @Nullable
    protected PlayerListEntry getPlayerListEntry() {
        ClientPlayNetworkHandler networkHandler = MinecraftClient.getInstance().getNetworkHandler();
        if (this.cachedScoreboardEntry == null && networkHandler != null) {
            this.cachedScoreboardEntry = networkHandler.getPlayerListEntry(this.getUuid());
        }
        return this.cachedScoreboardEntry;
    }

    @Nullable
    public Identifier getSkinTexture() {
        if (this.getOwnerUuid() == null)
            return null;
        PlayerListEntry playerListEntry = this.getPlayerListEntry();
        return playerListEntry == null ? DefaultSkinHelper.getTexture(this.getOwnerUuid()) : playerListEntry.getSkinTexture();
    }

    @Override
    public void setOwnerUuid(@Nullable UUID uuid) {
        super.setOwnerUuid(uuid);
        this.playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, 1.0F);
        this.spawnBreakParticles(this);
        this.setCustomName(this.getOwner() == null ? null : this.getOwner().getName());
    }

    private void swap(PlayerEntity player, Vec3d blockPos, Vec3d playerPos) {
        float health = this.getHealth();
        float playerHealth = player.getHealth();
        this.spawnBreakParticles(player);
        this.setPosition(playerPos);
        player.setPosition(blockPos);
        player.setHealth(health);
        this.setHealth(playerHealth);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.isAlive()) {
            return ActionResult.PASS;
        }
        Vec3d blockPos = this.getPos();
        Vec3d playerPos = player.getPos();

        if (this.isTamed() && this.isOwner(player)) {
            this.setTamed(false);
            this.setOwnerUuid(null);
            player.getDataTracker().set(FormManager.GOO_ME_UUID, Optional.empty());
            this.swap(player, blockPos, playerPos);
            return ActionResult.success(this.world.isClient);
        }
        else if (!this.isTamed() && player.getDataTracker().get(FormManager.GOO_ME_UUID).isEmpty()) {
            this.setOwner(player);
            player.getDataTracker().set(FormManager.GOO_ME_UUID, Optional.of(this.getUuid()));
            this.swap(player, blockPos, playerPos);
            return ActionResult.success(this.world.isClient);
        }
        return ActionResult.PASS;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_SLIME_BLOCK_HIT;
    }

    @Override
    public void onDeath(DamageSource damageSource) {
        super.onDeath(damageSource);
        if (this.getOwner() instanceof PlayerEntity) {
            this.unFreakyFriday((PlayerEntity) this.getOwner());
        }
        this.spawnBreakParticles(this);
        this.breakAndDrop();
    }

    private void spawnBreakParticles(Entity entity) {
        if (this.world instanceof ServerWorld) {
            ((ServerWorld)this.world).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.SLIME_BLOCK.getDefaultState()), entity.getX(), entity.getBodyY(0.6666666666666666), entity.getZ(), 10, entity.getWidth() / 4.0f, entity.getHeight() / 4.0f, entity.getWidth() / 4.0f, 0.05);
        }
    }

    private void breakAndDrop() {
        Block.dropStack(this.world, this.getBlockPos(), new ItemStack(ModItems.GOO_ME));
        this.world.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.BLOCK_SLIME_BLOCK_BREAK, this.getSoundCategory(), 1.0f, 1.0f);
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (FormManager.isGooMeImmuneTo(source))
            return false;
        if (source.isSourceCreativePlayer()) {
            this.kill();
            this.spawnBreakParticles(this);
        }
        amount = amount * 2.0f;
        return super.damage(source, amount);
    }

    @Override
    public boolean hurtByWater() {
        return true;
    }

    @Nullable
    @Override
    public PassiveEntity createChild(ServerWorld world, PassiveEntity entity) {
        return null;
    }
}
