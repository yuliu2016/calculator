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
        return get(0, BitwiseXor1.class);
    }

    public boolean hasBitwiseXorBitXorBitwiseAnd() {
        return has(0);
    }

    public BitwiseAnd bitwiseAnd() {
        return get(1, BitwiseAnd.class);
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
            return get(0, BitwiseXor.class);
        }

        public BitwiseAnd bitwiseAnd() {
            return get(2, BitwiseAnd.class);
        }
    }
}
