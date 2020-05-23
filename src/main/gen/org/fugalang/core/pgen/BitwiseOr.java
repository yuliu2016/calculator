package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.ParserRules;

import java.util.List;

/**
 * bitwise_or: 'bitwise_xor' ('|' 'bitwise_xor')*
 */
public final class BitwiseOr extends NodeWrapper {

    public BitwiseOr(ParseTreeNode node) {
        super(ParserRules.BITWISE_OR, node);
    }

    public BitwiseXor bitwiseXor() {
        return get(0, BitwiseXor::new);
    }

    public List<BitwiseOr2> bitwiseXors() {
        return getList(1, BitwiseOr2::new);
    }

    /**
     * '|' 'bitwise_xor'
     */
    public static final class BitwiseOr2 extends NodeWrapper {

        public BitwiseOr2(ParseTreeNode node) {
            super(ParserRules.BITWISE_OR_2, node);
        }

        public BitwiseXor bitwiseXor() {
            return get(1, BitwiseXor::new);
        }
    }
}
