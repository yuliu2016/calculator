package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * bitwise_or: bitwise_or '|' bitwise_xor | bitwise_xor
 */
public final class BitwiseOr extends NodeWrapper {

    public BitwiseOr(ParseTreeNode node) {
        super(node);
    }

    public BitwiseOr1 bitwiseOrBitOrBitwiseXor() {
        return get(0, BitwiseOr1.class);
    }

    public boolean hasBitwiseOrBitOrBitwiseXor() {
        return has(0);
    }

    public BitwiseXor bitwiseXor() {
        return get(1, BitwiseXor.class);
    }

    public boolean hasBitwiseXor() {
        return has(1);
    }

    /**
     * bitwise_or '|' bitwise_xor
     */
    public static final class BitwiseOr1 extends NodeWrapper {

        public BitwiseOr1(ParseTreeNode node) {
            super(node);
        }

        public BitwiseOr bitwiseOr() {
            return get(0, BitwiseOr.class);
        }

        public BitwiseXor bitwiseXor() {
            return get(2, BitwiseXor.class);
        }
    }
}
