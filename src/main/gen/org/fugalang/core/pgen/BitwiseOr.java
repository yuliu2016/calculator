package org.fugalang.core.pgen;

import org.fugalang.core.parser.ParseTree;
import org.fugalang.core.parser.ConjunctionRule;
import java.util.List;

// bitwise_or: 'bitwise_xor' ('|' 'bitwise_xor')*
public final class BitwiseOr extends ConjunctionRule {
    public static final String RULE_NAME = "bitwise_or";

    private final BitwiseXor bitwiseXor;
    private final List<BitwiseOr2> bitwiseOr2List;

    public BitwiseOr(
            BitwiseXor bitwiseXor,
            List<BitwiseOr2> bitwiseOr2List
    ) {
        this.bitwiseXor = bitwiseXor;
        this.bitwiseOr2List = bitwiseOr2List;
    }

    @Override
    protected void buildRule() {
        setExplicitName(RULE_NAME);
        addRequired("bitwiseXor", bitwiseXor);
        addRequired("bitwiseOr2List", bitwiseOr2List);
    }

    public BitwiseXor bitwiseXor() {
        return bitwiseXor;
    }

    public List<BitwiseOr2> bitwiseOr2List() {
        return bitwiseOr2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParseTree.recursionGuard(level, RULE_NAME)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE_NAME);
        boolean result;

        result = BitwiseXor.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            if (!BitwiseOr2.parse(parseTree, level + 1)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    // '|' 'bitwise_xor'
    public static final class BitwiseOr2 extends ConjunctionRule {
        public static final String RULE_NAME = "bitwise_or:2";

        private final boolean isTokenBitOr;
        private final BitwiseXor bitwiseXor;

        public BitwiseOr2(
                boolean isTokenBitOr,
                BitwiseXor bitwiseXor
        ) {
            this.isTokenBitOr = isTokenBitOr;
            this.bitwiseXor = bitwiseXor;
        }

        @Override
        protected void buildRule() {
            setImpliedName(RULE_NAME);
            addRequired("isTokenBitOr", isTokenBitOr);
            addRequired("bitwiseXor", bitwiseXor);
        }

        public boolean isTokenBitOr() {
            return isTokenBitOr;
        }

        public BitwiseXor bitwiseXor() {
            return bitwiseXor;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParseTree.recursionGuard(level, RULE_NAME)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE_NAME);
            boolean result;

            result = parseTree.consumeTokenLiteral("|");
            result = result && BitwiseXor.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
