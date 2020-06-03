package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * compound_stmt: if_stmt | while_stmt | for_stmt | try_stmt | with_stmt
 */
public final class CompoundStmt extends NodeWrapper {

    public CompoundStmt(ParseTreeNode node) {
        super(node);
    }

    public IfStmt ifStmt() {
        return new IfStmt(get(0));
    }

    public boolean hasIfStmt() {
        return has(0);
    }

    public WhileStmt whileStmt() {
        return new WhileStmt(get(1));
    }

    public boolean hasWhileStmt() {
        return has(1);
    }

    public ForStmt forStmt() {
        return new ForStmt(get(2));
    }

    public boolean hasForStmt() {
        return has(2);
    }

    public TryStmt tryStmt() {
        return new TryStmt(get(3));
    }

    public boolean hasTryStmt() {
        return has(3);
    }

    public WithStmt withStmt() {
        return new WithStmt(get(4));
    }

    public boolean hasWithStmt() {
        return has(4);
    }
}
