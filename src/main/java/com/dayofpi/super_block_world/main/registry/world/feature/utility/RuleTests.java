package com.dayofpi.super_block_world.main.registry.world.feature.utility;

import com.dayofpi.super_block_world.main.registry.block.BlockRegistry;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;

public class RuleTests {
    public static final RuleTest VANILLATE = new BlockMatchRuleTest(BlockRegistry.VANILLATE);
    public static final RuleTest TOADSTONE = new BlockMatchRuleTest(BlockRegistry.TOADSTONE);
    public static final RuleTest ROYALITE = new BlockMatchRuleTest(BlockRegistry.ROYALITE);
    public static final RuleTest FROSTY_VANILLATE = new BlockMatchRuleTest(BlockRegistry.FROSTY_VANILLATE);
    public static final RuleTest FROSTED_VANILLATE = new BlockMatchRuleTest(BlockRegistry.FROSTED_VANILLATE);
    public static final RuleTest TOADSTOOL_SOIL = new BlockMatchRuleTest(BlockRegistry.TOADSTOOL_SOIL);
    public static final RuleTest TOADSTOOL_PATH = new BlockMatchRuleTest(BlockRegistry.TOADSTOOL_PATH);
    public static final RuleTest TOPPED_VANILLATE = new BlockMatchRuleTest(BlockRegistry.TOPPED_VANILLATE);
}
