package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * func_suite: ':' 'expr' | 'block_suite'
 */
public final class FuncSuite extends NodeWrapper {

    public static final ParserRule RULE =
            new ParserRule("func_suite", RuleType.Disjunction, true);

    public static FuncSuite of(ParseTreeNode node) {
        return new FuncSuite(node);
    }

    private FuncSuite(ParseTreeNode node) {
        super(RULE, node);
    }

    @Override
    protected void buildRule() {
        addChoice(funcSuite1OrNull());
        addChoice(blockSuiteOrNull());
    }

    public FuncSuite1 funcSuite1() {
        var element = getItem(0);
        element.failIfAbsent(FuncSuite1.RULE);
        return FuncSuite1.of(element);
    }

    public FuncSuite1 funcSuite1OrNull() {
        var element = getItem(0);
        if (!element.isPresent(FuncSuite1.RULE)) {
            return null;
        }
        return FuncSuite1.of(element);
    }

    public boolean hasFuncSuite1() {
        var element = getItem(0);
        return element.isPresent(FuncSuite1.RULE);
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

        result = FuncSuite1.parse(parseTree, level + 1);
        result = result || BlockSuite.parse(parseTree, level + 1);

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * ':' 'expr'
     */
    public static final class FuncSuite1 extends NodeWrapper {

        public static final ParserRule RULE =
                new ParserRule("func_suite:1", RuleType.Conjunction, false);

        public static FuncSuite1 of(ParseTreeNode node) {
            return new FuncSuite1(node);
        }

        private FuncSuite1(ParseTreeNode node) {
            super(RULE, node);
        }

        @Override
        protected void buildRule() {
            addRequired(isTokenColon(), ":");
            addRequired(expr());
        }

        public boolean isTokenColon() {
            var element = getItem(0);
            element.failIfAbsent();
            return element.asBoolean();
        }

        public Expr expr() {
            var element = getItem(1);
            element.failIfAbsent(Expr.RULE);
            return Expr.of(element);
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeToken(":");
            result = result && Expr.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
