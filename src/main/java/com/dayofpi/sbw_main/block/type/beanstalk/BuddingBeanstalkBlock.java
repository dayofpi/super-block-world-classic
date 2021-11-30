package com.dayofpi.sbw_main.block.type.beanstalk;

import com.dayofpi.sbw_main.block.registry.categories.PlantBlocks;
import com.dayofpi.sbw_main.block.type.template.ModPlantStemBlock;
import com.dayofpi.sbw_main.entity.registry.ModEntities;
import com.dayofpi.sbw_main.entity.type.mobs.NipperPlantEntity;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineLogic;
import net.minecraft.client.util.ParticleUtil;
import net.minecraft.fluid.Fluids;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.Random;

public class BuddingBeanstalkBlock extends ModPlantStemBlock {
    public static final VoxelShape SHAPE = Block.createCuboidShape(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);
    public static final BooleanProperty OPEN;

    static {
        OPEN = Properties.OPEN;
    }

    public BuddingBeanstalkBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.UP, SHAPE, false, 0.1D);
        this.setDefaultState(this.stateManager.getDefaultState().with(OPEN, false));
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(OPEN).add(AGE);
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(OPEN)) {
            double d = (double) pos.getX() + 0.5D;
            double e = (double) pos.getY() + 0.5D;
            double f = (double) pos.getZ() + 0.5D;
            world.addParticle(ParticleTypes.SPORE_BLOSSOM_AIR, d, e, f, 0.0D, 0.5D, 0.0D);
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (direction == this.growthDirection.getOpposite() && !state.canPlaceAt(world, pos)) {
            world.createAndScheduleBlockTick(pos, this, 1);
        }

        if (direction != this.growthDirection || !neighborState.isOf(this) && !neighborState.isOf(this.getPlant())) {
            if (this.tickWater) {
                world.createAndScheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
            }

            return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        } else {
            return this.copyState(state, this.getPlant().getDefaultState());
        }
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        boolean hasLight = world.getLightLevel(pos.up()) >= 9;
        boolean isOpen = world.getBlockState(pos).get(OPEN);
        if (isOpen) {
            if (!hasLight || random.nextInt(7) == 0) {
                world.setBlockState(pos, state.with(OPEN, false));
            }
            if (hasLight) {
                int multiplier;
                if (random.nextInt(2) == 0) {
                    multiplier = -1;
                } else multiplier = 1;
                BlockPos soil = pos.add(random.nextInt(2) * multiplier, random.nextInt(8) * -1, random.nextInt(2) * multiplier);
                NipperPlantEntity entity = ModEntities.NIPPER_PLANT.create(world);
                if (world.getBlockState(soil.down()).isIn(BlockTags.DIRT) && !world.getBlockState(soil.up()).isOpaque() && (entity != null)) {
                    entity.refreshPositionAndAngles((double) soil.getX() + 0.5D, soil.getY() + 1D, (double) soil.getZ() + 0.5D, 0.0F, 0.0F);
                    world.spawnEntity(entity);
                    ParticleUtil.spawnParticle(world, soil.up(), ParticleTypes.HAPPY_VILLAGER, UniformIntProvider.create(2, 3));
                    world.playSound(null, soil, SoundEvents.ITEM_BONE_MEAL_USE, SoundCategory.NEUTRAL, 2.0F, 1.0F);
                    if (random.nextInt(2) == 0) {
                        world.setBlockState(pos, state.with(OPEN, false));
                    }
                }
            }
        } else if (hasLight && random.nextInt(7) == 0) {
            world.setBlockState(pos, state.with(OPEN, true));
            world.createAndScheduleBlockTick(pos, this, 500);

        }
        super.randomTick(state, world, pos, random);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos blockPos, Random random) {
        super.scheduledTick(state, world, blockPos, random);
        world.setBlockState(blockPos, state.with(OPEN, false));
        if (!this.canPlaceAt(state, world, blockPos.down())) {
            world.breakBlock(blockPos, true );
        }
    }

    protected int getGrowthLength(Random random) {
        return VineLogic.getGrowthLength(random);
    }

    protected Block getPlant() {
        return PlantBlocks.BEANSTALK_PLANT;
    }

    protected boolean chooseStemState(BlockState state) {
        return VineLogic.isValidForWeepingStem(state);
    }
}