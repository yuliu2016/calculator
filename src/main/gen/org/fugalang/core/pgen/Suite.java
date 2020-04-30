package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import org.fugalang.core.parser.DisjunctionRule;

// suite: ':' 'simple_stmt' | 'block_suite'
public final class Suite extends DisjunctionRule {
    public static final String RULE_NAME = "suite";

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
        setExplicitName(RULE_NAME);
        addChoice("suite1", suite1);
        addChoice("blockSuite", blockSuite);
    }

    public Suite1 suite1() {
        return suite1;
    }

    public BlockSuite blockSuite() {
        return blockSuite;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        var result = false;
        parseTree.exit(level, marker, result);
        return result;
    }

    // ':' 'simple_stmt'
    public static final class Suite1 extends ConjunctionRule {
        public static final String RULE_NAME = "suite:1";

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
            setImpliedName(RULE_NAME);
            addRequired("isTokenColon", isTokenColon);
            addRequired("simpleStmt", simpleStmt);
        }

        public boolean isTokenColon() {
            return isTokenColon;
        }

        public SimpleStmt simpleStmt() {
            return simpleStmt;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            var result = false;
            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
