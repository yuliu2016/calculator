package org.fugalang.core.peg;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;

import java.util.List;

/**
 * bitwise_or: bitwise_xor ('|' bitwise_xor)*
 */
public final class BitwiseOr extends NodeWrapper {

    public BitwiseOr(ParseTreeNode node) {
        super(node);
    }

    public BitwiseXor bitwiseXor() {
        return get(0, BitwiseXor.class);
    }

    public List<BitwiseOr2> bitOrBitwiseXors() {
        return getList(1, BitwiseOr2.class);
    }

    /**
     * '|' bitwise_xor
     */
    public static final class BitwiseOr2 extends NodeWrapper {

        public BitwiseOr2(ParseTreeNode node) {
            super(node);
        }

        public BitwiseXor bitwiseXor() {
            return get(1, BitwiseXor.class);
        }
    }
}
