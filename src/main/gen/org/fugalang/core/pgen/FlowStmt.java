package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * flow_stmt: 'pass' | 'break' | 'continue' | 'return_stmt' | 'raise_stmt'
 */
public final class FlowStmt extends NodeWrapper {

    public FlowStmt(ParseTreeNode node) {
        super(node);
    }

    public boolean isPass() {
        return is(0);
    }

    public boolean isBreak() {
        return is(1);
    }

    public boolean isContinue() {
        return is(2);
    }

    public ReturnStmt returnStmt() {
        return get(3, ReturnStmt.class);
    }

    public boolean hasReturnStmt() {
        return has(3);
    }

    public RaiseStmt raiseStmt() {
        return get(4, RaiseStmt.class);
    }

    public boolean hasRaiseStmt() {
        return has(4);
    }
}
