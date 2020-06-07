package org.fugalang.core.peg.wrapper;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * bitwise_or:
 * *   | bitwise_or '|' bitwise_xor
 * *   | bitwise_xor
 */
public final class BitwiseOr extends NodeWrapper {

    public BitwiseOr(ParseTreeNode node) {
        super(node);
    }

    public BitwiseOr1 bitwiseOrBitOrBitwiseXor() {
        return new BitwiseOr1(get(0));
    }

    public boolean hasBitwiseOrBitOrBitwiseXor() {
        return has(0);
    }

    public BitwiseXor bitwiseXor() {
        return new BitwiseXor(get(1));
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
            return new BitwiseOr(get(0));
        }

        public BitwiseXor bitwiseXor() {
            return new BitwiseXor(get(2));
        }
    }
}
