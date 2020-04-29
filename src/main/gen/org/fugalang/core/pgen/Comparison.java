package org.fugalang.core.pgen;

import java.util.List;

// comparison: 'bitwise_or' ('comp_op' 'bitwise_or')*
public class Comparison {
    public final BitwiseOr bitwiseOr;
    public final List<Comparison2Group> comparison2GroupList;

    public Comparison(
            BitwiseOr bitwiseOr,
            List<Comparison2Group> comparison2GroupList
    ) {
        this.bitwiseOr = bitwiseOr;
        this.comparison2GroupList = comparison2GroupList;
    }

    // 'comp_op' 'bitwise_or'
    public static class Comparison2Group {
        public final CompOp compOp;
        public final BitwiseOr bitwiseOr;

        public Comparison2Group(
                CompOp compOp,
                BitwiseOr bitwiseOr
        ) {
            this.compOp = compOp;
            this.bitwiseOr = bitwiseOr;
        }
    }
}
