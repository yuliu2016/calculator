package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * except_suite: ('except_clause' 'suite')+ ['else_suite'] ['finally_suite']
 */
public final class ExceptSuite extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("except_suite", RuleType.Conjunction);

    public static ExceptSuite of(ParseTreeNode node) {
        return new ExceptSuite(node);
    }

    private ExceptSuite(ParseTreeNode node) {
        super(RULE, node);
    }

    public List<ExceptSuite1> exceptClauseSuiteList() {
        return getList(0, ExceptSuite1::of);
    }

    public ElseSuite elseSuite() {
        return ElseSuite.of(get(1));
    }

    public boolean hasElseSuite() {
        return has(1, ElseSuite.RULE);
    }

    public FinallySuite finallySuite() {
        return FinallySuite.of(get(2));
    }

    public boolean hasFinallySuite() {
        return has(2, FinallySuite.RULE);
    }

    public static boolean parse(ParseTree t, int lv) {
        if (!ParserUtil.recursionGuard(lv, RULE)) return false;
        t.enter(lv, RULE);
        boolean r;
        r = parseExceptClauseSuiteList(t, lv);
        if (r) ElseSuite.parse(t, lv + 1);
        if (r) FinallySuite.parse(t, lv + 1);
        t.exit(r);
        return r;
    }

    private static boolean parseExceptClauseSuiteList(ParseTree t, int lv) {
        t.enterCollection();
        var r = ExceptSuite1.parse(t, lv + 1);
        if (r) while (true) {
            var p = t.position();
            if (!ExceptSuite1.parse(t, lv + 1) || t.loopGuard(p)) break;
        }
        t.exitCollection();
        return r;
    }

    /**
     * 'except_clause' 'suite'
     */
    public static final class ExceptSuite1 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("except_suite:1", RuleType.Conjunction);

        public static ExceptSuite1 of(ParseTreeNode node) {
            return new ExceptSuite1(node);
        }

        private ExceptSuite1(ParseTreeNode node) {
            super(RULE, node);
        }

        public ExceptClause exceptClause() {
            return ExceptClause.of(get(0));
        }

        public Suite suite() {
            return Suite.of(get(1));
        }

        public static boolean parse(ParseTree t, int lv) {
            if (!ParserUtil.recursionGuard(lv, RULE)) return false;
            t.enter(lv, RULE);
            boolean r;
            r = ExceptClause.parse(t, lv + 1);
            r = r && Suite.parse(t, lv + 1);
            t.exit(r);
            return r;
        }
    }
}
