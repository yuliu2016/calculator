package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

/**
 * compound_stmt: 'if_stmt' | 'while_stmt' | 'for_stmt' | 'try_stmt' | 'with_stmt'
 */
public final class CompoundStmt extends NodeWrapper {

    public CompoundStmt(ParseTreeNode node) {
        super(FugaRules.COMPOUND_STMT, node);
    }

    public IfStmt ifStmt() {
        return get(0, IfStmt.class);
    }

    public boolean hasIfStmt() {
        return has(0);
    }

    public WhileStmt whileStmt() {
        return get(1, WhileStmt.class);
    }

    public boolean hasWhileStmt() {
        return has(1);
    }

    public ForStmt forStmt() {
        return get(2, ForStmt.class);
    }

    public boolean hasForStmt() {
        return has(2);
    }

    public TryStmt tryStmt() {
        return get(3, TryStmt.class);
    }

    public boolean hasTryStmt() {
        return has(3);
    }

    public WithStmt withStmt() {
        return get(4, WithStmt.class);
    }

    public boolean hasWithStmt() {
        return has(4);
    }
}
