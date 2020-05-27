package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * sum_op: '+' | '-'
 */
public final class SumOp extends NodeWrapper {

    public SumOp(ParseTreeNode node) {
        super(node);
    }

    public boolean isPlus() {
        return is(0);
    }

    public boolean isMinus() {
        return is(1);
    }
}
