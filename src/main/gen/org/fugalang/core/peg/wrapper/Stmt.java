package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * stmt:
 * *   | (simple_stmt | compound_stmt) NEWLINE
 */
public final class Stmt extends NodeWrapper {

    public Stmt(ParseTreeNode node) {
        super(node);
    }

    public Stmt1 simpleStmtOrCompoundStmt() {
        return new Stmt1(get(0));
    }

    public String newline() {
        return get(1, TokenType.NEWLINE);
    }

    /**
     * simple_stmt | compound_stmt
     */
    public static final class Stmt1 extends NodeWrapper {

        public Stmt1(ParseTreeNode node) {
            super(node);
        }

        public SimpleStmt simpleStmt() {
            return new SimpleStmt(get(0));
        }

        public boolean hasSimpleStmt() {
            return has(0);
        }

        public CompoundStmt compoundStmt() {
            return new CompoundStmt(get(1));
        }

        public boolean hasCompoundStmt() {
            return has(1);
        }
    }
}
