package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * comparison: bitwise_or (comp_op bitwise_or)*
 */
public final class Comparison extends NodeWrapper {

    public Comparison(ParseTreeNode node) {
        super(node);
    }

    public BitwiseOr bitwiseOr() {
        return get(0, BitwiseOr.class);
    }

    public List<Comparison2> compOpBitwiseOrs() {
        return getList(1, Comparison2.class);
    }

    /**
     * comp_op bitwise_or
     */
    public static final class Comparison2 extends NodeWrapper {

        public Comparison2(ParseTreeNode node) {
            super(node);
        }

        public CompOp compOp() {
            return get(0, CompOp.class);
        }

        public BitwiseOr bitwiseOr() {
            return get(1, BitwiseOr.class);
        }
    }
}
