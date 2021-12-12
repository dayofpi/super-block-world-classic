/*
 * Decompiled with CFR 0.0.9 (FabricMC cc05e23f).
 */
package com.dayofpi.sbw_main.client.renderer.other;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.*;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.text.OrderedText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.SignType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;

import java.util.List;
import java.util.Map;

@Environment(value=EnvType.CLIENT)
public class CustomSignRenderer
implements BlockEntityRenderer<SignBlockEntity> {
    private static final String STICK = "stick";
    private static final int RENDER_DISTANCE = MathHelper.square(16);
    private final Map<SignType, SignModel> typeToModel;
    private final TextRenderer textRenderer;

    public CustomSignRenderer(BlockEntityRendererFactory.Context ctx) {
        this.typeToModel = SignType.stream().collect(ImmutableMap.toImmutableMap(signType -> signType, signType -> new SignModel(ctx.getLayerModelPart(EntityModelLayers.createSign(signType)))));
        this.textRenderer = ctx.getTextRenderer();
    }

    @Override
    public void render(SignBlockEntity signBlockEntity, float f, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, int j) {
        int o;
        boolean bl;
        int n;
        BlockState blockState = signBlockEntity.getCachedState();
        matrixStack.push();
        float h;
        SignType signType = CustomSignRenderer.getSignType(blockState.getBlock());
        SignModel signModel = this.typeToModel.get(signType);
        if (blockState.getBlock() instanceof SignBlock) {
            matrixStack.translate(0.5, 0.5, 0.5);
            h = -((float)(blockState.get(SignBlock.ROTATION) * 360) / 16.0f);
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(h));
            signModel.stick.visible = true;
        } else {
            matrixStack.translate(0.5, 0.5, 0.5);
            h = -blockState.get(WallSignBlock.FACING).asRotation();
            matrixStack.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(h));
            matrixStack.translate(0.0, -0.3125, -0.4375);
            signModel.stick.visible = false;
        }
        matrixStack.push();
        matrixStack.scale(0.6666667f, -0.6666667f, -0.6666667f);
        SpriteIdentifier spriteIdentifier = TexturedRenderLayers.getSignTextureId(signType);
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumerProvider, signModel::getLayer);
        signModel.root.render(matrixStack, vertexConsumer, i, j);
        matrixStack.pop();
        matrixStack.translate(0.0, 0.3333333432674408, 0.046666666865348816);
        matrixStack.scale(0.010416667f, -0.010416667f, 0.010416667f);
        int l = CustomSignRenderer.getColor(signBlockEntity);
        OrderedText[] orderedTexts = signBlockEntity.updateSign(MinecraftClient.getInstance().shouldFilterText(), text -> {
            List<OrderedText> list = this.textRenderer.wrapLines(text, 90);
            return list.isEmpty() ? OrderedText.EMPTY : list.get(0);
        });
        if (signBlockEntity.isGlowingText()) {
            n = signBlockEntity.getTextColor().getSignColor();
            bl = CustomSignRenderer.shouldRender(signBlockEntity, n);
            o = 0xF000F0;
        } else {
            n = l;
            bl = false;
            o = i;
        }
        for (int p = 0; p < 4; ++p) {
            OrderedText orderedText = orderedTexts[p];
            float q = -this.textRenderer.getWidth(orderedText) / 2F;
            if (bl) {
                this.textRenderer.drawWithOutline(orderedText, q, p * 10 - 20, n, l, matrixStack.peek().getPositionMatrix(), vertexConsumerProvider, o);
                continue;
            }
            this.textRenderer.draw(orderedText, q, (float)(p * 10 - 20), n, false, matrixStack.peek().getPositionMatrix(), vertexConsumerProvider, false, 0, o);
        }
        matrixStack.pop();
    }

    private static boolean shouldRender(SignBlockEntity sign, int signColor) {
        if (signColor == DyeColor.BLACK.getSignColor()) {
            return true;
        }
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
        if (clientPlayerEntity != null && minecraftClient.options.getPerspective().isFirstPerson() && clientPlayerEntity.isUsingSpyglass()) {
            return true;
        }
        Entity entity = minecraftClient.getCameraEntity();
        return entity != null && entity.squaredDistanceTo(Vec3d.ofCenter(sign.getPos())) < (double)RENDER_DISTANCE;
    }

    private static int getColor(SignBlockEntity sign) {
        int i = sign.getTextColor().getSignColor();
        int j = (int)((double)NativeImage.getRed(i) * 0.4);
        int k = (int)((double)NativeImage.getGreen(i) * 0.4);
        int l = (int)((double)NativeImage.getBlue(i) * 0.4);
        if (i == DyeColor.BLACK.getSignColor() && sign.isGlowingText()) {
            return -988212;
        }
        return NativeImage.packColor(0, l, k, j);
    }

    public static SignType getSignType(Block block) {
        return block instanceof AbstractSignBlock ? ((AbstractSignBlock)block).getSignType() : SignType.OAK;
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("sign", ModelPartBuilder.create().uv(0, 0).cuboid(-12.0f, -14.0f, -1.0f, 24.0f, 12.0f, 2.0f), ModelTransform.NONE);
        modelPartData.addChild(STICK, ModelPartBuilder.create().uv(0, 14).cuboid(-1.0f, -2.0f, -1.0f, 2.0f, 14.0f, 2.0f), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 32);
    }

    @Environment(value=EnvType.CLIENT)
    public static final class SignModel
    extends Model {
        public final ModelPart root;
        public final ModelPart stick;

        public SignModel(ModelPart root) {
            super(RenderLayer::getEntityCutoutNoCull);
            this.root = root;
            this.stick = root.getChild(CustomSignRenderer.STICK);
        }

        @Override
        public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
            this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        }
    }
}

