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
        return new Suite(get(1));
    }

    public TryStmt3 exceptSuiteOrFinallySuite() {
        return new TryStmt3(get(2));
    }

    /**
     * except_suite | finally_suite
     */
    public static final class TryStmt3 extends NodeWrapper {

        public TryStmt3(ParseTreeNode node) {
            super(node);
        }

        public ExceptSuite exceptSuite() {
            return new ExceptSuite(get(0));
        }

        public boolean hasExceptSuite() {
            return has(0);
        }

        public FinallySuite finallySuite() {
            return new FinallySuite(get(1));
        }

        public boolean hasFinallySuite() {
            return has(1);
        }
    }
}
