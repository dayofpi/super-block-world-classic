package com.dayofpi.sbw_main.world.structure_feature;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.world.registry.ModStructures;
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
    private static final Identifier STRUCTURE = new Identifier(Main.MOD_ID, "toad_house");

    public static void addPiece(StructureManager structureManager, BlockPos pos, BlockRotation rotation, StructurePiecesHolder structurePiecesHolder) {
        structurePiecesHolder.addPiece(new Piece(structureManager, STRUCTURE, pos));
    }

    public static class Piece extends SimpleStructurePiece {
        public Piece(StructureManager structureManager, Identifier identifier, BlockPos blockPos) {
            super(ModStructures.TOAD_HOUSE_PIECE, 0, structureManager, identifier, identifier.toString(), createPlacementData(), blockPos);
        }

        public Piece(class_6625 class_6625, NbtCompound nbtCompound) {
            super(ModStructures.TOAD_HOUSE_PIECE, nbtCompound, class_6625.structureManager(), (identifier) -> createPlacementData());
        }

        private static StructurePlacementData createPlacementData() {
            List<StructureProcessorRule> list = Lists.newArrayList();
            list.add(createReplacementRule(ModBlocks.QUESTION_BLOCK, 0.25F, Blocks.AIR));
            list.add(createReplacementRule(ModBlocks.QUESTION_BLOCK, 0.25F, ModBlocks.COIN_BLOCK));
            list.add(createReplacementRule(Blocks.POTTED_RED_MUSHROOM, 0.3F, Blocks.POTTED_BROWN_MUSHROOM));
            list.add(createReplacementRule(Blocks.POTTED_RED_MUSHROOM, 0.25F, ModBlocks.POTTED_YELLOW_MUSHROOM));
            list.add(createReplacementRule(Blocks.POTTED_RED_MUSHROOM, 0.25F, ModBlocks.POTTED_FIRE_TULIP));
            list.add(createReplacementRule(Blocks.POTTED_RED_MUSHROOM, 0.25F, ModBlocks.POTTED_MUNCHER));
            list.add(createReplacementRule(Blocks.POTTED_RED_MUSHROOM, 0.25F, ModBlocks.POTTED_BEANSTALK));

            return (new StructurePlacementData()).setRotation(BlockRotation.random(new Random())).setMirror(BlockMirror.NONE).addProcessor(new RuleStructureProcessor(list)).addProcessor(BlockIgnoreStructureProcessor.IGNORE_STRUCTURE_BLOCKS);
        }

        private static StructureProcessorRule createReplacementRule(Block old, float chance, Block updated) {
            return new StructureProcessorRule(new RandomBlockMatchRuleTest(old, chance), AlwaysTrueRuleTest.INSTANCE, updated.getDefaultState());
        }

        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess world, Random random, BlockBox boundingBox) {
        }

        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
            this.pos = new BlockPos(this.pos.getX(), world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ()), this.pos.getZ());
            super.translate(0, -1, 0);
            super.generate(world, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, pos);
        }
    }
}
