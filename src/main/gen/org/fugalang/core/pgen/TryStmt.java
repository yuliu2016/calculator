package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * try_stmt: 'try' 'suite' ('except_suite' | 'finally_suite')
 */
public final class TryStmt extends NodeWrapper {

    public TryStmt(ParseTreeNode node) {
        super(ParserRules.TRY_STMT, node);
    }

    public Suite suite() {
        return get(1, Suite::new);
    }

    public TryStmt3 exceptSuiteOrFinallySuite() {
        return get(2, TryStmt3::new);
    }

    /**
     * 'except_suite' | 'finally_suite'
     */
    public static final class TryStmt3 extends NodeWrapper {

        public TryStmt3(ParseTreeNode node) {
            super(ParserRules.TRY_STMT_3, node);
        }

        public ExceptSuite exceptSuite() {
            return get(0, ExceptSuite::new);
        }

        public boolean hasExceptSuite() {
            return has(0);
        }

        public FinallySuite finallySuite() {
            return get(1, FinallySuite::new);
        }

        public boolean hasFinallySuite() {
            return has(1);
        }
    }
}
