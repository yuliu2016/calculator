package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * comparison: bitwise_or (comp_op bitwise_or)+ | bitwise_or
 */
public final class Comparison extends NodeWrapper {

    public Comparison(ParseTreeNode node) {
        super(node);
    }

    public Comparison1 comparison1() {
        return new Comparison1(get(0));
    }

    public boolean hasComparison1() {
        return has(0);
    }

    public BitwiseOr bitwiseOr() {
        return new BitwiseOr(get(1));
    }

    public boolean hasBitwiseOr() {
        return has(1);
    }

    /**
     * bitwise_or (comp_op bitwise_or)+
     */
    public static final class Comparison1 extends NodeWrapper {

        public Comparison1(ParseTreeNode node) {
            super(node);
        }

        public BitwiseOr bitwiseOr() {
            return new BitwiseOr(get(0));
        }

        public List<Comparison12> compOpBitwiseOrs() {
            return getList(1, Comparison12::new);
        }
    }

    /**
     * comp_op bitwise_or
     */
    public static final class Comparison12 extends NodeWrapper {

        public Comparison12(ParseTreeNode node) {
            super(node);
        }

        public CompOp compOp() {
            return new CompOp(get(0));
        }

        public BitwiseOr bitwiseOr() {
            return new BitwiseOr(get(1));
        }
    }
}
