package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

/**
 * try_stmt: 'try' 'suite' ('except_suite' | 'finally_suite')
 */
public final class TryStmt extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("try_stmt", RuleType.Conjunction);

    public static TryStmt of(ParseTreeNode node) {
        return new TryStmt(node);
    }

    private TryStmt(ParseTreeNode node) {
        super(RULE, node);
    }

    public Suite suite() {
        return get(1, Suite::of);
    }

    public TryStmt3 exceptSuiteOrFinallySuite() {
        return get(2, TryStmt3::of);
    }

    /**
     * 'except_suite' | 'finally_suite'
     */
    public static final class TryStmt3 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("try_stmt:3", RuleType.Disjunction);

        public static TryStmt3 of(ParseTreeNode node) {
            return new TryStmt3(node);
        }

        private TryStmt3(ParseTreeNode node) {
            super(RULE, node);
        }

        public ExceptSuite exceptSuite() {
            return get(0, ExceptSuite::of);
        }

        public boolean hasExceptSuite() {
            return has(0, ExceptSuite.RULE);
        }

        public FinallySuite finallySuite() {
            return get(1, FinallySuite::of);
        }

        public boolean hasFinallySuite() {
            return has(1, FinallySuite.RULE);
        }
    }
}
