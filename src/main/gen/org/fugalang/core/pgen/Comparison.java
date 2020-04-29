package org.fugalang.core.pgen;

import java.util.List;

// comparison: 'bitwise_or' ('comp_op' 'bitwise_or')*
public class Comparison {
    private final BitwiseOr bitwiseOr;
    private final List<Comparison2Group> comparison2GroupList;

    public Comparison(
            BitwiseOr bitwiseOr,
            List<Comparison2Group> comparison2GroupList
    ) {
        this.bitwiseOr = bitwiseOr;
        this.comparison2GroupList = comparison2GroupList;
    }

    public BitwiseOr getBitwiseOr() {
        return bitwiseOr;
    }

    public List<Comparison2Group> getComparison2GroupList() {
        return comparison2GroupList;
    }

    // 'comp_op' 'bitwise_or'
    public static class Comparison2Group {
        private final CompOp compOp;
        private final BitwiseOr bitwiseOr;

        public Comparison2Group(
                CompOp compOp,
                BitwiseOr bitwiseOr
        ) {
            this.compOp = compOp;
            this.bitwiseOr = bitwiseOr;
        }

        public CompOp getCompOp() {
            return compOp;
        }

        public BitwiseOr getBitwiseOr() {
            return bitwiseOr;
        }
    }
}
