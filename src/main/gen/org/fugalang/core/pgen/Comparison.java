package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * comparison: 'bitwise_or' ('comp_op' 'bitwise_or')*
 */
public final class Comparison extends NodeWrapper {

    public Comparison(ParseTreeNode node) {
        super(ParserRules.COMPARISON, node);
    }

    public BitwiseOr bitwiseOr() {
        return get(0, BitwiseOr::new);
    }

    public List<Comparison2> compOpBitwiseOrs() {
        return getList(1, Comparison2::new);
    }

    /**
     * 'comp_op' 'bitwise_or'
     */
    public static final class Comparison2 extends NodeWrapper {

        public Comparison2(ParseTreeNode node) {
            super(ParserRules.COMPARISON_2, node);
        }

        public CompOp compOp() {
            return get(0, CompOp::new);
        }

        public BitwiseOr bitwiseOr() {
            return get(1, BitwiseOr::new);
        }
    }
}
