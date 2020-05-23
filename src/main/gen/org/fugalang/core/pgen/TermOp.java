package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

/**
 * term_op: '*' | '@' | '/' | '%' | '//'
 */
public final class TermOp extends NodeWrapper {

    public TermOp(ParseTreeNode node) {
        super(FugaRules.TERM_OP, node);
    }

    public boolean isTimes() {
        return is(0);
    }

    public boolean isMatrixTimes() {
        return is(1);
    }

    public boolean isDiv() {
        return is(2);
    }

    public boolean isModulus() {
        return is(3);
    }

    public boolean isFloorDiv() {
        return is(4);
    }
}
