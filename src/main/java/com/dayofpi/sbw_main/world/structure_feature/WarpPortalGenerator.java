package com.dayofpi.sbw_main.world.structure_feature;

import com.dayofpi.sbw_main.Main;
import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.dayofpi.sbw_main.world.registry.ModStructurePieces;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.class_6625;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.state.property.Properties;
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
import net.minecraft.util.math.Direction;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.List;
import java.util.Random;

public class WarpPortalGenerator {
    private static final Identifier STRUCTURE = new Identifier(Main.MOD_ID, "warp_portal");

    public static void addPiece(StructureManager structureManager, BlockPos pos, BlockRotation rotation, StructurePiecesHolder structurePiecesHolder) {
        structurePiecesHolder.addPiece(new Piece(structureManager, STRUCTURE, pos, rotation));
    }

    public static class Piece extends SimpleStructurePiece {
        private final BlockRotation rotation;
        private final Identifier template;

        public Piece(class_6625 class_6625, NbtCompound nbtCompound) {
            super(ModStructurePieces.WARP_PORTAL, nbtCompound, class_6625.structureManager(), (identifier) -> createPlacementData());
            this.template = new Identifier(nbtCompound.getString("Template"));
            this.rotation = BlockRotation.valueOf(nbtCompound.getString("Rot"));
        }

        public Piece(StructureManager structureManager, Identifier identifier, BlockPos blockPos, BlockRotation blockRotation) {
            super(ModStructurePieces.WARP_PORTAL, 0, structureManager, identifier, identifier.toString(), createPlacementData(), blockPos);
            this.pos = blockPos;
            this.rotation = blockRotation;
            this.template = identifier;
        }

        protected void writeNbt(class_6625 arg, NbtCompound nbt) {
            super.writeNbt(arg, nbt);
            nbt.putString("Template", this.template.toString());
            nbt.putString("Rot", this.rotation.name());
        }

        private static StructurePlacementData createPlacementData() {
            List<StructureProcessorRule> processors = Lists.newArrayList();
            Random random = new Random();
            processors.add(createReplacementRule(ModBlocks.STONE_TORCH, 0.1F, ModBlocks.STONE_TORCH));
            processors.add(createReplacementRule(ModBlocks.WARP_FRAME, 0.4F, Blocks.AIR));
            processors.add(createReplacementRule(ModBlocks.WARP_FRAME, 0.2F, ModBlocks.GOLDEN_BRICKS));
            processors.add(createReplacementRule(ModBlocks.POLISHED_HARDSTONE, 0.5F, ModBlocks.HARDSTONE));
            processors.add(createReplacementRule(ModBlocks.HARDSTONE_BRICKS, 0.3F, ModBlocks.CRACKED_HARDSTONE_BRICKS));
            processors.add(createReplacementRule(ModBlocks.HARDSTONE_BRICK_STAIRS, 0.1F, ModBlocks.WARP_FRAME));
            processors.add(createReplacementRule(ModBlocks.HARDSTONE_BRICKS, 0.2F, ModBlocks.HARDSTONE_BRICK_STAIRS.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.fromHorizontal(random.nextInt(4)))));
            processors.add(createReplacementRule(ModBlocks.HARDSTONE_BRICKS, 0.1F, Blocks.AIR));
            processors.add(createReplacementRule(ModBlocks.HARDSTONE_BRICKS, 0.2F, Blocks.GRAVEL));

            return (new StructurePlacementData())
                    .setRotation(BlockRotation.random(new Random()))
                    .setMirror(BlockMirror.NONE).addProcessor(new RuleStructureProcessor(processors))
                    .addProcessor(BlockIgnoreStructureProcessor.IGNORE_AIR_AND_STRUCTURE_BLOCKS);
        }

        private static StructureProcessorRule createReplacementRule(Block old, float chance, Block updated) {
            return new StructureProcessorRule(new RandomBlockMatchRuleTest(old, chance), AlwaysTrueRuleTest.INSTANCE, updated.getDefaultState());
        }

        private static StructureProcessorRule createReplacementRule(Block old, float chance, BlockState updated) {
            return new StructureProcessorRule(new RandomBlockMatchRuleTest(old, chance), AlwaysTrueRuleTest.INSTANCE, updated);
        }

        protected void handleMetadata(String metadata, BlockPos pos, ServerWorldAccess world, Random random, BlockBox boundingBox) {
        }

        public void generate(StructureWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockBox boundingBox, ChunkPos chunkPos, BlockPos pos) {
            this.pos = new BlockPos(this.pos.getX(), world.getTopY(Heightmap.Type.WORLD_SURFACE_WG, pos.getX(), pos.getZ()), this.pos.getZ());
            this.translate(0, -random.nextInt(4), 0);
            super.generate(world, structureAccessor, chunkGenerator, random, boundingBox, chunkPos, pos);
        }
    }
}
