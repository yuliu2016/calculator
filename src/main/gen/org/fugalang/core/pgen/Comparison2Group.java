package org.fugalang.core.pgen;

// 'comp_op' 'bitwise_or'
public class Comparison2Group {
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
