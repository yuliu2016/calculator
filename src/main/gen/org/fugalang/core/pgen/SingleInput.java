package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;
import org.fugalang.core.token.TokenType;

/**
 * single_input: 'NEWLINE' | 'simple_stmt' | 'compound_stmt' 'NEWLINE'
 */
public final class SingleInput extends NodeWrapper {

    public SingleInput(ParseTreeNode node) {
        super(FugaRules.SINGLE_INPUT, node);
    }

    public String newline() {
        return get(0, TokenType.NEWLINE);
    }

    public boolean hasNewline() {
        return has(0);
    }

    public SimpleStmt simpleStmt() {
        return get(1, SimpleStmt.class);
    }

    public boolean hasSimpleStmt() {
        return has(1);
    }

    public SingleInput3 compoundStmtNewline() {
        return get(2, SingleInput3.class);
    }

    public boolean hasCompoundStmtNewline() {
        return has(2);
    }

    /**
     * 'compound_stmt' 'NEWLINE'
     */
    public static final class SingleInput3 extends NodeWrapper {

        public SingleInput3(ParseTreeNode node) {
            super(FugaRules.SINGLE_INPUT_3, node);
        }

        public CompoundStmt compoundStmt() {
            return get(0, CompoundStmt.class);
        }

        public String newline() {
            return get(1, TokenType.NEWLINE);
        }
    }
}
