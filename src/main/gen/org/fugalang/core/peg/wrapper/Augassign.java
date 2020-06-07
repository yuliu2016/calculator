package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * augassign:
 * *   | '+='
 * *   | '-='
 * *   | '*='
 * *   | '@='
 * *   | '/='
 * *   | '%='
 * *   | '&='
 * *   | '|='
 * *   | '^='
 * *   | '<<='
 * *   | '>>='
 * *   | '**='
 * *   | '//='
 */
public final class Augassign extends NodeWrapper {

    public Augassign(ParseTreeNode node) {
        super(node);
    }

    public boolean isPlusAssign() {
        return is(0);
    }

    public boolean isMinusAssign() {
        return is(1);
    }

    public boolean isTimesAssign() {
        return is(2);
    }

    public boolean isMatrixTimesAssign() {
        return is(3);
    }

    public boolean isDivAssign() {
        return is(4);
    }

    public boolean isModulusAssign() {
        return is(5);
    }

    public boolean isBitAndAssign() {
        return is(6);
    }

    public boolean isBitOrAssign() {
        return is(7);
    }

    public boolean isBitXorAssign() {
        return is(8);
    }

    public boolean isLshiftAssign() {
        return is(9);
    }

    public boolean isRshiftAssign() {
        return is(10);
    }

    public boolean isPowerAssign() {
        return is(11);
    }

    public boolean isFloorDivAssign() {
        return is(12);
    }
}
