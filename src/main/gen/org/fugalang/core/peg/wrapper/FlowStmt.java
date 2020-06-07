package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * flow_stmt: 
 * | 'pass'
 * | 'break'
 * | 'continue'
 * | return_stmt
 * | raise_stmt
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
        return new ReturnStmt(get(3));
    }

    public boolean hasReturnStmt() {
        return has(3);
    }

    public RaiseStmt raiseStmt() {
        return new RaiseStmt(get(4));
    }

    public boolean hasRaiseStmt() {
        return has(4);
    }
}
