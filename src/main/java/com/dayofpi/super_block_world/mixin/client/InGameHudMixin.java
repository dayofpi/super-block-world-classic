package com.dayofpi.super_block_world.mixin.client;

import com.dayofpi.super_block_world.util.FormManager;
import com.dayofpi.super_block_world.util.PowerUp;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.world.GameMode;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin extends DrawableHelper {
    @Shadow
    @Final
    private MinecraftClient client;
    @Shadow
    private int scaledHeight;
    @Shadow
    private int scaledWidth;
    @Shadow
    @Final
    private ItemRenderer itemRenderer;

    @Shadow
    protected abstract PlayerEntity getCameraPlayer();

    @Inject(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;renderMountHealth(Lnet/minecraft/client/util/math/MatrixStack;)V"), method = "render")
    public void render(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        ClientPlayerInteractionManager interactionManager = this.client.interactionManager;
        if (this.client.interactionManager == null) return;
        if (!this.client.options.hudHidden && interactionManager.getCurrentGameMode() != GameMode.SPECTATOR) {
            RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            this.renderPowerBar(matrices);
        }
    }

    private String getPowerUp() {
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity != null) {
            return playerEntity.getDataTracker().get(FormManager.POWER_UP);
        }
        return null;
    }

    private void renderPowerBar(MatrixStack matrices) {
        String powerUp = this.getPowerUp();
        if (Objects.equals(powerUp, PowerUp.NONE.asString())) {
            return;
        }
        PlayerEntity playerEntity = this.getCameraPlayer();
        if (playerEntity == null) {
            return;
        }
        this.client.getProfiler().push("powerUpBar");
        int i = 0;
        int maxAir = playerEntity.getMaxAir();
        int air = Math.min(playerEntity.getAir(), maxAir);
        if (playerEntity.isSubmergedIn(FluidTags.WATER) || air < maxAir) {
            i = 9;
        }
        int height = this.scaledHeight - 50 - i;
        int width = this.scaledWidth / 2 + (91);
        this.itemRenderer.renderInGuiWithOverrides(playerEntity, PowerUp.getItemStack(this.getPowerUp()), width - (98), height - 3, 1);
        RenderSystem.setShaderTexture(0, GUI_ICONS_TEXTURE);
        int powerLevel = playerEntity.getDataTracker().get(FormManager.POWER_LEVEL);
        int powerHealth = playerEntity.getDataTracker().get(FormManager.POWER_HEALTH);
        for (int w = 0; w < 10; ++w) {
            int s = width - w * 8 - 9;
            this.drawTexture(matrices, s, height, 160, 224, 9, 9);
            if (w * 2 + 1 < powerLevel) {
                this.drawTexture(matrices, s, height, 178, 224, 9, 9);
            }
            if (w * 2 + 1 == powerLevel) {
                this.drawTexture(matrices, s, height, 187, 224, 9, 9);
            }
            if (w < powerHealth) {
                this.drawTexture(matrices, s, height, 169, 224, 9, 9);
            }
        }

        this.client.getProfiler().pop();
    }
}
