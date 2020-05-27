package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * try_stmt: 'try' suite (except_suite | finally_suite)
 */
public final class TryStmt extends NodeWrapper {

    public TryStmt(ParseTreeNode node) {
        super(node);
    }

    public Suite suite() {
        return get(1, Suite.class);
    }

    public TryStmt3 exceptSuiteOrFinallySuite() {
        return get(2, TryStmt3.class);
    }

    /**
     * except_suite | finally_suite
     */
    public static final class TryStmt3 extends NodeWrapper {

        public TryStmt3(ParseTreeNode node) {
            super(node);
        }

        public ExceptSuite exceptSuite() {
            return get(0, ExceptSuite.class);
        }

        public boolean hasExceptSuite() {
            return has(0);
        }

        public FinallySuite finallySuite() {
            return get(1, FinallySuite.class);
        }

        public boolean hasFinallySuite() {
            return has(1);
        }
    }
}
