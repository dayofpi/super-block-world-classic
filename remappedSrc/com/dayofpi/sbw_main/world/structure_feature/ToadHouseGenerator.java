package com.dayofpi.sbw_main.world.structure_feature;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.block.registry.categories.PottedBlocks;
import com.dayofpi.sbw_main.world.registry.ModStructurePieces;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.class_6625;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.SimpleStructurePiece;
import net.minecraft.structure.StructureManager;
import net.minecraft.structure.StructurePiecesHolder;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.processor.BlockIgnoreStructureProcessor;
import net.minecraft.structure.processor.RuleStructureProcessor;
import net.minecraft.structure.processor.StructureProcessorRule;
import net.minecraft.structure.rule.AlwaysTrueRuleTest;
import net.minecraft.structure.rule.RandomBlockMatchRuleTest;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.List;
import java.util.Random;

public class ToadHouseGenerator {
    private static final Identifier RED_TOAD_HOUSE = new Identifier(Main.MOD_ID, "toad_house");
    private static final Identifier GREEN_TOAD_HOUSE = new Identifier(Main.MOD_ID, "toad_house_green");

    public static void addPieces(StructureManager manager, StructurePiecesHolder structurePiecesHolder, Random random, BlockPos pos) {
        BlockRotation blockRotation = BlockRotation.random(random);
        structurePiecesHolder.addPiece(new Piece(manager, random.nextFloat() < .3F ? GREEN_TOAD_HOUSE : RED_TOAD_HOUSE, pos, blockRotation));
    }

    public static class Piece extends SimpleStructurePiece {
        public Piece(class_6625 class_6625, NbtCompound nbtCompound) {
            super(ModStructurePieces.TOAD_HOUSE, nbtCompound, class_6625.structureManager(),
                    (identifier) -> createPlacementData(BlockRotation.valueOf(nbtCompound.getString("Rot"))));
        }

        public Piece(StructureManager structureManager, Identifier identifier, BlockPos blockPos, BlockRotation blockRotation) {
            super(ModStructurePieces.TOAD_HOUSE, 0, structureManager, identifier, identifier.toString(), createPlacementData(blockRotation), blockPos);
            this.pos = blockPos;
        }

        private static StructurePlacementData createPlacementData(BlockRotation rotation) {
            List<StructureProcessorRule> processors = Lists.newArrayList();
            processors.add(createReplacementRule(ModBlocks.QUESTION_BLOCK, 0.25F, Blocks.AIR));
            processors.add(createReplacementRule(ModBlocks.QUESTION_BLOCK, 0.25F, ModBlocks.COIN_BLOCK));
            processors.add(createReplacementRule(PottedBlocks.POTTED_GREEN_MUSHROOM, 0.25F, PottedBlocks.POTTED_FIRE_TULIP));
            processors.add(createReplacementRule(PottedBlocks.POTTED_GREEN_MUSHROOM, 0.25F, PottedBlocks.POTTED_PURPLE_MUSHROOM));
            processors.add(createReplacementRule(Blocks.POTTED_RED_MUSHROOM, 0.3F, Blocks.POTTED_BROWN_MUSHROOM));
            processors.add(createReplacementRule(Blocks.POTTED_RED_MUSHROOM, 0.25F, PottedBlocks.POTTED_YELLOW_MUSHROOM));
            processors.add(createReplacementRule(Blocks.POTTED_RED_MUSHROOM, 0.25F, PottedBlocks.POTTED_MUNCHER));
            processors.add(createReplacementRule(Blocks.POTTED_RED_MUSHROOM, 0.25F, PottedBlocks.POTTED_BEANSTALK));

            return new StructurePlacementData()
                    .setRotation(rotation)
                    .setMirror(BlockMirror.NONE)
                    .addProcessor(new RuleStructureProcessor(processors))
                    .addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
        }

        private static StructureProcessorRule createReplacementRule(Block old, float chance, Block updated) {
            return new StructureProcessorRule(new RandomBlockMatchRuleTest(old, chance), AlwaysTrueRuleTest.INSTANCE, updated.getDefaultState());
        }

        protected void writeNbt(class_6625 arg, NbtCompound nbt) {
            super.writeNbt(arg, nbt);
            nbt.putString("Rot", this.placementData.getRotation().name());
        }

        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess world, Random random, BlockBox boundingBox) {
        }

        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
            int newPos = world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, this.pos.getX(), this.pos.getZ());
            this.pos = new BlockPos(this.pos.getX(), newPos - 1, this.pos.getZ());
            super.generate(world, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, pos);
        }
    }
}
