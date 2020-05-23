package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * except_suite: ('except_clause' 'suite')+ ['else_suite'] ['finally_suite']
 */
public final class ExceptSuite extends NodeWrapper {

    public ExceptSuite(ParseTreeNode node) {
        super(ParserRules.EXCEPT_SUITE, node);
    }

    public List<ExceptSuite1> exceptClauseSuites() {
        return getList(0, ExceptSuite1.class);
    }

    public ElseSuite elseSuite() {
        return get(1, ElseSuite.class);
    }

    public boolean hasElseSuite() {
        return has(1);
    }

    public FinallySuite finallySuite() {
        return get(2, FinallySuite.class);
    }

    public boolean hasFinallySuite() {
        return has(2);
    }

    /**
     * 'except_clause' 'suite'
     */
    public static final class ExceptSuite1 extends NodeWrapper {

        public ExceptSuite1(ParseTreeNode node) {
            super(ParserRules.EXCEPT_SUITE_1, node);
        }

        public ExceptClause exceptClause() {
            return get(0, ExceptClause.class);
        }

        public Suite suite() {
            return get(1, Suite.class);
        }
    }
}