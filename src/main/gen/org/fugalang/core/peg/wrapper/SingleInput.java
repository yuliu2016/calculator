package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.token.TokenType;

/**
 * single_input:
 * *   | NEWLINE
 * *   | ENDMARKER
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

    public String endmarker() {
        return get(1, TokenType.ENDMARKER);
    }

    public boolean hasEndmarker() {
        return has(1);
    }

    public SimpleStmt simpleStmt() {
        return new SimpleStmt(get(2));
    }

    public boolean hasSimpleStmt() {
        return has(2);
    }

    public SingleInput4 compoundStmtNewline() {
        return new SingleInput4(get(3));
    }

    public boolean hasCompoundStmtNewline() {
        return has(3);
    }

    /**
     * compound_stmt NEWLINE
     */
    public static final class SingleInput4 extends NodeWrapper {

        public SingleInput4(ParseTreeNode node) {
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
