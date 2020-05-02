package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * bitwise_or: 'bitwise_xor' ('|' 'bitwise_xor')*
 */
public final class BitwiseOr extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("bitwise_or", RuleType.Conjunction, true);

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
        addRequired("bitwiseXor", bitwiseXor());
        addRequired("bitwiseOr2List", bitwiseOr2List());
    }

    public BitwiseXor bitwiseXor() {
        return bitwiseXor;
    }

    public List<BitwiseOr2> bitwiseOr2List() {
        return bitwiseOr2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = BitwiseXor.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!BitwiseOr2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '|' 'bitwise_xor'
     */
    public static final class BitwiseOr2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("bitwise_or:2", RuleType.Conjunction, false);

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
            addRequired("isTokenBitOr", isTokenBitOr());
            addRequired("bitwiseXor", bitwiseXor());
        }

        public boolean isTokenBitOr() {
            return isTokenBitOr;
        }

        public BitwiseXor bitwiseXor() {
            return bitwiseXor;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("|");
            result = result && BitwiseXor.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
