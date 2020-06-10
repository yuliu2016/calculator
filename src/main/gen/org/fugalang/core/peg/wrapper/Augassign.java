package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * augassign:
 * *   | target augassign_op exprlist
 */
public final class Augassign extends NodeWrapper {

    public Augassign(ParseTreeNode node) {
        super(node);
    }

    public Target target() {
        return new Target(get(0));
    }

    public AugassignOp augassignOp() {
        return new AugassignOp(get(1));
    }

    public Exprlist exprlist() {
        return new Exprlist(get(2));
    }
}
