package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * suite: ':' 'simple_stmt' | 'block_suite'
 */
public final class Suite extends DisjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("suite", RuleType.Disjunction, true);

    private final Suite1 suite1;
    private final BlockSuite blockSuite;

    public Suite(
            Suite1 suite1,
            BlockSuite blockSuite
    ) {
        this.suite1 = suite1;
        this.blockSuite = blockSuite;
    }

    @Override
    protected void buildRule() {
        addChoice("suite1", suite1());
        addChoice("blockSuite", blockSuite());
    }

    public Suite1 suite1() {
        return suite1;
    }

    public boolean hasSuite1() {
        return suite1() != null;
    }

    public BlockSuite blockSuite() {
        return blockSuite;
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
    public static final class Suite1 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("suite:1", RuleType.Conjunction, false);

        private final boolean isTokenColon;
        private final SimpleStmt simpleStmt;

        public Suite1(
                boolean isTokenColon,
                SimpleStmt simpleStmt
        ) {
            this.isTokenColon = isTokenColon;
            this.simpleStmt = simpleStmt;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenColon", isTokenColon());
            addRequired("simpleStmt", simpleStmt());
        }

        public boolean isTokenColon() {
            return isTokenColon;
        }

        public SimpleStmt simpleStmt() {
            return simpleStmt;
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
