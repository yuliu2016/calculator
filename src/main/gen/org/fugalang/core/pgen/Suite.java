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

    @Override
    protected void buildRule() {
        addChoice("suite1", suite1());
        addChoice("blockSuite", blockSuite());
    }

    public Suite1 suite1() {
        var element = getItem(0);
        if (!element.isPresent()) return null;
        return Suite1.of(element);
    }

    public boolean hasSuite1() {
        return suite1() != null;
    }

    public BlockSuite blockSuite() {
        var element = getItem(1);
        if (!element.isPresent()) return null;
        return BlockSuite.of(element);
    }

    public boolean hasBlockSuite() {
        return blockSuite() != null;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
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

        @Override
        protected void buildRule() {
            addRequired("isTokenColon", isTokenColon());
            addRequired("simpleStmt", simpleStmt());
        }

        public boolean isTokenColon() {
            var element = getItem(0);
            return element.asBoolean();
        }

        public SimpleStmt simpleStmt() {
            var element = getItem(1);
            if (!element.isPresent()) return null;
            return SimpleStmt.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral(":");
            result = result && SimpleStmt.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
