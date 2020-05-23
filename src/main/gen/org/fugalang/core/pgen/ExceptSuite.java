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

    public List<ExceptSuite1> exceptClauseSuites() {
        return getList(0, ExceptSuite1::of);
    }

    public ElseSuite elseSuite() {
        return get(1, ElseSuite::of);
    }

    public boolean hasElseSuite() {
        return has(1, ElseSuite.RULE);
    }

    public FinallySuite finallySuite() {
        return get(2, FinallySuite::of);
    }

    public boolean hasFinallySuite() {
        return has(2, FinallySuite.RULE);
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
            return get(0, ExceptClause::of);
        }

        public Suite suite() {
            return get(1, Suite::of);
        }
    }
}
