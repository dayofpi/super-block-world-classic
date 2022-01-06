package com.dayofpi.super_block_world.client.renderer.mob;

import com.dayofpi.super_block_world.client.model.ToadModel;
import com.dayofpi.super_block_world.client.renderer.feature.ToadLayer;
import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.common.entity.mob.npc.ToadEntity;
import com.dayofpi.super_block_world.main.registry.main.ItemInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.MapColor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Vec3f;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

@Environment(EnvType.CLIENT)
public class ToadRenderer<T extends ToadEntity> extends GeoEntityRenderer<T> {
    public ToadRenderer(EntityRendererFactory.Context context) {
        super(context, new ToadModel<>());
        this.addLayer(new ToadLayer<>(this));
        this.shadowRadius = 0.5f;
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks, MatrixStack matrices, VertexConsumerProvider provider, int light) {
        super.render(entity, entityYaw, partialTicks, matrices, provider, light);
        if (this.dispatcher.targetedEntity != entity || entity.isBaby() || entity.isScared() || entity.isCheering()) {
            return;
        }
        String received = String.valueOf(entity.getReceivedCoins());
        String wanted = String.valueOf(entity.getWantedCoins());
        Text count = Text.of(received + "/" + wanted);
        float f = entity.getHeight() + 0.5f;
        int y = 10;
        matrices.push();
        matrices.translate(0.0, f, 0.0);
        matrices.multiply(this.dispatcher.getRotation());
        matrices.scale(-0.025f, -0.025f, 0.025f);
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        float backgroundOpacity = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25f);
        int j = (int)(backgroundOpacity * 255.0f) << 24;
        TextRenderer textRenderer = this.getTextRenderer();
        float x = -textRenderer.getWidth(count) / 2;
        textRenderer.draw(count, x, (float)y, MapColor.GOLD.color, false, matrix4f, provider, false, j, light);
        matrices.pop();
        matrices.push();
        matrices.translate(0.0, f, 0.0);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(entity.age * 10));
        matrices.scale(0.4f, 0.4f, 0.4f);
        MinecraftClient.getInstance().getItemRenderer().renderItem(ItemInit.COIN.getDefaultStack(), ModelTransformation.Mode.GUI, light, OverlayTexture.DEFAULT_UV, matrices, provider, 0);
        matrices.pop();
    }

    @Override
    public Identifier getTexture(T entity) {
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
