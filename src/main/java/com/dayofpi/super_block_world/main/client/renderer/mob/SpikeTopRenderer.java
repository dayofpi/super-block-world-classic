package com.dayofpi.super_block_world.main.client.renderer.mob;

import com.dayofpi.super_block_world.main.Main;
import com.dayofpi.super_block_world.main.client.feature.SpikeTopEyes;
import com.dayofpi.super_block_world.main.client.model.BuzzyModel;
import com.dayofpi.super_block_world.main.client.ModelLayers;
import com.dayofpi.super_block_world.main.common.entity.type.mobs.SpikeTopEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.World;
@Environment(EnvType.CLIENT)
public class SpikeTopRenderer<T extends SpikeTopEntity> extends MobEntityRenderer<T, BuzzyModel<T>> {
    private static final Identifier TEXTURE = new Identifier(Main.MOD_ID, "textures/entity/buzzy_beetle/spike_top.png");

    public SpikeTopRenderer(EntityRendererFactory.Context context) {
        super(context, new BuzzyModel<>(context.getPart(ModelLayers.BUZZY_BEETLE)), 0.5F);
        this.addFeature(new SpikeTopEyes<>(this));
    }

    @Override
    protected void setupTransforms(T entity, MatrixStack matrices, float animationProgress, float bodyYaw, float tickDelta) {
        if (entity.isUpsideDown()) {
            matrices.translate(0.0D, entity.getHeight() + 0.1F, 0.0D);
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
        } else if (entity.isClimbingWall() && !entity.isOnGround()) {
            World world = entity.getEntityWorld();
            BlockPos blockPos = entity.getBlockPos().down();
            float climbAngle = 90.0F;
            if (world.getBlockState(blockPos).isSideSolidFullSquare(world, blockPos, Direction.UP)) {
                climbAngle = 45.0F;
            }
            Direction moveDirection = entity.getMovementDirection();
            if ((moveDirection == Direction.NORTH)) {
                matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(climbAngle));
                matrices.translate(0.0D, -0.6F, 0.0D);
            } else if (moveDirection == Direction.SOUTH) {
                matrices.multiply(Vec3f.NEGATIVE_X.getDegreesQuaternion(climbAngle));
                matrices.translate(0.0D, -0.6F, 0.0D);
            } else if (moveDirection == Direction.EAST) {
                matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(climbAngle));
                matrices.translate(0.0D, 0.6F, 0.0D);
            } else if (moveDirection == Direction.WEST) {
                matrices.multiply(Vec3f.NEGATIVE_Z.getDegreesQuaternion(climbAngle));
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
