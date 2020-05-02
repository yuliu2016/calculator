package org.fugalang.core.pgen;

import org.fugalang.core.parser.*;

import java.util.List;

/**
 * bitwise_xor: 'bitwise_and' ('^' 'bitwise_and')*
 */
public final class BitwiseXor extends ConjunctionRule {

    public static final ParserRule RULE =
            new ParserRule("bitwise_xor", RuleType.Conjunction, true);

    private final BitwiseAnd bitwiseAnd;
    private final List<BitwiseXor2> bitwiseXor2List;

    public BitwiseXor(
            BitwiseAnd bitwiseAnd,
            List<BitwiseXor2> bitwiseXor2List
    ) {
        this.bitwiseAnd = bitwiseAnd;
        this.bitwiseXor2List = bitwiseXor2List;
    }

    @Override
    protected void buildRule() {
        addRequired("bitwiseAnd", bitwiseAnd());
        addRequired("bitwiseXor2List", bitwiseXor2List());
    }

    public BitwiseAnd bitwiseAnd() {
        return bitwiseAnd;
    }

    public List<BitwiseXor2> bitwiseXor2List() {
        return bitwiseXor2List;
    }

    public static boolean parse(ParseTree parseTree, int level) {
        if (!ParserUtil.recursionGuard(level, RULE)) {
            return false;
        }
        var marker = parseTree.enter(level, RULE);
        boolean result;

        result = BitwiseAnd.parse(parseTree, level + 1);
        parseTree.enterCollection();
        while (true) {
            var pos = parseTree.position();
            if (!BitwiseXor2.parse(parseTree, level + 1) ||
                    parseTree.guardLoopExit(pos)) {
                break;
            }
        }
        parseTree.exitCollection();

        parseTree.exit(level, marker, result);
        return result;
    }

    /**
     * '^' 'bitwise_and'
     */
    public static final class BitwiseXor2 extends ConjunctionRule {

        public static final ParserRule RULE =
                new ParserRule("bitwise_xor:2", RuleType.Conjunction, false);

        private final boolean isTokenBitXor;
        private final BitwiseAnd bitwiseAnd;

        public BitwiseXor2(
                boolean isTokenBitXor,
                BitwiseAnd bitwiseAnd
        ) {
            this.isTokenBitXor = isTokenBitXor;
            this.bitwiseAnd = bitwiseAnd;
        }

        @Override
        protected void buildRule() {
            addRequired("isTokenBitXor", isTokenBitXor());
            addRequired("bitwiseAnd", bitwiseAnd());
        }

        public boolean isTokenBitXor() {
            return isTokenBitXor;
        }

        public BitwiseAnd bitwiseAnd() {
            return bitwiseAnd;
        }

        public static boolean parse(ParseTree parseTree, int level) {
            if (!ParserUtil.recursionGuard(level, RULE)) {
                return false;
            }
            var marker = parseTree.enter(level, RULE);
            boolean result;

            result = parseTree.consumeTokenLiteral("^");
            result = result && BitwiseAnd.parse(parseTree, level + 1);

            parseTree.exit(level, marker, result);
            return result;
        }
    }
}
