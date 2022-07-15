package com.dayofpi.super_block_world.client.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.common.block_entities.FlagBE;
import com.dayofpi.super_block_world.common.blocks.FlagBlock;
import com.dayofpi.super_block_world.registry.ModTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

import java.util.Arrays;
import java.util.Comparator;

@Environment(EnvType.CLIENT)
@SuppressWarnings("deprecation")
public class FlagRenderer implements BlockEntityRenderer<FlagBE> {
    public static final SpriteIdentifier POLE_TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(Main.MOD_ID, "entity/flag/flag"));
    public static final SpriteIdentifier[] COLOR_TEXTURES = Arrays.stream(DyeColor.values()).sorted(Comparator.comparingInt(DyeColor::getId)).map(dyeColor -> new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(Main.MOD_ID, "entity/flag/" + dyeColor.getName()))).toArray(SpriteIdentifier[]::new);
    public static final SpriteIdentifier RAINBOW_FLAG = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(Main.MOD_ID, "entity/flag/rainbow"));
    public static final SpriteIdentifier TRANS_FLAG = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(Main.MOD_ID, "entity/flag/trans"));
    public static final SpriteIdentifier BI_FLAG = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(Main.MOD_ID, "entity/flag/bi"));
    public static final SpriteIdentifier LESBIAN_FLAG = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(Main.MOD_ID, "entity/flag/lesbian"));

    public static final String ROOT = "root";
    public static final String FLAG = "flag";
    public static final String POLE = "pole";
    private final ModelPart flag;
    private final ModelPart pole;

    public FlagRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(ModModelLayers.FLAG);
        ModelPart root = modelPart.getChild(ROOT);
        this.flag = root.getChild(FLAG);
        this.pole = root.getChild(POLE);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild(ROOT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        root.addChild(FLAG, ModelPartBuilder.create().uv(8, 8).cuboid(-12.0F, -6.0F, 0.0F, 12.0F, 12.0F, 0.0F), ModelTransform.of(0.0F, -6.0F, 0.0F, 0.0F, -1.5708F, 0.0F));
        root.addChild(POLE, ModelPartBuilder.create().uv(0, 0).cuboid(-10.0F, -16.0F, 6.0F, 4.0F, 4.0F, 4.0F), ModelTransform.pivot(8.0F, 0.0F, -8.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    private SpriteIdentifier getSpecialFlag(FlagBE entity) {
        Text text = entity.getCustomName();
        if (text != null) {
            for (String name : FlagBlock.TRANS) {
                if (text.getString().contains(name))
                    return TRANS_FLAG;
            }
            for (String name : FlagBlock.BI) {
                if (text.getString().contains(name))
                    return BI_FLAG;
            }
            for (String name : FlagBlock.LESBIAN) {
                if (text.getString().contains(name))
                    return LESBIAN_FLAG;
            }
        }
        return RAINBOW_FLAG;
    }

    @Override
    public void render(FlagBE entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider provider, int light, int overlay) {
        World world = entity.getWorld();
        BlockPos blockPos = entity.getPos();
        if (world != null) {
            matrices.push();
            matrices.scale(1.0F, -1.0F, -1.0F);
            matrices.translate(0.5, 0.0, -0.5);
            matrices.push();
            VertexConsumer consumer = POLE_TEXTURE.getVertexConsumer(provider, RenderLayer::getEntitySolid);
            this.pole.render(matrices, consumer, light, overlay);
            matrices.pop();
            renderFlag(matrices, world, blockPos, entity, tickDelta, provider, light, overlay);
            matrices.pop();
        }
    }

    private void renderFlag(MatrixStack matrices, World world, BlockPos blockPos, FlagBE entity, float tickDelta, VertexConsumerProvider provider, int light, int overlay) {
        matrices.push();
        SpriteIdentifier id = COLOR_TEXTURES[entity.getColor().getId()];
        if (entity.isRainbow()) id = this.getSpecialFlag(entity);

        int rotation = 0;
        if (world.getBlockState(blockPos).isIn(ModTags.FLAGS))
            rotation = world.getBlockState(blockPos).get(FlagBlock.ROTATION);
        float angle = (rotation * 22.5F) % 360;
        float wave = MathHelper.cos(world.getTime()) + angle;

        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(wave));
        this.flag.render(matrices, id.getVertexConsumer(provider, RenderLayer::getEntityCutout), light, overlay);
        matrices.pop();
    }
}
