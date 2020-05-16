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
        return Suite.of(get(1));
    }

    public TryStmt3 exceptSuiteOrFinallySuite() {
        return TryStmt3.of(get(2));
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = t.consume("try");
        r = r && Suite.parse(t, lv + 1);
        r = r && TryStmt3.parse(t, lv + 1);
        t.exit(r);
        return r;
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
            return ExceptSuite.of(get(0));
        }

        public boolean hasExceptSuite() {
            return has(0, ExceptSuite.RULE);
        }

        public FinallySuite finallySuite() {
            return FinallySuite.of(get(1));
        }

        public boolean hasFinallySuite() {
            return has(1, FinallySuite.RULE);
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = ExceptSuite.parse(t, lv + 1);
            r = r || FinallySuite.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
