package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * shift_op: '<<' | '>>'
 */
public final class ShiftOp extends NodeWrapper {

    public ShiftOp(ParseTreeNode node) {
        super(node);
    }

    public boolean isLshift() {
        return is(0);
    }

    public boolean isRshift() {
        return is(1);
    }
}
