package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * compound_stmt: 'if_stmt' | 'while_stmt' | 'for_stmt' | 'try_stmt' | 'with_stmt'
 */
public final class CompoundStmt extends NodeWrapper {

    public CompoundStmt(ParseTreeNode node) {
        super(ParserRules.COMPOUND_STMT, node);
    }

    public IfStmt ifStmt() {
        return get(0, IfStmt::new);
    }

    public boolean hasIfStmt() {
        return has(0, ParserRules.IF_STMT);
    }

    public WhileStmt whileStmt() {
        return get(1, WhileStmt::new);
    }

    public boolean hasWhileStmt() {
        return has(1, ParserRules.WHILE_STMT);
    }

    public ForStmt forStmt() {
        return get(2, ForStmt::new);
    }

    public boolean hasForStmt() {
        return has(2, ParserRules.FOR_STMT);
    }

    public TryStmt tryStmt() {
        return get(3, TryStmt::new);
    }

    public boolean hasTryStmt() {
        return has(3, ParserRules.TRY_STMT);
    }

    public WithStmt withStmt() {
        return get(4, WithStmt::new);
    }

    public boolean hasWithStmt() {
        return has(4, ParserRules.WITH_STMT);
    }
}
