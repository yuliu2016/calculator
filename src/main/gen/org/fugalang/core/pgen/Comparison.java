package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// comparison: 'bitwise_or' ('comp_op' 'bitwise_or')*
public final class Comparison extends ConjunctionRule {
    private final BitwiseOr bitwiseOr;
    private final List<Comparison2> comparison2List;

    public Comparison(
            BitwiseOr bitwiseOr,
            List<Comparison2> comparison2List
    ) {
        this.bitwiseOr = bitwiseOr;
        this.comparison2List = comparison2List;

        addRequired("bitwiseOr", bitwiseOr);
        addRequired("comparison2List", comparison2List);
    }

    public BitwiseOr bitwiseOr() {
        return bitwiseOr;
    }

    public List<Comparison2> comparison2List() {
        return comparison2List;
    }

    // 'comp_op' 'bitwise_or'
    public static final class Comparison2 extends ConjunctionRule {
        private final CompOp compOp;
        private final BitwiseOr bitwiseOr;

        public Comparison2(
                CompOp compOp,
                BitwiseOr bitwiseOr
        ) {
            this.compOp = compOp;
            this.bitwiseOr = bitwiseOr;

            addRequired("compOp", compOp);
            addRequired("bitwiseOr", bitwiseOr);
        }

        public CompOp compOp() {
            return compOp;
        }

        public BitwiseOr bitwiseOr() {
            return bitwiseOr;
        }
    }
}
