package com.dayofpi.super_block_world.block.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.block.block_entities.SuperPickaxBE;
import com.dayofpi.super_block_world.block.blocks.SuperPickaxBlock;
import com.dayofpi.super_block_world.block.ModBlocks;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
@Environment(EnvType.CLIENT)
public class SuperPickaxRenderer implements BlockEntityRenderer<SuperPickaxBE>  {
    public static final SpriteIdentifier TEXTURE = new SpriteIdentifier(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE, new Identifier(Main.MOD_ID, "entity/super_pickax"));
    private final ModelPart root;

    public SuperPickaxRenderer(BlockEntityRendererFactory.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(ModModelLayers.SUPER_PICKAX);
        this.root = modelPart.getChild("root");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild("root", ModelPartBuilder.create().uv(0, 8).cuboid(-1.0F, -10.0F, -1.0F, 2.0F, 10.0F, 2.0F)
                .uv(0, 0).cuboid(-2.0F, -9.0F, -2.0F, 4.0F, 4.0F, 4.0F)
                .uv(8, 13).mirrored().cuboid(2.0F, -8.0F, -1.0F, 3.0F, 3.0F, 2.0F).mirrored(false)
                .uv(8, 8).cuboid(-5.0F, -8.0F, -1.0F, 3.0F, 3.0F, 2.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void render(SuperPickaxBE entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        World world = entity.getWorld();
        boolean hasWorld = world != null;
        BlockState blockState = hasWorld ? entity.getCachedState() : ModBlocks.SUPER_PICKAX.getDefaultState().with(SuperPickaxBlock   .FACING, Direction.SOUTH);
        float rotation = blockState.get(SuperPickaxBlock.FACING).rotateYClockwise().asRotation();

        matrices.translate(0.5, 0.0, 0.5);
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(-rotation));

        if (hasWorld && blockState.get(SuperPickaxBlock.ACTIVE)) {
            float progress = world.getTime() * 2F * 0.1F;
            matrices.multiply(Vec3f.POSITIVE_Z.getRadialQuaternion(MathHelper.cos(progress)));
        }
        matrices.scale(-1.0F, -1.0F, 1.0F);
        matrices.translate(0.0, -1.5, 0.0);

        VertexConsumer consumer = TEXTURE.getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);
        this.root.render(matrices, consumer, light, overlay);
        matrices.pop();
    }
}
