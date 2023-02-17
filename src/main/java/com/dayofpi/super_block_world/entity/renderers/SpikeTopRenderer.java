package com.dayofpi.super_block_world.entity.renderers;

import com.dayofpi.super_block_world.Main;
import com.dayofpi.super_block_world.client.features.ModEyesFeatureRenderer;
import com.dayofpi.super_block_world.client.registry.ModModelLayers;
import com.dayofpi.super_block_world.entity.entities.hostile.SpikeTopEntity;
import com.dayofpi.super_block_world.entity.models.BuzzyBeetleModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;
@Environment(EnvType.CLIENT)
public class SpikeTopRenderer<T extends SpikeTopEntity> extends MobEntityRenderer<T, BuzzyBeetleModel<T>> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/buzzy_beetle/spike_top.png");
    private static final Identifier EYES = new Identifier(Main.MOD_ID, "textures/entity/buzzy_beetle/spike_top_eyes.png");

    public SpikeTopRenderer(EntityRendererFactory.Context context) {
        super(context, new BuzzyBeetleModel<>(context.getPart(ModModelLayers.SPIKE_TOP)), 0.5F);
        this.addFeature(new ModEyesFeatureRenderer<>(EYES, this));
    }

    @Override
    protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        if (entity.isUpsideDown()) {
            matrices.translate(0.0D, entity.getHeight() + 0.1F, 0.0D);
            matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180.0F));
        } else if (entity.isClimbingWall() && !entity.isOnGround()) {
            World world = entity.getEntityWorld();
            BlockPos blockPos = entity.getBlockPos().down();
            float climbAngle = 90.0F;
            if (world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP)) {
                climbAngle = 45.0F;
            }
            Direction moveDirection = entity.getMovementDirection();
            if ((moveDirection == Direction.NORTH)) {
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(climbAngle));
                matrices.translate(0.0D, -0.6F, 0.0D);
            } else if (moveDirection == Direction.SOUTH) {
                matrices.multiply(RotationAxis.NEGATIVE_X.rotationDegrees(climbAngle));
                matrices.translate(0.0D, -0.6F, 0.0D);
            } else if (moveDirection == Direction.EAST) {
                matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(climbAngle));
                matrices.translate(0.0D, 0.6F, 0.0D);
            } else if (moveDirection == Direction.WEST) {
                matrices.multiply(RotationAxis.NEGATIVE_Z.rotationDegrees(climbAngle));
                matrices.translate(0.0D, -0.6F, 0.0D);
            }
        }
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta);
    }

    @Override
    public Identifier getTexture(T entity) {
        return TEXTURE;
    }
}
