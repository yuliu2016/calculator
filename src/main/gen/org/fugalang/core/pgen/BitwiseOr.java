package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * bitwise_or: 'bitwise_xor' ('|' 'bitwise_xor')*
 */
public final class BitwiseOr extends NodeWrapper {
    public static final ParserRule RULE =
            ParserRule.of("bitwise_or", RuleType.Conjunction);

    public static BitwiseOr of(ParseTreeNode node) {
        return new BitwiseOr(node);
    }

    private BitwiseOr(ParseTreeNode node) {
        super(RULE, node);
    }

    public BitwiseXor bitwiseXor() {
        return get(0, BitwiseXor::of);
    }

    public List<BitwiseOr2> bitwiseXors() {
        return getList(1, BitwiseOr2::of);
    }

    /**
     * '|' 'bitwise_xor'
     */
    public static final class BitwiseOr2 extends NodeWrapper {
        public static final ParserRule RULE =
                ParserRule.of("bitwise_or:2", RuleType.Conjunction);

        public static BitwiseOr2 of(ParseTreeNode node) {
            return new BitwiseOr2(node);
        }

        private BitwiseOr2(ParseTreeNode node) {
            super(RULE, node);
        }

        public BitwiseXor bitwiseXor() {
            return get(1, BitwiseXor::of);
        }
    }
}
