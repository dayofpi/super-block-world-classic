package com.dayofpi.sbw_main.entity.renderer;

import com.dayofpi.sbw_main.block.block_entity.ModSignBE;
import com.dayofpi.sbw_main.block.types.template.ModSignBlock;
import com.dayofpi.sbw_main.block.types.template.ModSignTemplate;
import com.dayofpi.sbw_main.block.types.template.ModWallSignBlock;
import com.google.common.collect.ImmutableMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
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
import java.util.Objects;

@Environment(EnvType.CLIENT)
public class ModSignRenderer implements BlockEntityRenderer<ModSignBE> {
    private static final int RENDER_DISTANCE = MathHelper.square(16);
    private final Map<SignType, ModSignRenderer.SignModel> typeToModel;
    private final TextRenderer textRenderer;

    public ModSignRenderer(BlockEntityRendererFactory.Context ctx) {
        this.typeToModel = SignType.stream().collect(ImmutableMap.toImmutableMap((signType) -> signType, (signType) -> new SignModel(ctx.getLayerModelPart(EntityModelLayers.createSign(signType)))));
        this.textRenderer = ctx.getTextRenderer();
    }

    @Override
    public void render(ModSignBE entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockState blockState = entity.getCachedState();
        matrices.push();
        float g = 0.6666667F;
        SignType signType = getSignType(blockState.getBlock());
        SignModel signModel = this.typeToModel.get(signType);
        float h;
        if (blockState.getBlock() instanceof ModSignBlock) {
            matrices.translate(0.5D, 0.5D, 0.5D);
            h = -((float) (blockState.get(ModSignBlock.ROTATION) * 360) / 16.0F);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(h));
            signModel.stick.visible = true;
        } else {
            matrices.translate(0.5D, 0.5D, 0.5D);
            h = -blockState.get(ModWallSignBlock.FACING).asRotation();
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(h));
            matrices.translate(0.0D, -0.3125D, -0.4375D);
            signModel.stick.visible = false;
        }

        matrices.push();
        matrices.scale(g, -g, -g);
        SpriteIdentifier spriteIdentifier = TexturedRenderLayers.getSignTextureId(signType);
        Objects.requireNonNull(signModel);
        VertexConsumer vertexConsumer = spriteIdentifier.getVertexConsumer(vertexConsumers, signModel::getLayer);
        signModel.root.render(matrices, vertexConsumer, light, overlay);
        matrices.pop();
        float l = 0.010416667F;
        matrices.translate(0.0D, 0.3333333432674408D, 0.046666666865348816D);
        matrices.scale(l, -l, l);
        int m = getColor(entity);
        OrderedText[] orderedTexts = entity.updateSign(MinecraftClient.getInstance().shouldFilterText(), (text) -> {
            List<OrderedText> list = this.textRenderer.wrapLines(text, 90);
            return list.isEmpty() ? OrderedText.EMPTY : list.get(0);
        });
        int q;
        boolean bl2;
        int r;
        if (entity.isGlowingText()) {
            q = entity.getTextColor().getSignColor();
            bl2 = shouldRender(entity, q);
            r = 15728880;
        } else {
            q = m;
            bl2 = false;
            r = light;
        }

        for (int s = 0; s < 4; ++s) {
            OrderedText orderedText = orderedTexts[s];
            float t = (float) (-this.textRenderer.getWidth(orderedText) / 2);
            if (bl2) {
                this.textRenderer.drawWithOutline(orderedText, t, (float) (s * 10 - 20), q, m, matrices.peek().getModel(), vertexConsumers, r);
            } else {
                this.textRenderer.draw(orderedText, t, (float) (s * 10 - 20), q, false, matrices.peek().getModel(), vertexConsumers, false, 0, r);
            }
        }

        matrices.pop();
    }

    public static SignType getSignType(Block block) {
        SignType signType2;
        signType2 = ((ModSignTemplate) block).getSignType();

        return signType2;
    }

    private static int getColor(ModSignBE sign) {
        int i = sign.getTextColor().getSignColor();
        double d = 0.4D;
        int j = (int) ((double) NativeImage.getRed(i) * d);
        int k = (int) ((double) NativeImage.getGreen(i) * d);
        int l = (int) ((double) NativeImage.getBlue(i) * d);
        return i == DyeColor.BLACK.getSignColor() && sign.isGlowingText() ? -988212 : NativeImage.packColor(0, l, k, j);
    }

    private static boolean shouldRender(ModSignBE sign, int signColor) {
        if (signColor == DyeColor.BLACK.getSignColor()) {
            return true;
        } else {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            ClientPlayerEntity clientPlayerEntity = minecraftClient.player;
            if (clientPlayerEntity != null && minecraftClient.options.getPerspective().isFirstPerson() && clientPlayerEntity.isUsingSpyglass()) {
                return true;
            } else {
                Entity entity = minecraftClient.getCameraEntity();
                return entity != null && entity.squaredDistanceTo(Vec3d.ofCenter(sign.getPos())) < (double) RENDER_DISTANCE;
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public static final class SignModel extends Model {
        public final ModelPart root;
        public final ModelPart stick;

        public SignModel(ModelPart root) {
            super(RenderLayer::getEntityCutoutNoCull);
            this.root = root;
            this.stick = root.getChild("stick");
        }

        public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
            this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
        }
    }
}
