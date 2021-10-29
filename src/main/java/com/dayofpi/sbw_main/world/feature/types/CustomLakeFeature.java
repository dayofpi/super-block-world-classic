package com.dayofpi.sbw_main.world.feature.types;


import com.dayofpi.sbw_main.block.registry.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.LightType;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.Random;

public class CustomLakeFeature extends Feature<SingleStateFeatureConfig> {
    private static final BlockState CAVE_AIR;

    static {
        CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    }

    public CustomLakeFeature(Codec<SingleStateFeatureConfig> codec) {
        super(codec);
    }

    public boolean generate(FeatureContext<SingleStateFeatureConfig> context) {
        BlockPos blockPos = context.getOrigin();
        StructureWorldAccess structureWorldAccess = context.getWorld();
        Random random = context.getRandom();

        SingleStateFeatureConfig singleStateFeatureConfig;
        for(singleStateFeatureConfig = context.getConfig(); blockPos.getY() > structureWorldAccess.getBottomY() + 5 && structureWorldAccess.isAir(blockPos); blockPos = blockPos.down()) {
        }

        if (blockPos.getY() <= structureWorldAccess.getBottomY() + 4) {
            return false;
        } else {
            blockPos = blockPos.down(4);
            if (!structureWorldAccess.getStructures(ChunkSectionPos.from(blockPos), StructureFeature.VILLAGE).isEmpty()) {
                return false;
            } else {
                boolean[] bls = new boolean[2048];
                int i = random.nextInt(4) + 4;

                int y;
                for(y = 0; y < i; ++y) {
                    double d = random.nextDouble() * 6.0D + 3.0D;
                    double e = random.nextDouble() * 4.0D + 2.0D;
                    double f = random.nextDouble() * 6.0D + 3.0D;
                    double g = random.nextDouble() * (16.0D - d - 2.0D) + 1.0D + d / 2.0D;
                    double h = random.nextDouble() * (8.0D - e - 4.0D) + 2.0D + e / 2.0D;
                    double k = random.nextDouble() * (16.0D - f - 2.0D) + 1.0D + f / 2.0D;

                    for(int l = 1; l < 15; ++l) {
                        for(int m = 1; m < 15; ++m) {
                            for(int n = 1; n < 7; ++n) {
                                double o = ((double)l - g) / (d / 2.0D);
                                double p = ((double)n - h) / (e / 2.0D);
                                double q = ((double)m - k) / (f / 2.0D);
                                double r = o * o + p * p + q * q;
                                if (r < 1.0D) {
                                    bls[(l * 16 + m) * 8 + n] = true;
                                }
                            }
                        }
                    }
                }

                int aa;
                int z;
                for(y = 0; y < 16; ++y) {
                    for(z = 0; z < 16; ++z) {
                        for(aa = 0; aa < 8; ++aa) {
                            boolean bl = !bls[(y * 16 + z) * 8 + aa] && (y < 15 && bls[((y + 1) * 16 + z) * 8 + aa] || y > 0 && bls[((y - 1) * 16 + z) * 8 + aa] || z < 15 && bls[(y * 16 + z + 1) * 8 + aa] || z > 0 && bls[(y * 16 + (z - 1)) * 8 + aa] || aa < 7 && bls[(y * 16 + z) * 8 + aa + 1] || aa > 0 && bls[(y * 16 + z) * 8 + (aa - 1)]);
                            if (bl) {
                                Material material = structureWorldAccess.getBlockState(blockPos.add(y, aa, z)).getMaterial();
                                if (aa >= 4 && material.isLiquid()) {
                                    return false;
                                }

                                if (aa < 4 && !material.isSolid() && structureWorldAccess.getBlockState(blockPos.add(y, aa, z)) != singleStateFeatureConfig.state) {
                                    return false;
                                }
                            }
                        }
                    }
                }

                BlockPos blockPos3;
                for(y = 0; y < 16; ++y) {
                    for(z = 0; z < 16; ++z) {
                        for(aa = 0; aa < 8; ++aa) {
                            if (bls[(y * 16 + z) * 8 + aa]) {
                                blockPos3 = blockPos.add(y, aa, z);
                                boolean bl2 = aa >= 4;
                                structureWorldAccess.setBlockState(blockPos3, bl2 ? CAVE_AIR : singleStateFeatureConfig.state, 2);
                                if (bl2) {
                                    structureWorldAccess.getBlockTickScheduler().schedule(blockPos3, CAVE_AIR.getBlock(), 0);
                                    this.markBlocksAboveForPostProcessing(structureWorldAccess, blockPos3);
                                }
                            }
                        }
                    }
                }

                for(y = 0; y < 16; ++y) {
                    for(z = 0; z < 16; ++z) {
                        for(aa = 4; aa < 8; ++aa) {
                            if (bls[(y * 16 + z) * 8 + aa]) {
                                blockPos3 = blockPos.add(y, aa - 1, z);
                                if (isSoil(structureWorldAccess.getBlockState(blockPos3)) && structureWorldAccess.getLightLevel(LightType.SKY, blockPos.add(y, aa, z)) > 0) {
                                    structureWorldAccess.setBlockState(blockPos3, ModBlocks.TOADSTOOL_GRASS.getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }

                if (singleStateFeatureConfig.state.getMaterial() == Material.WATER) {
                    for(y = 0; y < 16; ++y) {
                        for(z = 0; z < 16; ++z) {
                            blockPos3 = blockPos.add(y, 4, z);
                            if (structureWorldAccess.getBiome(blockPos3).canSetIce(structureWorldAccess, blockPos3, false)) {
                                structureWorldAccess.setBlockState(blockPos3, Blocks.ICE.getDefaultState(), 2);
                            }
                        }
                    }
                }

                return true;
            }
        }
    }
}
