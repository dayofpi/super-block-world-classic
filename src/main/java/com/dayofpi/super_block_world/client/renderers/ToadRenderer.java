package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.ToadSkinFeatureRenderer;
import com.dayofpi.super_block_world.client.models.ToadModel;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.entities.passive.ToadEntity;
import com.dayofpi.super_block_world.registry.ModItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.MapColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class ToadRenderer extends MobEntityRenderer<ToadEntity, ToadModel<ToadEntity>> {
    public ToadRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new ToadModel<>(ctx.getPart(ModModelLayers.TOAD)), 0.5f);
        this.addFeature(new ToadSkinFeatureRenderer<>(this));
    }

    @Override
    protected boolean isShaking(ToadEntity entity) {
        return super.isShaking(entity) || entity.isScared();
    }

    @Override
    public void render(ToadEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int light) {
        super.render(entity, yaw, tickDelta, matrices, provider, light);
        if (this.dispatcher.targetedEntity != entity || entity.isBaby() || entity.isScared() || entity.isCheering()) {
            return;
        }
        String received = String.valueOf(entity.getReceivedCoins());
        String wanted = String.valueOf(entity.getWantedCoins());
        Text count = Text.of(received + "/" + wanted);
        float coinY = entity.getHeight() + 0.5f;
        int textY = 10;
        matrices.push();
        matrices.translate(0.0, coinY, 0.0);
        matrices.multiply(this.dispatcher.getRotation());
        matrices.scale(-0.025f, -0.025f, 0.025f);
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        float backgroundOpacity = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25f);
        int j = (int) (backgroundOpacity * 255.0f) << 24;
        TextRenderer textRenderer = this.getTextRenderer();
        float x = -textRenderer.getWidth(count) / 2f;
        textRenderer.draw(count, x, (float) textY, MapColor.GOLD.color, false, matrix4f, provider, false, j, light);
        matrices.pop();
        matrices.push();
        matrices.translate(0.0, coinY, 0.0);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.age * 10));
        matrices.scale(0.4f, 0.4f, 0.4f);
        MinecraftClient.getInstance().getItemRenderer().renderItem(ModItems.COIN.getDefaultStack(), ModelTransformation.Mode.GUI, light, OverlayTexture.DEFAULT_UV, matrices, provider, 0);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(ToadEntity entity) {
        if (entity.isScared())
            return new Identifier(Main.MOD_ID, "textures/entity/toad/toad_scared.png");
        else if (entity.isHappy())
            return new Identifier(Main.MOD_ID, "textures/entity/toad/toad_happy.png");
        if (entity.isAnnoyed())
            return new Identifier(Main.MOD_ID, "textures/entity/toad/toad_annoyed.png");
        else
            return new Identifier(Main.MOD_ID, "textures/entity/toad/toad.png");
    }
}
