package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * single_input:
 * *   | NEWLINE
 * *   | simple_stmt
 * *   | compound_stmt NEWLINE
 */
public final class SingleInput extends NodeWrapper {

    public SingleInput(ParseTreeNode node) {
        super(node);
    }

    public String newline() {
        return get(0, TokenType.NEWLINE);
    }

    public boolean hasNewline() {
        return has(0);
    }

    public SimpleStmt simpleStmt() {
        return new SimpleStmt(get(1));
    }

    public boolean hasSimpleStmt() {
        return has(1);
    }

    public SingleInput3 compoundStmtNewline() {
        return new SingleInput3(get(2));
    }

    public boolean hasCompoundStmtNewline() {
        return has(2);
    }

    /**
     * compound_stmt NEWLINE
     */
    public static final class SingleInput3 extends NodeWrapper {

        public SingleInput3(ParseTreeNode node) {
            super(node);
        }

        public CompoundStmt compoundStmt() {
            return new CompoundStmt(get(0));
        }

        public String newline() {
            return get(1, TokenType.NEWLINE);
        }
    }
}
