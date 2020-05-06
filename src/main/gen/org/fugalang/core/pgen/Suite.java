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
        addChoice(suite1OrNull());
        addChoice(blockSuiteOrNull());
    }

    public Suite1 suite1() {
        var element = getItem(0);
        element.failIfAbsent(Suite1.RULE);
        return Suite1.of(element);
    }

    public Suite1 suite1OrNull() {
        var element = getItem(0);
        if (!element.isPresent(Suite1.RULE)) {
            return null;
        }
        return Suite1.of(element);
    }

    public boolean hasSuite1() {
        var element = getItem(0);
        return element.isPresent(Suite1.RULE);
    }

    public BlockSuite blockSuite() {
        var element = getItem(1);
        element.failIfAbsent(BlockSuite.RULE);
        return BlockSuite.of(element);
    }

    public BlockSuite blockSuiteOrNull() {
        var element = getItem(1);
        if (!element.isPresent(BlockSuite.RULE)) {
            return null;
        }
        return BlockSuite.of(element);
    }

    public boolean hasBlockSuite() {
        var element = getItem(1);
        return element.isPresent(BlockSuite.RULE);
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
            addRequired(isTokenColon(), ":");
            addRequired(simpleStmt());
        }

        public boolean isTokenColon() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public SimpleStmt simpleStmt() {
            var element = getItem(1);
            element.failIfAbsent(SimpleStmt.RULE);
            return SimpleStmt.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(":");
            result = result && SimpleStmt.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
