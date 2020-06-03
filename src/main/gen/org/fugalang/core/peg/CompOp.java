package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * comp_op: '<' | '>' | '==' | '>=' | '<=' | '!=' | 'in' | 'not' 'in' | 'is' | 'is' 'not'
 */
public final class CompOp extends NodeWrapper {

    public CompOp(ParseTreeNode node) {
        super(node);
    }

    public boolean isLess() {
        return is(0);
    }

    public boolean isGreater() {
        return is(1);
    }

    public boolean isEqual() {
        return is(2);
    }

    public boolean isMoreEqual() {
        return is(3);
    }

    public boolean isLessEqual() {
        return is(4);
    }

    public boolean isNequal() {
        return is(5);
    }

    public boolean isIn() {
        return is(6);
    }

    public CompOp8 notIn() {
        return new CompOp8(get(7));
    }

    public boolean hasNotIn() {
        return has(7);
    }

    public boolean isIs() {
        return is(8);
    }

    public CompOp10 isNot() {
        return new CompOp10(get(9));
    }

    public boolean hasIsNot() {
        return has(9);
    }

    /**
     * 'not' 'in'
     */
    public static final class CompOp8 extends NodeWrapper {

        public CompOp8(ParseTreeNode node) {
            super(node);
        }
    }

    /**
     * 'is' 'not'
     */
    public static final class CompOp10 extends NodeWrapper {

        public CompOp10(ParseTreeNode node) {
            super(node);
        }
    }
}
