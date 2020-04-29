package org.fugalang.core.pgen;

// comparison: 'bitwise_or' ('comp_op' 'bitwise_or')*
public class Comparison {
    public final BitwiseOr bitwiseOr;
    public final Comparison2Group comparison2Group;

    public Comparison(
            BitwiseOr bitwiseOr,
            Comparison2Group comparison2Group
    ) {
        this.bitwiseOr = bitwiseOr;
        this.comparison2Group = comparison2Group;
    }
}
