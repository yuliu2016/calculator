package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * suite: ':' 'simple_stmt' | 'block_suite'
 */
public final class Suite extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("suite", RuleType.Disjunction, true);

    public static Suite of(ParseTreeNode node) {
        return new Suite(node);
    }

    private Suite(ParseTreeNode node) {
        super(RULE, node);
    }

    public Suite1 suite1() {
        return Suite1.of(getItem(0));
    }

    public boolean hasSuite1() {
        return hasItemOfRule(0, Suite1.RULE);
    }

    public BlockSuite blockSuite() {
        return BlockSuite.of(getItem(1));
    }

    public boolean hasBlockSuite() {
        return hasItemOfRule(1, BlockSuite.RULE);
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) return false;
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = Suite1.parse(parseTree, level + 1);
        result = result || BlockSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ':' 'simple_stmt'
     */
    public static final class Suite1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("suite:1", RuleType.Conjunction, false);

        public static Suite1 of(ParseTreeNode node) {
            return new Suite1(node);
        }

        private Suite1(ParseTreeNode node) {
            super(RULE, node);
        }

        public SimpleStmt simpleStmt() {
            return SimpleStmt.of(getItem(1));
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) return false;
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(":");
            result = result && SimpleStmt.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
