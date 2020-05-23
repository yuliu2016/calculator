package org.fugalang.core.pgen;

import org.fugalang.core.parser.NodeWrapper;
import org.fugalang.core.parser.ParseTreeNode;
import org.fugalang.core.pgen.parser.FugaRules;

import java.util.List;

/**
 * bitwise_xor: 'bitwise_and' ('^' 'bitwise_and')*
 */
public final class BitwiseXor extends NodeWrapper {

    public BitwiseXor(ParseTreeNode node) {
        super(FugaRules.BITWISE_XOR, node);
    }

    public BitwiseAnd bitwiseAnd() {
        return get(0, BitwiseAnd.class);
    }

    public List<BitwiseXor2> bitwiseAnds() {
        return getList(1, BitwiseXor2.class);
    }

    /**
     * '^' 'bitwise_and'
     */
    public static final class BitwiseXor2 extends NodeWrapper {

        public BitwiseXor2(ParseTreeNode node) {
            super(FugaRules.BITWISE_XOR_2, node);
        }

        public BitwiseAnd bitwiseAnd() {
            return get(1, BitwiseAnd.class);
        }
    }
}
