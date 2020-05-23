package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

/**
 * shift_op: '<<' | '>>'
 */
public final class ShiftOp extends NodeWrapper {

    public ShiftOp(ParseTreeNode node) {
        super(FugaRules.SHIFT_OP, node);
    }

    public boolean isLshift() {
        return is(0);
    }

    public boolean isRshift() {
        return is(1);
    }
}
