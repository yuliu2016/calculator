package org.fugalang.core.pgen;

import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// comparison: 'bitwise_or' ('comp_op' 'bitwise_or')*
public final class Comparison extends ConjunctionRule {
    private final BitwiseOr bitwiseOr;
    private final List<Comparison2Group> comparison2GroupList;

    public Comparison(
            BitwiseOr bitwiseOr,
            List<Comparison2Group> comparison2GroupList
    ) {
        this.bitwiseOr = bitwiseOr;
        this.comparison2GroupList = comparison2GroupList;

        addRequired("bitwiseOr", bitwiseOr);
        addRequired("comparison2GroupList", comparison2GroupList);
    }

    public BitwiseOr bitwiseOr() {
        return bitwiseOr;
    }

    public List<Comparison2Group> comparison2GroupList() {
        return comparison2GroupList;
    }

    // 'comp_op' 'bitwise_or'
    public static final class Comparison2Group extends ConjunctionRule {
        private final CompOp compOp;
        private final BitwiseOr bitwiseOr;

        public Comparison2Group(
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
