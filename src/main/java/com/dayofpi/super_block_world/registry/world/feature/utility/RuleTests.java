package com.dayofpi.super_block_world.registry.world.feature.utility;

import com.dayofpi.super_block_world.registry.main.BlockInit;
import net.minecraft.structure.rule.BlockMatchRuleTest;
import net.minecraft.structure.rule.RuleTest;

public class RuleTests {
    public static final RuleTest VANILLATE = new BlockMatchRuleTest(BlockInit.VANILLATE);
    public static final RuleTest TOADSTONE = new BlockMatchRuleTest(BlockInit.TOADSTONE);
    public static final RuleTest ROYALITE = new BlockMatchRuleTest(BlockInit.ROYALITE);
    public static final RuleTest FROSTY_VANILLATE = new BlockMatchRuleTest(BlockInit.FROSTY_VANILLATE);
    public static final RuleTest FROSTED_VANILLATE = new BlockMatchRuleTest(BlockInit.FROSTED_VANILLATE);
    public static final RuleTest TOADSTOOL_SOIL = new BlockMatchRuleTest(BlockInit.TOADSTOOL_SOIL);
    public static final RuleTest TOPPED_VANILLATE = new BlockMatchRuleTest(BlockInit.TOPPED_VANILLATE);
}
