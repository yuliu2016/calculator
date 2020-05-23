package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

/**
 * sum_op: '+' | '-'
 */
public final class SumOp extends NodeWrapper {

    public SumOp(ParseTreeNode node) {
        super(ParserRules.SUM_OP, node);
    }

    public boolean isPlus() {
        return is(0);
    }

    public boolean isMinus() {
        return is(1);
    }
}
