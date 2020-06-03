package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

/**
 * bitwise_xor: bitwise_xor '^' bitwise_and | bitwise_and
 */
public final class BitwiseXor extends NodeWrapper {

    public BitwiseXor(ParseTreeNode node) {
        super(node);
    }

    public BitwiseXor1 bitwiseXorBitXorBitwiseAnd() {
        return new BitwiseXor1(get(0));
    }

    public boolean hasBitwiseXorBitXorBitwiseAnd() {
        return has(0);
    }

    public BitwiseAnd bitwiseAnd() {
        return new BitwiseAnd(get(1));
    }

    public boolean hasBitwiseAnd() {
        return has(1);
    }

    /**
     * bitwise_xor '^' bitwise_and
     */
    public static final class BitwiseXor1 extends NodeWrapper {

        public BitwiseXor1(ParseTreeNode node) {
            super(node);
        }

        public BitwiseXor bitwiseXor() {
            return new BitwiseXor(get(0));
        }

        public BitwiseAnd bitwiseAnd() {
            return new BitwiseAnd(get(2));
        }
    }
}
